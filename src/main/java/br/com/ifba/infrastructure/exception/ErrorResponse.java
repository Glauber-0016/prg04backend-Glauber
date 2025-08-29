package br.com.ifba.infrastructure.exception;
import java.time.LocalDateTime;

// DTO para padronizar a resposta de erro.
public record ErrorResponse(
        String message,
        int status,
        LocalDateTime timestamp
) {
}