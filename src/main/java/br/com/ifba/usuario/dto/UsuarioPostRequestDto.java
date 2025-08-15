package br.com.ifba.usuario.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class UsuarioPostRequestDto {

    @JsonProperty(value = "nome")
    @NotBlank(message = "O Nome é obrigatório.")
    private String nome;

    @JsonProperty(value = "email")
    @NotBlank(message = "O email é obrigatório.")
    @Email(message = "O email deve ser válido.")
    private String email;

    @JsonProperty(value = "senha")
    @NotBlank(message = "A senha é obrigatória.")
    @Length(min = 8, message = "A senha deve ter no mínimo 8 caracteres.")
    private String senha;
}