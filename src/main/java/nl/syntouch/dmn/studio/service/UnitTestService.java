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
import nl.syntouch.dmn.studio.model.dto.deployment.DeployDTO;
import nl.syntouch.dmn.studio.model.dto.test.DeployTestDTO;
import nl.syntouch.dmn.studio.model.dto.test.UnittestResultDTO;
import nl.syntouch.dmn.studio.repository.UnittestRepository;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.RestClientBuilder;
import org.openapi.quarkus.operaton_rest_api_json.api.DeploymentApi;
import org.openapi.quarkus.operaton_rest_api_json.model.DeploymentWithDefinitionsDto;
import nl.syntouch.dmn.studio.repository.DmnRepository;
import nl.syntouch.dmn.studio.repository.DmnVersionRepository;
import org.openapi.quarkus.operaton_rest_api_json.api.DecisionDefinitionApi;
import org.openapi.quarkus.operaton_rest_api_json.model.EvaluateDecisionDto;
import org.openapi.quarkus.operaton_rest_api_json.model.VariableValueDto;
import java.io.IOException;
import java.net.URI;
import java.util.*;


@Transactional
@RequiredArgsConstructor
@ApplicationScoped
public class UnitTestService {
    @ConfigProperty(name = "quarkus.rest-client.operaton_rest_api_ut.url")
    String unitTestUrl;

    @ConfigProperty(name = "operaton.context-path")
    String contextPath;

    private final SecurityIdentity identity;
    private final DmnRepository dmnRepository;
    private final DmnVersionRepository dmnVersionRepository;
    private final UnittestRepository unittestRepository;
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public UnittestResultDTO handleTestDeployment(DeployTestDTO deployTestDTO) throws IOException {
        DMN dmn = dmnRepository.findByIdOptional(deployTestDTO.dmnId()).orElseThrow();
        DMNVersion dmnVersion = dmnVersionRepository.findByIdOptional(new DMNVersionId(dmn.getId(), deployTestDTO.version())).orElseThrow();

        DeploymentWithDefinitionsDto deploymentWithDefinitionsDto = createTestDeployment(dmn, dmnVersion);
        try {
            UnittestResultDTO result = callTestDeployment(deploymentWithDefinitionsDto, deployTestDTO);

            Test unitTest = getTest(deployTestDTO, dmnVersion, result.result());
            unittestRepository.persist(unitTest);

            return result;
        } finally {
            deleteTestDeployment(deploymentWithDefinitionsDto.getId());
        }
    }

    private DeploymentWithDefinitionsDto createTestDeployment(DMN dmn, DMNVersion dmnVersion) throws IOException {
        Environment testEnv = Environment.find("internal = true and lower(name) = ?1", "test").firstResult();

        DeployDTO deploymentData = new DeployDTO(
                dmn,
                dmnVersion.getVersion(),
                testEnv,
                "unit-test-deployments",
                identity.getPrincipal().getName(),
                false,
                true,
                "Unit Test - " + dmn.getName(),
                null,
                ""
        );

        var form = DmnDeploymentService.getCreateDeploymentMultipartForm(deploymentData, dmnVersion.getFileBlob());
        DeploymentApi unitTestClient = RestClientBuilder.newBuilder().baseUri(unitTestUrl + contextPath).build(DeploymentApi.class);
        try {
            return unitTestClient.createDeployment(form);
        } finally {
            DmnDeploymentService.deleteTempFile(form.data);
        }
    }

    private static Test getTest(DeployTestDTO deployTestDTO, DMNVersion dmnVersion, boolean passed) {
        Test unitTest = new Test();
        unitTest.setDecisionName(deployTestDTO.decisionName());
        unitTest.setTitle(deployTestDTO.title());
        unitTest.setDmnVersion(dmnVersion);
        unitTest.setPassed(passed);
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
        Optional<String> decisionDefinitionIdOpt = deploymentWithDefinitionsDto
                .getDeployedDecisionDefinitions()
                .keySet()
                .stream()
                .filter(key -> key.contains(deployTestDTO.decisionName()))
                .findFirst();
        String decisionDefinitionId = decisionDefinitionIdOpt.orElse(null);

        try {
            DecisionDefinitionApi unitTestClient = RestClientBuilder.newBuilder()
                    .baseUri(URI.create(unitTestUrl + contextPath))
                    .build(DecisionDefinitionApi.class);

            EvaluateDecisionDto evaluateDecisionDto = buildEvaluateDecisionDto(deployTestDTO.inputData());
            List<Map<String, VariableValueDto>> result = unitTestClient.evaluateDecisionById(decisionDefinitionId, evaluateDecisionDto);

            String responseBody = objectMapper.writeValueAsString(result);
            String expected = buildOutputJSON(deployTestDTO.outputData());
            boolean passed = compareDMNResponse(expected, responseBody);

            return new UnittestResultDTO(passed, expected, responseBody);
        } catch (Exception e) {
            throw new RuntimeException(e);
         }
     }

    private EvaluateDecisionDto buildEvaluateDecisionDto(List<DeployTestDTO.ParamDTO> inputData) {
        Map<String, VariableValueDto> variables = new LinkedHashMap<>();
        for (DeployTestDTO.ParamDTO param : inputData) {
            VariableValueDto variableValueDto = new VariableValueDto();
            variableValueDto.setValue(param.value());
            variableValueDto.setType(param.typeRef());
            variables.put(param.key(), variableValueDto);
        }
        EvaluateDecisionDto evaluateDecisionDto = new EvaluateDecisionDto();
        evaluateDecisionDto.setVariables(variables);
        return evaluateDecisionDto;
    }

    private String buildOutputJSON(List<DeployTestDTO.ParamDTO> outputData) {
        ObjectNode combinedObject = objectMapper.createObjectNode();
        for (DeployTestDTO.ParamDTO param : outputData) {
            ObjectNode valueObject = objectMapper.createObjectNode();
            String capitalized = param.typeRef().isEmpty() ? param.typeRef() : param.typeRef().substring(0,1).toUpperCase() + param.typeRef().substring(1);
            valueObject.put("value", param.value());
            valueObject.put("type", capitalized);
            valueObject.set("valueInfo", objectMapper.createObjectNode()); // empty object
            combinedObject.set(param.key(), valueObject);
        }
        return objectMapper.createArrayNode().add(combinedObject).toString();
    }

    private boolean compareDMNResponse(String expectedParams, String dmnResponseJson) throws Exception {
        JsonNode expectedArray = objectMapper.readTree(expectedParams);
        JsonNode actualArray = objectMapper.readTree(dmnResponseJson);
        return expectedArray.equals(actualArray);
    }

    public void deleteTestDeployment(String deploymentId) {
        try {
            DeploymentApi unitTestClient = RestClientBuilder.newBuilder().baseUri(unitTestUrl + contextPath).build(DeploymentApi.class);
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
