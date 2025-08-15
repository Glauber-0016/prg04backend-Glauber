package br.com.ifba.usuario.controller;

import br.com.ifba.usuario.entity.Usuario;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
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

        // GET /usuarios - Retorna todos os usuários
        @GetMapping
        public ResponseEntity<List<Usuario>> getAll() {
            return ResponseEntity.ok(usuarios);
        }

        // POST /usuarios - Cria um novo usuário
        @PostMapping
        public ResponseEntity<Usuario> create(@RequestBody Usuario usuario) {
            usuario.setId(contadorId.incrementAndGet()); // Gera e define um novo ID
            usuarios.add(usuario);
            return ResponseEntity.status(HttpStatus.CREATED).body(usuario);
        }

        // GET /usuarios/{id} - Busca um usuário pelo ID
        @GetMapping("/{id}")
        public ResponseEntity<Usuario> getById(@PathVariable Long id) {
            Optional<Usuario> usuarioEncontrado = usuarios.stream()
                    .filter(u -> u.getId().equals(id))
                    .findFirst();


            return usuarioEncontrado.map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        }

        // PUT /usuarios/{id} - Atualiza um usuário existente
        @PutMapping("/{id}")
        public ResponseEntity<Usuario> update(@PathVariable Long id, @RequestBody Usuario usuarioDetails) {
            for (int i = 0; i < usuarios.size(); i++) {
                Usuario usuarioExistente = usuarios.get(i);
                if (usuarioExistente.getId().equals(id)) {

                    usuarioDetails.setId(id);
                    usuarios.set(i, usuarioDetails);
                    return ResponseEntity.ok(usuarioDetails);
                }
            }

            return ResponseEntity.notFound().build();
        }

        // DELETE /usuarios/{id} - Deleta um usuário pelo ID
        @DeleteMapping("/{id}")
        public ResponseEntity<Void> delete(@PathVariable Long id) {
            boolean foiRemovido = usuarios.removeIf(usuario -> usuario.getId().equals(id));

            if (foiRemovido) {
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        }
}