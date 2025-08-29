package br.com.ifba.article.service;

import br.com.ifba.article.dto.ArticleCreateDTO;
import br.com.ifba.article.dto.ArticleResponseDTO;
import br.com.ifba.article.entity.Article;
import br.com.ifba.article.repository.ArticleRepository;
import br.com.ifba.infrastructure.exception.ResourceNotFoundException;
import br.com.ifba.user.entity.User;
import br.com.ifba.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Classe de serviço para gerenciar a lógica de negócio relacionada aos Artigos.
 * <p>
 * Esta classe é responsável por coordenar as operações entre o Controller e o Repository,
 * aplicando as regras de negócio necessárias.
 */
@Service
public class ArticleService {

    /**
     * Repositório para operações de acesso a dados de Artigos.
     */
    @Autowired
    private ArticleRepository articleRepository;

    /**
     * Repositório para operações de acesso a dados de Usuários.
     * Necessário para validar e associar o autor ao criar um artigo.
     */
    @Autowired
    private UserRepository userRepository;

    /**
     * Cria um novo artigo no sistema.
     *
     * @param createDTO DTO com os dados de criação do artigo.
     * @param authorId  O ID do usuário que está criando o artigo.
     * @return um DTO com os dados do artigo recém-criado.
     * @throws ResourceNotFoundException se o usuário autor não for encontrado.
     * @throws IllegalStateException     se o usuário não tiver a permissão de 'ADMIN'.
     */
    @Transactional
    public ArticleResponseDTO createArticle(ArticleCreateDTO createDTO, Long authorId) {
        // Busca a entidade do autor no banco de dados
        User author = userRepository.findById(authorId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário autor não encontrado com o ID: " + authorId));

        // Regra de Negócio: Apenas usuários com a role 'ADMIN' podem criar artigos
        if (!"ADMIN".equals(author.getRole())) {
            throw new IllegalStateException("Apenas administradores podem criar artigos.");
        }

        // Cria uma nova instância da entidade Article
        Article newArticle = new Article();
        newArticle.setTitle(createDTO.getTitle());
        newArticle.setContent(createDTO.getContent());
        newArticle.setUser(author); // Associa a entidade User ao artigo
        newArticle.setStatus("RASCUNHO"); // Define um status inicial padrão

        // Salva a nova entidade no banco de dados
        Article savedArticle = articleRepository.save(newArticle);

        // Converte a entidade salva para um DTO de resposta e a retorna
        return toResponseDTO(savedArticle);
    }

    /**
     * Busca uma lista paginada de todos os artigos.
     * A anotação @Transactional(readOnly = true) otimiza a consulta, pois indica que nenhuma escrita será feita.
     *
     * @param pageable Objeto contendo as informações de paginação (página, tamanho, ordenação).
     * @return uma Página (Page) de DTOs de artigo.
     */
    @Transactional(readOnly = true)
    public Page<ArticleResponseDTO> findAll(Pageable pageable) {
        Page<Article> articlePage = articleRepository.findAll(pageable);
        // Mapeia a página de entidades para uma página de DTOs usando o método auxiliar
        return articlePage.map(this::toResponseDTO);
    }


    /**
     * Busca um artigo específico pelo seu ID.
     *
     * @param id O ID do artigo a ser buscado.
     * @return um DTO com os dados do artigo encontrado.
     * @throws ResourceNotFoundException se nenhum artigo for encontrado com o ID fornecido.
     */
    @Transactional(readOnly = true)
    public ArticleResponseDTO findById(Long id) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Artigo não encontrado com o ID: " + id));
        return toResponseDTO(article);
    }

    /**
     * Método auxiliar privado para converter uma entidade {@link Article} em um {@link ArticleResponseDTO}.
     * <p>
     * Este método centraliza a lógica de mapeamento, evitando duplicação de código e
     * garantindo que a entidade do banco de dados não seja exposta diretamente na API.
     *
     * @param article A entidade Article a ser convertida.
     * @return O DTO correspondente.
     */
    private ArticleResponseDTO toResponseDTO(Article article) {
        return new ArticleResponseDTO(
                article.getId(),
                article.getTitle(),
                article.getContent(),
                article.getPublishDate(),
                article.getStatus(),
                article.getUser().getId(),
                article.getUser().getName()
        );
    }
}