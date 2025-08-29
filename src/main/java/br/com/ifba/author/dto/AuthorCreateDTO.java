package br.com.ifba.author.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AuthorCreateDTO {
    @NotBlank(message = "O nome do autor é obrigatório.")
    private String authorName;
    private String biography;
    private String authorPhoto;
}