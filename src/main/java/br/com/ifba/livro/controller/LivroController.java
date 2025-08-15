package br.com.ifba.livro.controller;

import br.com.ifba.autor.entity.Autor;
import br.com.ifba.autor.repository.AutorRepository;
import br.com.ifba.livro.entity.Livro;
import br.com.ifba.livro.repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/livros")
public class LivroController {

    @Autowired
    private LivroRepository livroRepository;

    @Autowired
    private AutorRepository autorRepository;

    @PostMapping
    public ResponseEntity<?> createLivro(@RequestBody Livro livro) {
        if (livro.getAutor() == null || livro.getAutor().getId() == null) {
            return ResponseEntity.badRequest().body("O autor é obrigatório.");
        }

        Optional<Autor> autorOptional = autorRepository.findById(livro.getAutor().getId());
        if (autorOptional.isEmpty()) {
            return ResponseEntity.badRequest().body("Autor com ID " + livro.getAutor().getId() + " não encontrado.");
        }

        livro.setAutor(autorOptional.get());
        Livro novoLivro = livroRepository.save(livro);
        return new ResponseEntity<>(novoLivro, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Livro>> getAllLivros() {
        List<Livro> livros = livroRepository.findAll();
        return ResponseEntity.ok(livros);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Livro> getLivroById(@PathVariable Long id) {
        return livroRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateLivro(@PathVariable Long id, @RequestBody Livro livroDetails) {
        Optional<Livro> livroOptional = livroRepository.findById(id);

        if (livroOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Livro livroExistente = livroOptional.get();
        livroExistente.setTitulo(livroDetails.getTitulo());
        livroExistente.setAnoPublicacao(livroDetails.getAnoPublicacao());


        if (livroDetails.getAutor() != null && livroDetails.getAutor().getId() != null) {
            Optional<Autor> autorOptional = autorRepository.findById(livroDetails.getAutor().getId());
            if (autorOptional.isEmpty()) {
                return ResponseEntity.badRequest().body("Autor com ID " + livroDetails.getAutor().getId() + " não encontrado.");
            }
            livroExistente.setAutor(autorOptional.get());
        }

        Livro livroAtualizado = livroRepository.save(livroExistente);
        return ResponseEntity.ok(livroAtualizado);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLivro(@PathVariable Long id) {
        if (!livroRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        livroRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }



}