package br.com.ifba.author.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthorResponseDTO {
    private Long id;
    private String authorName;
    private String biography;
    private String authorPhoto;
}