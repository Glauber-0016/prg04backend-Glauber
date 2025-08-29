package br.com.ifba.article.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class ArticleResponseDTO {
    private Long id;
    private String title;
    private String content;
    private Date publishDate;
    private String status;
    private Long authorId;
    private String authorName;
}