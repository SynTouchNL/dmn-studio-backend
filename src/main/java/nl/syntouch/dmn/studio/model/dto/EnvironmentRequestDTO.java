package nl.syntouch.dmn.studio.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import nl.syntouch.dmn.studio.model.validation.HttpUrl;

public record EnvironmentRequestDTO(
        @NotBlank(message = "Name is required")
        @Size(max = 255, message = "Name must be at most 255 characters")
        String name,
        @NotBlank(message = "URL is required")
        @Size(max = 2048, message = "URL must be at most 2048 characters")
        @HttpUrl
        String url,
        @Size(max = 255, message = "Username must be at most 255 characters")
        String username,
        @Size(max = 512, message = "Password must be at most 512 characters")
        String password,
        @NotNull(message = "Active status is required")
        Boolean active
) {
}
