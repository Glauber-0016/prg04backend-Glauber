package br.com.ifba.article.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * DTO (Data Transfer Object) utilizado para encapsular os dados de entrada
 * ao criar um novo Artigo.
 * Esta classe é recebida como corpo (body) da requisição no endpoint de criação de artigo.
 * A anotação @Data do Lombok gera automaticamente getters, setters, toString, etc.
 */
@Data
public class ArticleCreateDTO {

    /**
     * O título do artigo.
     * A anotação @NotBlank garante que o valor não seja nulo e não contenha apenas espaços em branco.
     * A mensagem é a resposta de erro caso a validação falhe.
     */
    @NotBlank(message = "O título é obrigatório.")
    private String title;

    /**
     * O conteúdo principal (corpo) do artigo.
     * A anotação @NotBlank garante que o valor não seja nulo e não contenha apenas espaços em branco.
     */
    @NotBlank(message = "O conteúdo é obrigatório.")
    private String content;
}