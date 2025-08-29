package br.com.ifba.book.service;

import br.com.ifba.author.entity.Author;
import br.com.ifba.author.repository.AuthorRepository;
import br.com.ifba.book.dto.BookCreateDTO;
import br.com.ifba.book.dto.BookResponseDTO;
import br.com.ifba.book.entity.Book;
import br.com.ifba.book.repository.BookRepository;
import br.com.ifba.infrastructure.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Classe de serviço que contém a lógica de negócio para a entidade Livro (Book).
 * <p>
 * Responsável por operações como criação e consulta de livros, coordenando
 * com os repositórios necessários.
 */
@Service
public class BookService {

    /**
     * Repositório para operações de acesso a dados de Livros.
     */
    @Autowired
    private BookRepository bookRepository;

    /**
     * Repositório para operações de acesso a dados de Autores.
     * Necessário para buscar e validar o autor ao criar um novo livro.
     */
    @Autowired
    private AuthorRepository authorRepository;

    /**
     * Cria um novo livro no banco de dados, associando-o a um autor existente.
     * A anotação @Transactional garante que a operação seja atômica.
     *
     * @param createDTO DTO com os dados para a criação do novo livro.
     * @return um BookResponseDTO com os dados do livro recém-criado.
     * @throws ResourceNotFoundException se o autor com o ID especificado no DTO não for encontrado.
     */
    @Transactional
    public BookResponseDTO createBook(BookCreateDTO createDTO) {
        // Valida se o autor informado existe no banco de dados
        Author author = authorRepository.findById(createDTO.getAuthorId())
                .orElseThrow(() -> new ResourceNotFoundException("Autor não encontrado com o ID: " + createDTO.getAuthorId()));

        // Cria e popula a nova entidade Book com os dados do DTO
        Book newBook = new Book();
        newBook.setBookTitle(createDTO.getBookTitle());
        newBook.setPubYear(createDTO.getPubYear());
        newBook.setSynopsis(createDTO.getSynopsis());
        newBook.setCover(createDTO.getCover());
        newBook.setAuthor(author); // Associa a entidade Author ao livro

        // Salva o novo livro no banco de dados
        Book savedBook = bookRepository.save(newBook);

        // Converte a entidade salva em um DTO de resposta e o retorna
        return toResponseDTO(savedBook);
    }

    /**
     * Busca e retorna uma lista de todos os livros cadastrados.
     * A anotação @Transactional(readOnly = true) otimiza a consulta para operações de leitura.
     *
     * @return uma lista de BookResponseDTO contendo os dados de todos os livros.
     */
    @Transactional(readOnly = true)
    public List<BookResponseDTO> findAllBooks() {
        return bookRepository.findAll().stream()
                .map(this::toResponseDTO) // Usa o método auxiliar para converter cada entidade
                .collect(Collectors.toList());
    }

    /**
     * Método auxiliar privado para converter uma entidade {@link Book} em um {@link BookResponseDTO}.
     * <p>
     * Este método centraliza a lógica de mapeamento e enriquece o DTO com informações
     * do autor associado, como seu ID e nome.
     *
     * @param book A entidade Book a ser convertida.
     * @return O DTO correspondente.
     */
    private BookResponseDTO toResponseDTO(Book book) {
        return new BookResponseDTO(
                book.getId(),
                book.getBookTitle(),
                book.getPubYear(),
                book.getSynopsis(),
                book.getCover(),
                book.getAuthor().getId(),
                book.getAuthor().getAuthorName()
        );
    }
}