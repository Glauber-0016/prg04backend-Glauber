package br.com.ifba.user.service;

import br.com.ifba.infrastructure.exception.ResourceNotFoundException;
import br.com.ifba.profile.entity.UserProfile;
import br.com.ifba.user.dto.UserCreateDTO;
import br.com.ifba.user.dto.UserResponseDTO;
import br.com.ifba.user.entity.User;
import br.com.ifba.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Classe de serviço que encapsula a lógica de negócio para a entidade Usuário (User).
 * <p>
 * É responsável por operações como criação, consulta e deleção de usuários,
 * aplicando regras de negócio como validação de email e criptografia de senha.
 */
@Service
public class UserService {

    /**
     * Repositório para operações de acesso a dados de Usuários.
     */
    @Autowired
    private UserRepository userRepository;

    /**
     * Componente do Spring Security para codificar (fazer o hash) de senhas.
     */
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Busca e retorna uma lista de todos os usuários cadastrados.
     * A anotação @Transactional(readOnly = true) otimiza a consulta para operações de leitura.
     *
     * @return uma lista de UserResponseDTO contendo os dados de todos os usuários.
     */
    @Transactional(readOnly = true)
    public List<UserResponseDTO> findAll() {
        List<User> users = userRepository.findAll();
        // Converte a lista de entidades para uma lista de DTOs
        return users.stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Busca um usuário específico pelo seu ID.
     *
     * @param id O ID do usuário a ser buscado.
     * @return um UserResponseDTO com os dados do usuário encontrado.
     * @throws ResourceNotFoundException se nenhum usuário for encontrado com o ID fornecido.
     */
    @Transactional(readOnly = true)
    public UserResponseDTO findById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com o ID: " + id));
        return toResponseDTO(user);
    }

    /**
     * Cria (registra) um novo usuário no sistema.
     *
     * @param createDTO DTO com os dados de criação do novo usuário.
     * @return um UserResponseDTO com os dados do usuário recém-criado.
     * @throws IllegalArgumentException se o email fornecido já estiver em uso.
     */
    @Transactional
    public UserResponseDTO createUser(UserCreateDTO createDTO) {
        // Regra de Negócio: Impede o cadastro de usuários com emails duplicados
        if (userRepository.existsByEmail(createDTO.getEmail())) {
            throw new IllegalArgumentException("O email informado já está em uso.");
        }

        // Mapeia os dados do DTO para a nova entidade User
        User user = new User();
        user.setName(createDTO.getName());
        user.setEmail(createDTO.getEmail());
        // Regra de Negócio: A senha é criptografada antes de ser salva
        user.setPassword(passwordEncoder.encode(createDTO.getPassword()));
        // Regra de Negócio: Todo novo usuário é criado com a permissão de "LEITOR"
        user.setRole("LEITOR");

        // Regra de Negócio: Cria e associa um perfil de usuário vazio automaticamente
        UserProfile newProfile = new UserProfile();
        user.setUserProfile(newProfile);
        newProfile.setUser(user);

        // Salva o novo usuário (e o perfil, via cascade) no banco de dados
        User savedUser = userRepository.save(user);

        // Converte e retorna o DTO de resposta
        return toResponseDTO(savedUser);
    }

    /**
     * Deleta um usuário do sistema com base no seu ID.
     *
     * @param id O ID do usuário a ser deletado.
     * @throws ResourceNotFoundException se nenhum usuário for encontrado com o ID fornecido.
     */
    @Transactional
    public void deleteUser(Long id) {
        // Garante que o usuário existe antes de tentar deletar
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("Usuário não encontrado com o ID: " + id);
        }
        userRepository.deleteById(id);
    }

    /**
     * Método auxiliar privado para converter uma entidade {@link User} em um {@link UserResponseDTO}.
     *
     * @param user A entidade User a ser convertida.
     * @return O DTO correspondente, contendo apenas os dados públicos do usuário.
     */
    private UserResponseDTO toResponseDTO(User user) {
        return new UserResponseDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole(),
                user.getProfilePic()
        );
    }
}