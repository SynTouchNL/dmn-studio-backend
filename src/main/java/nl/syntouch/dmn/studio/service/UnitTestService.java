package nl.syntouch.dmn.studio.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.quarkus.security.identity.SecurityIdentity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import nl.syntouch.dmn.studio.model.*;
import nl.syntouch.dmn.studio.model.composites.DMNVersionId;
import nl.syntouch.dmn.studio.model.dto.DeployDTO;
import nl.syntouch.dmn.studio.model.dto.DeployTestDTO;
import nl.syntouch.dmn.studio.model.dto.UnittestResultDTO;
import nl.syntouch.dmn.studio.repository.UnittestRepository;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.RestClientBuilder;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.openapi.quarkus.operaton_rest_api_json.api.DeploymentApi;
import org.openapi.quarkus.operaton_rest_api_json.model.DeploymentWithDefinitionsDto;
import nl.syntouch.dmn.studio.repository.DmnRepository;
import nl.syntouch.dmn.studio.repository.DmnVersionRepository;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;
import java.util.stream.Collectors;


@Transactional
@RequiredArgsConstructor
@ApplicationScoped
public class UnitTestService {
    @RestClient
    DeploymentApi deploymentApi;

    @ConfigProperty(name = "quarkus.rest-client.operaton_rest_api_ut.url")
    String unitTestUrl;

    private final SecurityIdentity identity;
    private final DmnRepository dmnRepository;
    private final DmnVersionRepository dmnVersionRepository;
    private final UnittestRepository unittestRepository;
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public UnittestResultDTO handleTestDeployment(DeployTestDTO deployTestDTO) throws IOException {
        DeploymentWithDefinitionsDto deploymentWithDefinitionsDto = createTestDeployment(deployTestDTO);
        UnittestResultDTO result = callTestDeployment(deploymentWithDefinitionsDto, deployTestDTO);
        deleteTestDeployment(deployTestDTO, deploymentWithDefinitionsDto.getId()); // https://github.com/awaitility/awaitility
        return result;
    }

    public DeploymentWithDefinitionsDto createTestDeployment(DeployTestDTO deployTestDTO) throws IOException {
        DMN dmn = dmnRepository.findByIdOptional(deployTestDTO.dmnId()).orElseThrow();
        DMNVersion dmnVersion = dmnVersionRepository.findByIdOptional(new DMNVersionId(dmn.getId(), deployTestDTO.version())).orElseThrow();
        Environment test_env = Environment.find("name", "test").firstResult();

        DeployDTO deploymentData = new DeployDTO(
                dmn,
                dmnVersion.getVersion(),
                test_env,
                "unit-test-deployments",
                identity.getPrincipal().getName(),
                false,
                true,
                "Unit Test - " + dmn.getName(),
                null,
                ""
        );

        var form = DmnDeploymentService.getCreateDeploymentMultipartForm(deploymentData, dmnVersion.getFileBlob());
        DeploymentApi unitTestClient = RestClientBuilder.newBuilder().baseUri(unitTestUrl + "/engine-rest").build(DeploymentApi.class);
        DeploymentWithDefinitionsDto deploymentWithDefinitionsDto = unitTestClient.createDeployment(form);
        Test unitTest = getTest(deployTestDTO, dmnVersion);
        unittestRepository.persist(unitTest);

        return deploymentWithDefinitionsDto;
    }

    private static Test getTest(DeployTestDTO deployTestDTO, DMNVersion dmnVersion) {
        Test unitTest = new Test();
        unitTest.setDecisionName(deployTestDTO.decisionName());
        unitTest.setTitle(deployTestDTO.title());
        unitTest.setDmnVersion(dmnVersion);
        List<KeyValue> values = new ArrayList<>();

        for (DeployTestDTO.ParamDTO param : deployTestDTO.inputData()) {
            KeyValue kv = new KeyValue();
            kv.setKey(param.key());
            kv.setValue(param.value());
            kv.setIsInput(true);
            kv.setValueType(param.typeRef());

            kv.setTest(unitTest);
            values.add(kv);
        }

        for (DeployTestDTO.ParamDTO param : deployTestDTO.outputData()) {
            KeyValue kv = new KeyValue();
            kv.setKey(param.key());
            kv.setValue(param.value());
            kv.setIsInput(false);
            kv.setValueType(param.typeRef());

            kv.setTest(unitTest);
            values.add(kv);
        }

        unitTest.setValues(values);
        return unitTest;
    }

