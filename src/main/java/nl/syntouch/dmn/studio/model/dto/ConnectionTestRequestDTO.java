package nl.syntouch.dmn.studio.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Connection test for unsaved form values. When password is omitted and environmentId is given,
 * the stored password of that environment is used.
 */
public record ConnectionTestRequestDTO(
        @NotBlank(message = "URL is required")
        @Size(max = 2048, message = "URL must be at most 2048 characters")
        String url,
        String username,
        String password,
        Long environmentId
) {
}
