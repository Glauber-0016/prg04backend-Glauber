package br.com.ifba.like.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LikeDTO {
    @NotNull(message = "O ID do usuário é obrigatório.")
    private Long userId;

    private Long articleId;
    private Long commentId;
}