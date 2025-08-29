package br.com.ifba.like.service;

import br.com.ifba.article.entity.Article;
import br.com.ifba.article.repository.ArticleRepository;
import br.com.ifba.comment.repository.CommentRepository;
import br.com.ifba.infrastructure.exception.ResourceNotFoundException;
import br.com.ifba.like.entity.Like;
import br.com.ifba.like.repository.LikeRepository;
import br.com.ifba.user.entity.User;
import br.com.ifba.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Classe de serviço que encapsula a lógica de negócio para Curtidas (Likes).
 * <p>
 * É responsável por gerenciar as ações de curtir e descurtir artigos e comentários,
 * aplicando as regras de negócio necessárias, como a prevenção de curtidas duplicadas.
 */
@Service
public class LikeService {

    /**
     * Repositório para operações de acesso a dados de Curtidas.
     */
    @Autowired
    private LikeRepository likeRepository;

    /**
     * Repositório de Usuários, usado para validar a existência do usuário que está curtindo.
     */
    @Autowired
    private UserRepository userRepository;

    /**
     * Repositório de Artigos, usado para validar a existência do artigo que está sendo curtido.
     */
    @Autowired
    private ArticleRepository articleRepository;

    /**
     * Repositório de Comentários (preparado para futuras implementações de curtidas em comentários).
     */
    @Autowired
    private CommentRepository commentRepository;

    /**
     * Cria uma nova 'curtida' (like) de um usuário em um artigo específico.
     *
     * @param articleId O ID do artigo a ser curtido.
     * @param userId    O ID do usuário que está curtindo.
     * @throws ResourceNotFoundException se o usuário ou o artigo não forem encontrados.
     * @throws IllegalStateException     se o usuário já tiver curtido este artigo.
     */
    @Transactional
    public void likeArticle(Long articleId, Long userId) {
        // Valida e busca as entidades User e Article
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com o ID: " + userId));
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new ResourceNotFoundException("Artigo não encontrado com o ID: " + articleId));

        // Regra de Negócio: Impede que um usuário curta o mesmo artigo mais de uma vez.
        if (likeRepository.findByUserIdAndArticleId(userId, articleId).isPresent()) {
            throw new IllegalStateException("Usuário já curtiu este artigo.");
        }

        // Cria e salva a nova entidade Like
        Like newLike = new Like();
        newLike.setUser(user);
        newLike.setArticle(article);

        likeRepository.save(newLike);
    }

    /**
     * Remove uma 'curtida' (unlike) de um usuário em um artigo.
     *
     * @param articleId O ID do artigo a ser descurtido.
     * @param userId    O ID do usuário que está removendo a curtida.
     * @throws ResourceNotFoundException se a curtida correspondente não for encontrada.
     */
    @Transactional
    public void unlikeArticle(Long articleId, Long userId) {
        // Busca a curtida específica pelo usuário e artigo
        Like like = likeRepository.findByUserIdAndArticleId(userId, articleId)
                .orElseThrow(() -> new ResourceNotFoundException("Curtida não encontrada para este usuário e artigo."));

        // Deleta a curtida encontrada
        likeRepository.delete(like);
    }

    /**
     * Calcula e retorna o número total de curtidas de um artigo específico.
     *
     * @param articleId O ID do artigo.
     * @return a quantidade total de curtidas (long).
     * @throws ResourceNotFoundException se o artigo com o ID especificado não for encontrado.
     */
    @Transactional(readOnly = true)
    public long getArticleLikeCount(Long articleId) {
        // Valida a existência do artigo antes de contar as curtidas
        if (!articleRepository.existsById(articleId)) {
            throw new ResourceNotFoundException("Artigo não encontrado com o ID: " + articleId);
        }
        return likeRepository.countByArticleId(articleId);
    }
}