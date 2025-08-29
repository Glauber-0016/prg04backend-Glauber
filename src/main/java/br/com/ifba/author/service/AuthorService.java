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

/**
 * Classe de serviço que encapsula a lógica de negócio para a entidade Author.
 * <p>
 * Esta classe é responsável por todas as operações relacionadas a autores,
 * servindo como intermediária entre a camada de Controller e a de Repository.
 */
@Service
public class AuthorService {

    /**
     * Injeção de dependência do repositório de autores para acesso aos dados.
     */
    @Autowired
    private AuthorRepository authorRepository;

    /**
     * Cria um novo autor no banco de dados com base nos dados fornecidos.
     * A anotação @Transactional garante que a operação seja atômica.
     *
     * @param createDTO DTO contendo as informações do novo autor a ser criado.
     * @return um AuthorResponseDTO com os dados do autor recém-criado.
     */
    @Transactional
    public AuthorResponseDTO createAuthor(AuthorCreateDTO createDTO) {
        // Mapeia os dados do DTO para uma nova entidade Author
        Author newAuthor = new Author();
        newAuthor.setAuthorName(createDTO.getAuthorName());
        newAuthor.setBiography(createDTO.getBiography());
        newAuthor.setAuthorPhoto(createDTO.getAuthorPhoto());

        // Salva a nova entidade no banco de dados
        Author savedAuthor = authorRepository.save(newAuthor);

        // Converte a entidade salva em um DTO de resposta e retorna
        return toResponseDTO(savedAuthor);
    }

    /**
     * Busca e retorna uma lista de todos os autores cadastrados.
     * A anotação @Transactional(readOnly = true) otimiza a performance para operações de leitura.
     *
     * @return uma lista de AuthorResponseDTO contendo os dados de todos os autores.
     */
    @Transactional(readOnly = true)
    public List<AuthorResponseDTO> findAllAuthors() {
        return authorRepository.findAll().stream()
                .map(this::toResponseDTO) // Converte cada entidade Author para seu DTO correspondente
                .collect(Collectors.toList());
    }

    /**
     * Busca um autor específico pelo seu identificador único (ID).
     *
     * @param id O ID do autor a ser buscado.
     * @return um AuthorResponseDTO com os dados do autor encontrado.
     * @throws ResourceNotFoundException se nenhum autor for encontrado com o ID fornecido.
     */
    @Transactional(readOnly = true)
    public AuthorResponseDTO findAuthorById(Long id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Autor não encontrado com o ID: " + id));
        return toResponseDTO(author);
    }

    /**
     * Método auxiliar privado para converter uma entidade {@link Author} em um {@link AuthorResponseDTO}.
     * <p>
     * Centraliza a lógica de mapeamento para evitar duplicação de código e garantir que
     * a entidade de banco de dados não seja exposta diretamente na API.
     *
     * @param author A entidade Author a ser convertida.
     * @return O AuthorResponseDTO correspondente.
     */
    private AuthorResponseDTO toResponseDTO(Author author) {
        return new AuthorResponseDTO(
                author.getId(),
                author.getAuthorName(),
                author.getBiography(),
                author.getAuthorPhoto()
        );
    }
}