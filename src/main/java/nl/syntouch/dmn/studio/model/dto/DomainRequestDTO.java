package nl.syntouch.dmn.studio.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.UUID;

public record DomainRequestDTO(
        @NotBlank(message = "Name is required")
        @Size(min = 1, max = 45, message = "Name must be between 1 and 45 characters")
        String name,
        @NotNull(message = "Owner is required")
        @UUID(message = "ownerId must be a UUID")
        String ownerId,
        @NotNull(message = "Active status is required")
        Boolean active
) {
}
