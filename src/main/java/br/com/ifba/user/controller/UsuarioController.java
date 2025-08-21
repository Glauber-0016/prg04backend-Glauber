package br.com.ifba.user.controller;

import br.com.ifba.user.dto.UsuarioGetResponseDto;
import br.com.ifba.user.dto.UsuarioLoginDto;
import br.com.ifba.user.dto.UsuarioPostRequestDto;
import br.com.ifba.user.entity.User;
import br.com.ifba.user.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<UsuarioGetResponseDto> create(@Valid @RequestBody UsuarioPostRequestDto dto) {
        User novoUser = usuarioService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new UsuarioGetResponseDto(novoUser));
    }

    @GetMapping
    public ResponseEntity<List<UsuarioGetResponseDto>> getAll() {
        List<User> users = usuarioService.findAll();
        List<UsuarioGetResponseDto> responseList = users.stream()
                .map(UsuarioGetResponseDto::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responseList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioGetResponseDto> getById(@PathVariable Long id) {
        User user = usuarioService.findById(id);
        return ResponseEntity.ok(new UsuarioGetResponseDto(user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioGetResponseDto> update(@PathVariable Long id, @Valid @RequestBody UsuarioPostRequestDto dto) {
        User userAtualizado = usuarioService.update(id, dto);
        return ResponseEntity.ok(new UsuarioGetResponseDto(userAtualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        usuarioService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody UsuarioLoginDto loginDto) {
        usuarioService.login(loginDto.getNome(), loginDto.getSenha());
        return ResponseEntity.ok("Login bem-sucedido!");
    }
}