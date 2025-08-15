package br.com.ifba.usuario.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class UsuarioLoginDto {

    @NotBlank(message = "O nome é obrigatório.")
    private String nome;

    @NotBlank(message = "A senha é obrigatória.")
    private String senha;

}