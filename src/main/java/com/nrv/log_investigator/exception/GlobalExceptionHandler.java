package com.nrv.log_investigator.exception;

import com.nrv.log_investigator.domain.dto.ApiErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.ResourceAccessException;

import java.time.Instant;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log =
            LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // 1. Request validation failures: @NotBlank, @Size, etc.
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleValidationException(
            MethodArgumentNotValidException ex,
            HttpServletRequest request) {

        List<String> details = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .toList();

        log.warn("Request validation failed: {}", details);

        return buildResponse(
                HttpStatus.BAD_REQUEST,
                "VALIDATION_FAILED",
                "One or more request fields are invalid.",
                details,
                request
        );
    }

    // 2. Invalid or malformed JSON
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiErrorResponse> handleInvalidRequestBody(
            HttpMessageNotReadableException ex,
            HttpServletRequest request) {

        log.warn("Invalid request body: {}", ex.getMessage());

        return buildResponse(
                HttpStatus.BAD_REQUEST,
                "INVALID_REQUEST_BODY",
                "The request body is missing, malformed, or contains invalid values.",
                List.of(),
                request
        );
    }

    // 3. Ollama / AI service unavailable
    @ExceptionHandler(ResourceAccessException.class)
    public ResponseEntity<ApiErrorResponse> handleResourceAccessException(
            ResourceAccessException ex,
            HttpServletRequest request) {

        log.error("Failed to communicate with AI service", ex);

        return buildResponse(
                HttpStatus.SERVICE_UNAVAILABLE,
                "AI_SERVICE_UNAVAILABLE",
                "The log analysis service is temporarily unavailable. Please try again later.",
                List.of(),
                request
        );
    }

    // 4. Any unexpected exception
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleGenericException(
            Exception ex,
            HttpServletRequest request) {

        log.error("Unexpected error occurred while processing request", ex);

        return buildResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "INTERNAL_SERVER_ERROR",
                "An unexpected error occurred. Please try again later.",
                List.of(),
                request
        );
    }

    // Common response builder to avoid duplication
    private ResponseEntity<ApiErrorResponse> buildResponse(
            HttpStatus status,
            String error,
            String message,
            List<String> details,
            HttpServletRequest request) {

        ApiErrorResponse response = new ApiErrorResponse(
                status.value(),
                error,
                message,
                details,
                request.getRequestURI(),
                Instant.now()
        );

        return ResponseEntity.status(status).body(response);
    }
}