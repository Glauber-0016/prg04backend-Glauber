package br.com.ifba.usuario.controller;

import br.com.ifba.usuario.entity.Usuario;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 *
 * @author Glauber
 */

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final List<Usuario> usuarios = Collections.synchronizedList(new ArrayList<>());
    private final AtomicLong contadorId = new AtomicLong();

    @GetMapping
    public ResponseEntity<List<Usuario>> getAll() {
        return ResponseEntity.ok(usuarios);
    }

    @PostMapping
    public ResponseEntity<Usuario> create(@RequestBody Usuario usuario) {
        usuario.setId(contadorId.incrementAndGet());
        usuarios.add(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuario);
    }
}