package br.com.ifba.book.controller;

import br.com.ifba.author.entity.Author;
import br.com.ifba.author.repository.AutorRepository;
import br.com.ifba.book.entity.Book;
import br.com.ifba.book.repository.LivroRepository;
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
    public ResponseEntity<?> createLivro(@RequestBody Book book) {
        if (book.getAutor() == null || book.getAutor().getId() == null) {
            return ResponseEntity.badRequest().body("O autor é obrigatório.");
        }

        Optional<Author> autorOptional = autorRepository.findById(book.getAutor().getId());
        if (autorOptional.isEmpty()) {
            return ResponseEntity.badRequest().body("Autor com ID " + book.getAutor().getId() + " não encontrado.");
        }

        book.setAutor(autorOptional.get());
        Book novoBook = livroRepository.save(book);
        return new ResponseEntity<>(novoBook, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Book>> getAllLivros() {
        List<Book> books = livroRepository.findAll();
        return ResponseEntity.ok(books);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Book> getLivroById(@PathVariable Long id) {
        return livroRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateLivro(@PathVariable Long id, @RequestBody Book bookDetails) {
        Optional<Book> livroOptional = livroRepository.findById(id);

        if (livroOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Book bookExistente = livroOptional.get();
        bookExistente.setTitulo(bookDetails.getTitulo());
        bookExistente.setAnoPublicacao(bookDetails.getAnoPublicacao());


        if (bookDetails.getAutor() != null && bookDetails.getAutor().getId() != null) {
            Optional<Author> autorOptional = autorRepository.findById(bookDetails.getAutor().getId());
            if (autorOptional.isEmpty()) {
                return ResponseEntity.badRequest().body("Autor com ID " + bookDetails.getAutor().getId() + " não encontrado.");
            }
            bookExistente.setAutor(autorOptional.get());
        }

        Book bookAtualizado = livroRepository.save(bookExistente);
        return ResponseEntity.ok(bookAtualizado);
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