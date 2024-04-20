package com.my.project.imdd_clone.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

public record LoginRequest(
        @NotBlank
        String username,
        @NotBlank
        String password
) {
}
