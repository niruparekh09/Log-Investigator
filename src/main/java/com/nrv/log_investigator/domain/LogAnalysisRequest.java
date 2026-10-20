package com.nrv.log_investigator.domain;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LogAnalysisRequest(
        @NotBlank(message = "Logs must not be empty")
        @Size(max = 50_000, message = "Logs must not exceed 50,000 characters")
        String logs
) {
}