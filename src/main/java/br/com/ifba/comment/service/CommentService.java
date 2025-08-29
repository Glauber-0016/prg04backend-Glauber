package br.com.ifba.comment.service;

import br.com.ifba.article.entity.Article;
import br.com.ifba.article.repository.ArticleRepository;
import br.com.ifba.comment.dto.CommentCreateDTO;
import br.com.ifba.comment.dto.CommentResponseDTO;
import br.com.ifba.comment.entity.Comment;
import br.com.ifba.comment.repository.CommentRepository;
import br.com.ifba.infrastructure.exception.ResourceNotFoundException;
import br.com.ifba.user.entity.User;
import br.com.ifba.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Classe de serviço que encapsula a lógica de negócio para a entidade Comentário (Comment).
 * <p>
 * É responsável por operações como criar e buscar comentários, garantindo que
 * todas as regras de negócio e validações de integridade sejam aplicadas.
 */
@Service
public class CommentService {

    /**
     * Repositório para operações de acesso a dados de Comentários.
     */
    @Autowired
    private CommentRepository commentRepository;

    /**
     * Repositório para operações de acesso a dados de Usuários.
     * Utilizado para validar e associar o autor do comentário.
     */
    @Autowired
    private UserRepository userRepository;

    /**
     * Repositório para operações de acesso a dados de Artigos.
     * Utilizado para validar e associar o artigo ao qual o comentário pertence.
     */
    @Autowired
    private ArticleRepository articleRepository;

    /**
     * Cria um novo comentário em um artigo, associando-o a um usuário existente.
     * A anotação @Transactional garante que a operação seja atômica.
     *
     * @param createDTO DTO com os dados para a criação do novo comentário.
     * @return um CommentResponseDTO com os dados do comentário recém-criado.
     * @throws ResourceNotFoundException se o usuário ou o artigo especificados no DTO não forem encontrados.
     */
    @Transactional
    public CommentResponseDTO createComment(CommentCreateDTO createDTO) {
        // Valida e busca a entidade User (autor do comentário)
        User author = userRepository.findById(createDTO.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com o ID: " + createDTO.getUserId()));

        // Valida e busca a entidade Article (onde o comentário será postado)
        Article article = articleRepository.findById(createDTO.getArticleId())
                .orElseThrow(() -> new ResourceNotFoundException("Artigo não encontrado com o ID: " + createDTO.getArticleId()));

        // Cria e popula a nova entidade Comment
        Comment newComment = new Comment();
        newComment.setCommentText(createDTO.getCommentText());
        newComment.setUser(author);
        newComment.setArticle(article);
        newComment.setApproved(true); // Para simplificar, aprova o comentário automaticamente

        // Salva a nova entidade no banco de dados
        Comment savedComment = commentRepository.save(newComment);

        // Converte e retorna o DTO de resposta
        return toResponseDTO(savedComment);
    }

    /**
     * Busca e retorna uma lista de todos os comentários de um artigo específico.
     * A anotação @Transactional(readOnly = true) otimiza a performance para operações de leitura.
     *
     * @param articleId O ID do artigo cujos comentários serão buscados.
     * @return uma lista de CommentResponseDTO.
     * @throws ResourceNotFoundException se o artigo com o ID especificado não existir.
     */
    @Transactional(readOnly = true)
    public List<CommentResponseDTO> findCommentsByArticleId(Long articleId) {
        // Regra de negócio: antes de buscar os comentários, verifica se o artigo existe
        if (!articleRepository.existsById(articleId)) {
            throw new ResourceNotFoundException("Artigo não encontrado com o ID: " + articleId);
        }

        // Busca no repositório e converte a lista de entidades para uma lista de DTOs
        return commentRepository.findByArticleId(articleId)
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Método auxiliar privado para converter uma entidade {@link Comment} em um {@link CommentResponseDTO}.
     *
     * @param comment A entidade Comment a ser convertida.
     * @return O DTO correspondente.
     */
    private CommentResponseDTO toResponseDTO(Comment comment) {
        return new CommentResponseDTO(
                comment.getId(),
                comment.getCommentText(),
                comment.getCommentDate(),
                comment.getUser().getId(),
                comment.getUser().getName(), // Supondo que o User tem um método getName()
                comment.getArticle().getId()
        );
    }
}