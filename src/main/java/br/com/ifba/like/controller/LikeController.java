package br.com.ifba.like.controller;

import br.com.ifba.like.dto.LikeDTO;
import br.com.ifba.like.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Controller REST para gerenciar as operações de Curtidas (Likes).
 * <p>
 * Esta classe expõe endpoints para que os usuários possam curtir, descurtir
 * e consultar a quantidade de curtidas em artigos.
 */
@RestController
@RequestMapping("/api") // Define o caminho base
public class LikeController {

    /**
     * Injeção de dependência do LikeService, que contém a lógica de negócio para curtidas.
     */
    @Autowired
    private LikeService likeService;

    /**
     * Endpoint para um usuário curtir um artigo.
     * Acessível via requisição POST para /api/articles/{articleId}/like.
     *
     * @param articleId O ID do artigo que está sendo curtido.
     * @param likeDTO   DTO contendo o ID do usuário que está curtindo.
     * @return um ResponseEntity com status HTTP 200 (OK) se a operação for bem-sucedida.
     * @throws br.com.ifba.infrastructure.exception.ResourceNotFoundException se o artigo ou usuário não forem encontrados.
     * @throws IllegalStateException se o usuário já tiver curtido este artigo.
     */
    @PostMapping("/articles/{articleId}/like")
    public ResponseEntity<Void> likeArticle(@PathVariable Long articleId, @RequestBody LikeDTO likeDTO) {
        // NOTA: Em uma aplicação real com segurança, o 'userId' viria do usuário LOGADO,
        // e não do corpo da requisição.
        likeService.likeArticle(articleId, likeDTO.getUserId());
        return ResponseEntity.ok().build();
    }

    /**
     * Endpoint para um usuário remover sua curtida de um artigo (descurtir).
     * Acessível via requisição DELETE para /api/articles/{articleId}/like.
     *
     * @param articleId O ID do artigo que está sendo descurtido.
     * @param likeDTO   DTO contendo o ID do usuário que está removendo a curtida.
     * @return um ResponseEntity com status HTTP 204 (No Content), indicando sucesso na remoção.
     * @throws br.com.ifba.infrastructure.exception.ResourceNotFoundException se a curtida correspondente não for encontrada.
     */
    @DeleteMapping("/articles/{articleId}/like")
    public ResponseEntity<Void> unlikeArticle(@PathVariable Long articleId, @RequestBody LikeDTO likeDTO) {
        likeService.unlikeArticle(articleId, likeDTO.getUserId());
        return ResponseEntity.noContent().build();
    }

    /**
     * Endpoint para obter a contagem total de curtidas de um artigo específico.
     * Acessível via requisição GET para /api/articles/{articleId}/likes.
     *
     * @param articleId O ID do artigo cuja contagem de curtidas será consultada.
     * @return um ResponseEntity contendo um Mapa com a chave "likeCount" e o número de curtidas,
     * e o status HTTP 200 (OK).
     */
    @GetMapping("/articles/{articleId}/likes")
    public ResponseEntity<Map<String, Long>> getArticleLikes(@PathVariable Long articleId) {
        long count = likeService.getArticleLikeCount(articleId);
        // Retorna um JSON no formato: { "likeCount": valor }
        return ResponseEntity.ok(Map.of("likeCount", count));
    }
}