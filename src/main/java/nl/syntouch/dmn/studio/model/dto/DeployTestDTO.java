package nl.syntouch.dmn.studio.model.dto;

import java.util.LinkedHashMap;
import java.util.List;

public record DeployTestDTO (
        String decisionName,
        Long dmnId,
        Long version,
        String title,
        List<ParamDTO> inputData,
        List<ParamDTO> outputData
) {
    public record ParamDTO(
            String key,
            String value,
            String typeRef
    ) {}
}