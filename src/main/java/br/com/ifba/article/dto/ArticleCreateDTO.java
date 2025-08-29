package br.com.ifba.article.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ArticleCreateDTO {

    @NotBlank(message = "O título é obrigatório.")
    private String title;

    @NotBlank(message = "O conteúdo é obrigatório.")
    private String content;
}