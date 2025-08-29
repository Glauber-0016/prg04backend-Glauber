package br.com.ifba.comment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CommentCreateDTO {

    @NotBlank(message = "O texto do comentário não pode ser vazio.")
    private String commentText;

    @NotNull(message = "O ID do usuário é obrigatório.")
    private Long userId;

    @NotNull(message = "O ID do artigo é obrigatório.")
    private Long articleId;
}