    public UnittestResultDTO callTestDeployment(DeploymentWithDefinitionsDto deploymentWithDefinitionsDto, DeployTestDTO deployTestDTO) {
        HttpClient client = HttpClient.newHttpClient();
        Optional<String> decisionDefinitionIdOpt = deploymentWithDefinitionsDto
                .getDeployedDecisionDefinitions()
                .keySet()
                .stream()
                .filter(key -> key.contains(deployTestDTO.decisionName()))
                .findFirst();
        String decisionDefinitionId = decisionDefinitionIdOpt.orElse(null);

        try {
            String jsonInput = buildJSON(deployTestDTO.inputData());
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(unitTestUrl + "/engine-rest/decision-definition/" + decisionDefinitionId + "/evaluate"))
                    .POST(HttpRequest.BodyPublishers.ofString(jsonInput))
                    .header("Content-Type", "application/json")
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String responseBody = response.body();
            boolean passed = compareDMNResponse(buildOutputJSON(deployTestDTO.outputData()), responseBody);
            Test unitTest = unittestRepository.find("decisionName = ?1 AND dmnVersion.dmn.id = ?2 AND dmnVersion.version = ?3",
                    deployTestDTO.decisionName(),
                    deployTestDTO.dmnId(),
                    deployTestDTO.version()).firstResult();
            unitTest.setPassed(passed);
            unittestRepository.persist(unitTest);

            return new UnittestResultDTO(passed, buildOutputJSON(deployTestDTO.outputData()), responseBody);
        } catch (Exception e) {
            throw new RuntimeException(e);
         }
     }

    private String buildJSON(List<DeployTestDTO.ParamDTO> inputData) throws JsonProcessingException {
        Map<String, Object> variables = inputData.stream()
                .collect(Collectors.toMap(
                        DeployTestDTO.ParamDTO::key,
                        param -> Map.of(
                                "value", param.value(),
                                "type", param.typeRef()
                        ),
                        (existing, replacement) -> existing,
                        LinkedHashMap::new
                ));

        return objectMapper.writeValueAsString(Map.of("variables", variables));
    }

    private String buildOutputJSON(List<DeployTestDTO.ParamDTO> outputData) throws JsonProcessingException {
        ArrayNode expectedArray = objectMapper.createArrayNode();
        for (DeployTestDTO.ParamDTO param : outputData) {
            ObjectNode paramObject = objectMapper.createObjectNode();
            ObjectNode valueObject = objectMapper.createObjectNode();
            String capitalized = param.typeRef().isEmpty() ? param.typeRef() : param.typeRef().substring(0,1).toUpperCase() + param.typeRef().substring(1);
            valueObject.put("type", capitalized);
            valueObject.put("value", param.value());
            valueObject.set("valueInfo", objectMapper.createObjectNode()); // empty object
            paramObject.set(param.key(), valueObject);
            expectedArray.add(paramObject);
        }
        return expectedArray.toString();
    }

    private boolean compareDMNResponse(String expectedParams, String dmnResponseJson) throws Exception {
        JsonNode expectedArray = objectMapper.readTree(expectedParams);
        JsonNode actualArray = objectMapper.readTree(dmnResponseJson);
        return expectedArray.equals(actualArray);
    }

    public void deleteTestDeployment(DeployTestDTO deployTestDTO, String deploymentId) {
        try {
            DeploymentApi unitTestClient = RestClientBuilder.newBuilder().baseUri(unitTestUrl + "/engine-rest").build(DeploymentApi.class);
            unitTestClient.deleteDeployment(deploymentId, true, true, true);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<Test> getTests(Long dmnId, Long version) {
        return unittestRepository.find("dmnVersion.dmn.id = ?1 AND dmnVersion.version = ?2", dmnId, version).list();
    }

    public void deleteTest(Long dmnId, Long version, Long testId) throws NotFoundException {
        Test deletableTest = unittestRepository.find("dmnVersion.dmn.id = ?1 AND dmnVersion.version = ?2 AND id = ?3", dmnId, version, testId).firstResult();
        unittestRepository.delete(deletableTest);
    }
}
