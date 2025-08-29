package br.com.ifba.user.controller;

import br.com.ifba.user.dto.UserCreateDTO;
import br.com.ifba.user.dto.UserResponseDTO;
import br.com.ifba.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller REST para gerenciar as operações relacionadas a Usuários.
 * <p>
 * Esta classe expõe os endpoints da API para criar, consultar e deletar usuários.
 */
@RestController
@RequestMapping("/api/users") // Define o caminho base para todos os endpoints deste controller
public class UserController {

    /**
     * Injeção de dependência do UserService, que contém a lógica de negócio para usuários.
     */
    @Autowired
    private UserService userService;

    /**
     * Endpoint para buscar uma lista de todos os usuários cadastrados.
     * Acessível via requisição GET para /api/users.
     *
     * @return um ResponseEntity com a lista de DTOs de usuários e o status HTTP 200 (OK).
     */
    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.findAll());
    }

    /**
     * Endpoint para buscar um usuário específico pelo seu ID.
     * Acessível via requisição GET para /api/users/{id}.
     *
     * @param id O ID do usuário a ser buscado.
     * @return um ResponseEntity com o DTO do usuário encontrado e o status HTTP 200 (OK).
     * @throws br.com.ifba.infrastructure.exception.ResourceNotFoundException se o usuário não for encontrado.
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    /**
     * Endpoint para criar (registrar) um novo usuário.
     * Acessível via requisição POST para /api/users.
     *
     * @param createDTO DTO contendo os dados para a criação do usuário, validado.
     * @return um ResponseEntity com o DTO do usuário criado e o status HTTP 201 (Created).
     * @throws IllegalArgumentException se o email fornecido já estiver em uso.
     */
    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@Valid @RequestBody UserCreateDTO createDTO) {
        UserResponseDTO createdUser = userService.createUser(createDTO);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    /**
     * Endpoint para deletar um usuário pelo seu ID.
     * Acessível via requisição DELETE para /api/users/{id}.
     *
     * @param id O ID do usuário a ser deletado.
     * @return um ResponseEntity com status HTTP 204 (No Content) indicando sucesso.
     * @throws br.com.ifba.infrastructure.exception.ResourceNotFoundException se o usuário não for encontrado.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}