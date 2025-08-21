package br.com.ifba.author.controller;

import br.com.ifba.author.entity.Author;
import br.com.ifba.author.repository.AutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/autores")
public class AutorController {

    @Autowired
    private AutorRepository autorRepository;

    @PostMapping
    public ResponseEntity<Author> createAutor(@RequestBody Author author) {
        Author novoAuthor = autorRepository.save(author);
        return new ResponseEntity<>(novoAuthor, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Author>> getAllAutores() {
        List<Author> autores = autorRepository.findAll();
        return ResponseEntity.ok(autores);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Author> getAutorById(@PathVariable Long id) {
        Optional<Author> autorOptional = autorRepository.findById(id);
        return autorOptional.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Author> updateAutor(@PathVariable Long id, @RequestBody Author authorDetails) {
        Optional<Author> autorOptional = autorRepository.findById(id);
        if (autorOptional.isPresent()) {
            Author authorExistente = autorOptional.get();
            authorExistente.setNome(authorDetails.getNome());
            Author authorAtualizado = autorRepository.save(authorExistente);
            return ResponseEntity.ok(authorAtualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAutor(@PathVariable Long id) {
        if (autorRepository.existsById(id)) {
            autorRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}