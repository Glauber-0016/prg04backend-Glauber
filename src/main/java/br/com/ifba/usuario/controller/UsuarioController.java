package br.com.ifba.usuario.controller;

import br.com.ifba.infrastructure.exception.ResourceNotFoundException;
import br.com.ifba.usuario.dto.UsuarioGetResponseDto;
import br.com.ifba.usuario.dto.UsuarioLoginDto;
import br.com.ifba.usuario.dto.UsuarioPostRequestDto;
import br.com.ifba.usuario.entity.Usuario;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    private final List<Usuario> usuarios = Collections.synchronizedList(new ArrayList<>());
    private final AtomicLong contadorId = new AtomicLong();

    @PostMapping
    public ResponseEntity<UsuarioGetResponseDto> create(@Valid @RequestBody UsuarioPostRequestDto dto) {
        Usuario novoUsuario = new Usuario();
        novoUsuario.setId(contadorId.incrementAndGet());
        novoUsuario.setNome(dto.getNome());
        novoUsuario.setSenha(dto.getSenha());
        usuarios.add(novoUsuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(new UsuarioGetResponseDto(novoUsuario.getNome(), novoUsuario.getSenha()));
    }

    @GetMapping
    public ResponseEntity<List<UsuarioGetResponseDto>> getAll() {
        List<UsuarioGetResponseDto> responseList = usuarios.stream()
                .map(usuario -> new UsuarioGetResponseDto(usuario.getNome(), usuario.getSenha()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(responseList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioGetResponseDto> getById(@PathVariable Long id) {
        Usuario usuario = usuarios.stream()
                .filter(u -> u.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com o ID: " + id));

        return ResponseEntity.ok(new UsuarioGetResponseDto(usuario.getNome(), usuario.getSenha()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioGetResponseDto> update(@PathVariable Long id, @Valid @RequestBody UsuarioPostRequestDto dto) {
        Usuario usuarioExistente = usuarios.stream()
                .filter(u -> u.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com o ID: " + id));

        usuarioExistente.setNome(dto.getNome());
        usuarioExistente.setSenha(dto.getSenha());
        return ResponseEntity.ok(new UsuarioGetResponseDto(usuarioExistente.getNome(), usuarioExistente.getSenha()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Usuario usuarioParaDeletar = usuarios.stream()
                .filter(u -> u.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com o ID: " + id));

        usuarios.remove(usuarioParaDeletar);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody UsuarioLoginDto loginDto) {
        usuarios.stream()
                .filter(u -> u.getNome().equals(loginDto.getNome()) && u.getSenha().equals(loginDto.getSenha()))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Nome de usuário ou senha inválidos."));

        return ResponseEntity.ok("Login bem-sucedido!");
    }
}