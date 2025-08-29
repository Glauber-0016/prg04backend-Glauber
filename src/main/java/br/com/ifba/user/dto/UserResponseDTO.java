package br.com.ifba.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserResponseDTO {
    private Long id;
    private String nome;
    private String email;
    private String role;
    private String profilePic;
}