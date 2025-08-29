package br.com.ifba.profile.controller;

import br.com.ifba.profile.dto.UserProfileResponseDTO;
import br.com.ifba.profile.dto.UserProfileUpdateDTO;
import br.com.ifba.profile.service.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller REST para gerenciar as operações relacionadas aos Perfis de Usuário.
 * <p>
 * Esta classe expõe os endpoints da API para consultar e atualizar os perfis dos usuários.
 */
@RestController
@RequestMapping("/api/profiles") // Define o caminho base para todos os endpoints deste controller
public class UserProfileController {

    /**
     * Injeção de dependência do UserProfileService, que contém a lógica de negócio para perfis.
     */
    @Autowired
    private UserProfileService userProfileService;

    /**
     * Endpoint para buscar o perfil público de um usuário pelo seu nome de usuário.
     * Acessível via requisição GET para /api/profiles/{username}.
     *
     * @param username O nome de usuário (username) do perfil a ser buscado.
     * @return um ResponseEntity com o DTO do perfil encontrado e o status HTTP 200 (OK).
     * @throws br.com.ifba.infrastructure.exception.ResourceNotFoundException se o perfil para o usuário especificado não for encontrado.
     */
    @GetMapping("/{username}")
    public ResponseEntity<UserProfileResponseDTO> getProfile(@PathVariable String username) {
        return ResponseEntity.ok(userProfileService.getProfileByUsername(username));
    }

    /**
     * Endpoint para atualizar as informações do perfil de um usuário.
     * Acessível via requisição PUT para /api/profiles/{username}.
     *
     * @param username  O nome de usuário do perfil a ser atualizado.
     * @param updateDTO DTO contendo os novos dados do perfil.
     * @return um ResponseEntity com o DTO do perfil atualizado e o status HTTP 200 (OK).
     * @throws br.com.ifba.infrastructure.exception.ResourceNotFoundException se o perfil não for encontrado.
     */
    @PutMapping("/{username}")
    public ResponseEntity<UserProfileResponseDTO> updateProfile(@PathVariable String username, @RequestBody UserProfileUpdateDTO updateDTO) {
        // NOTA: Em uma aplicação real, este endpoint seria protegido para garantir
        // que apenas o próprio usuário (ou um administrador) possa atualizar seu perfil.
        return ResponseEntity.ok(userProfileService.updateProfile(username, updateDTO));
    }
}