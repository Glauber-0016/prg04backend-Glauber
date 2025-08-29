package br.com.ifba.book.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BookCreateDTO {
    @NotBlank(message = "O título do livro é obrigatório.")
    private String bookTitle;
    private String pubYear;
    private String synopsis;
    private String cover;

    @NotNull(message = "O ID do autor é obrigatório.")
    private Long authorId;
}