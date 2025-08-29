package br.com.ifba.infrastructure.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * Manipulador de Exceções Global para a aplicação.
 * <p>
 * A anotação {@code @ControllerAdvice} permite que esta classe intercepte exceções
 * lançadas por qualquer Controller na aplicação. Isso centraliza o tratamento de erros,
 * mantendo os controllers limpos e garantindo que as respostas de erro da API
 * sejam consistentes.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Manipula a exceção customizada {@link ResourceNotFoundException}.
     * <p>
     * É acionado quando uma operação tenta acessar um recurso que não existe (ex: buscar um usuário por um ID inválido).
     *
     * @param ex A exceção {@link ResourceNotFoundException} que foi lançada.
     * @return um {@link ResponseEntity} com a mensagem da exceção e o status HTTP 404 (Not Found).
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleResourceNotFoundException(ResourceNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    /**
     * Manipula a exceção {@link IllegalArgumentException}.
     * <p>
     * É acionado para erros de validação de regras de negócio na camada de serviço
     * (ex: tentar cadastrar um email que já existe).
     *
     * @param ex A exceção {@link IllegalArgumentException} que foi lançada.
     * @return um {@link ResponseEntity} com a mensagem da exceção e o status HTTP 400 (Bad Request).
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    /**
     * Manipula a exceção {@link MethodArgumentNotValidException}.
     * <p>
     * É acionado automaticamente pelo Spring quando a validação de um DTO anotado com {@code @Valid} falha.
     * Este método extrai os erros de cada campo e os retorna em um formato de mapa (JSON).
     *
     * @param ex A exceção {@link MethodArgumentNotValidException} que foi lançada.
     * @return um {@link ResponseEntity} contendo um mapa de campos e suas respectivas mensagens de erro,
     * e o status HTTP 400 (Bad Request).
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        // Itera sobre todos os erros de campo encontrados na validação
        ex.getBindingResult().getFieldErrors().forEach(error ->
                // Adiciona o nome do campo e a mensagem de erro padrão ao mapa
                errors.put(error.getField(), error.getDefaultMessage()));
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}