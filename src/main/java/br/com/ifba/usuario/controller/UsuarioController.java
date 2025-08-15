package br.com.ifba.usuario.controller;

import br.com.ifba.usuario.dto.UsuarioGetResponseDto;
import br.com.ifba.usuario.dto.UsuarioLoginDto;
import br.com.ifba.usuario.dto.UsuarioPostRequestDto;
import br.com.ifba.usuario.entity.Usuario;
import br.com.ifba.usuario.service.UsuarioService;
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
        Usuario novoUsuario = usuarioService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new UsuarioGetResponseDto(novoUsuario));
    }

    @GetMapping
    public ResponseEntity<List<UsuarioGetResponseDto>> getAll() {
        List<Usuario> usuarios = usuarioService.findAll();
        List<UsuarioGetResponseDto> responseList = usuarios.stream()
                .map(UsuarioGetResponseDto::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responseList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioGetResponseDto> getById(@PathVariable Long id) {
        Usuario usuario = usuarioService.findById(id);
        return ResponseEntity.ok(new UsuarioGetResponseDto(usuario));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioGetResponseDto> update(@PathVariable Long id, @Valid @RequestBody UsuarioPostRequestDto dto) {
        Usuario usuarioAtualizado = usuarioService.update(id, dto);
        return ResponseEntity.ok(new UsuarioGetResponseDto(usuarioAtualizado));
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