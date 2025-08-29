package br.com.ifba.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class CommentResponseDTO {
    private Long id;
    private String commentText;
    private Date commentDate;
    private Long authorId;
    private String authorName;
    private Long articleId;
}