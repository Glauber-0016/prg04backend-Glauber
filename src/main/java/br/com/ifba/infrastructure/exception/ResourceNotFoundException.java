package br.com.ifba.infrastructure.exception;

// Exceção para ser usada quando um recurso (como um usuário ou livro) não é encontrado.
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}