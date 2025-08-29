package br.com.ifba.book.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BookResponseDTO {
    private Long id;
    private String bookTitle;
    private String pubYear;
    private String synopsis;
    private String cover;
    private Long authorId;
    private String authorName;
}