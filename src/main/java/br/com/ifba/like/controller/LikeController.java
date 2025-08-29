package br.com.ifba.like.controller;

import br.com.ifba.like.dto.LikeDTO;
import br.com.ifba.like.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class LikeController {

    @Autowired
    private LikeService likeService;

    @PostMapping("/articles/{articleId}/like")
    public ResponseEntity<Void> likeArticle(@PathVariable Long articleId, @RequestBody LikeDTO likeDTO) {
        likeService.likeArticle(articleId, likeDTO.getUserId());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/articles/{articleId}/like")
    public ResponseEntity<Void> unlikeArticle(@PathVariable Long articleId, @RequestBody LikeDTO likeDTO) {
        likeService.unlikeArticle(articleId, likeDTO.getUserId());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/articles/{articleId}/likes")
    public ResponseEntity<Map<String, Long>> getArticleLikes(@PathVariable Long articleId) {
        long count = likeService.getArticleLikeCount(articleId);
        return ResponseEntity.ok(Map.of("likeCount", count));
    }
}