package br.com.ifba.author.service;

import br.com.ifba.author.dto.AuthorCreateDTO;
import br.com.ifba.author.dto.AuthorResponseDTO;
import br.com.ifba.author.entity.Author;
import br.com.ifba.author.repository.AuthorRepository;
import br.com.ifba.infrastructure.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    @Transactional
    public AuthorResponseDTO createAuthor(AuthorCreateDTO createDTO) {
        Author newAuthor = new Author();
        newAuthor.setAuthorName(createDTO.getAuthorName());
        newAuthor.setBiography(createDTO.getBiography());
        newAuthor.setAuthorPhoto(createDTO.getAuthorPhoto());

        Author savedAuthor = authorRepository.save(newAuthor);
        return toResponseDTO(savedAuthor);
    }

    @Transactional(readOnly = true)
    public List<AuthorResponseDTO> findAllAuthors() {
        return authorRepository.findAll().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public AuthorResponseDTO findAuthorById(Long id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Autor não encontrado com o ID: " + id));
        return toResponseDTO(author);
    }

    private AuthorResponseDTO toResponseDTO(Author author) {
        return new AuthorResponseDTO(
                author.getId(),
                author.getAuthorName(),
                author.getBiography(),
                author.getAuthorPhoto()
        );
    }
}