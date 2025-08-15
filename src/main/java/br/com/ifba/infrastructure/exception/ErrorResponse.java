package br.com.ifba.infrastructure.exception;
import java.time.LocalDateTime;

// DTO para padronizar a resposta de erro da nossa API.
public record ErrorResponse(
        String message,
        int status,
        LocalDateTime timestamp
) {
}