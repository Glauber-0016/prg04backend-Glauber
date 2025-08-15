package br.com.ifba.usuario.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioPostRequestDto {

    @JsonProperty(value = "id")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long id;

    @JsonProperty(value = "nome")
    @NotBlank (message = "O Nome é obrigatório.")
    @NotNull (message = "O Nome não pode estar vazio.")
    private String nome;


    @JsonProperty(value = "senha")
    @NotBlank (message = "A senha é obrigatória.")
    @NotNull (message = "A senha não pode estar vazia.")
    @Length(min = 8, max = 100)
    private String senha;
}