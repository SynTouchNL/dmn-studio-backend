package nl.syntouch.dmn.studio.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import nl.syntouch.dmn.studio.model.validation.HttpUrl;

public record ConnectionTestRequestDTO(
        @NotBlank(message = "URL is required")
        @Size(max = 2048, message = "URL must be at most 2048 characters")
        @HttpUrl
        String url,
        String username,
        String password,
        Long environmentId
) {
}
