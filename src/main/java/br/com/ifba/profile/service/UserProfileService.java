package br.com.ifba.profile.service;

import br.com.ifba.infrastructure.exception.ResourceNotFoundException;
import br.com.ifba.profile.dto.UserProfileResponseDTO;
import br.com.ifba.profile.dto.UserProfileUpdateDTO;
import br.com.ifba.profile.entity.UserProfile;
import br.com.ifba.profile.repository.UserProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Classe de serviço que encapsula a lógica de negócio para Perfis de Usuário.
 * <p>
 * Responsável por operações de consulta e atualização de perfis,
 * servindo como intermediária entre a camada de Controller e a de Repository.
 */
@Service
public class UserProfileService {

    /**
     * Injeção de dependência do repositório de perfis para acesso aos dados.
     */
    @Autowired
    private UserProfileRepository userProfileRepository;

    /**
     * Busca e retorna os dados públicos do perfil de um usuário com base no nome de usuário.
     * A anotação @Transactional(readOnly = true) otimiza a consulta para operações de leitura.
     *
     * @param username O nome de usuário do perfil a ser buscado.
     * @return um UserProfileResponseDTO com os dados do perfil encontrado.
     * @throws ResourceNotFoundException se nenhum perfil for encontrado para o usuário especificado.
     */
    @Transactional(readOnly = true)
    public UserProfileResponseDTO getProfileByUsername(String username) {
        // O método findByUserNome busca o perfil navegando através da entidade User relacionada
        // e filtrando pelo campo 'nome' da entidade User.
        UserProfile profile = userProfileRepository.findByUserName(username)
                .orElseThrow(() -> new ResourceNotFoundException("Perfil não encontrado para o usuário: " + username));
        return toResponseDTO(profile);
    }

    /**
     * Atualiza as informações de um perfil de usuário existente.
     *
     * @param username  O nome de usuário do perfil a ser atualizado.
     * @param updateDTO DTO contendo os novos dados a serem salvos no perfil.
     * @return um UserProfileResponseDTO com os dados do perfil após a atualização.
     * @throws ResourceNotFoundException se o perfil não for encontrado.
     */
    @Transactional
    public UserProfileResponseDTO updateProfile(String username, UserProfileUpdateDTO updateDTO) {
        // Busca o perfil existente no banco de dados
        UserProfile profile = userProfileRepository.findByUserName(username)
                .orElseThrow(() -> new ResourceNotFoundException("Perfil não encontrado para o usuário: " + username));

        // NOTA: Em uma aplicação real, aqui haveria uma verificação de segurança
        // para garantir que o usuário logado é o dono deste perfil ou um administrador.

        // Atualiza os campos da entidade com os dados do DTO
        profile.setBio(updateDTO.getBio());
        profile.setLocation(updateDTO.getLocation());
        profile.setBirthDate(updateDTO.getBirthDate());

        // Salva a entidade atualizada no banco de dados
        UserProfile updatedProfile = userProfileRepository.save(profile);

        // Converte e retorna o DTO de resposta
        return toResponseDTO(updatedProfile);
    }

    /**
     * Método auxiliar privado para converter uma entidade {@link UserProfile} em um {@link UserProfileResponseDTO}.
     *
     * @param profile A entidade UserProfile a ser convertida.
     * @return O DTO correspondente, enriquecido com dados do usuário associado.
     */
    private UserProfileResponseDTO toResponseDTO(UserProfile profile) {
        return new UserProfileResponseDTO(
                profile.getUser().getId(),
                profile.getUser().getName(),
                profile.getBio(),
                profile.getLocation(),
                profile.getBirthDate()
        );
    }
}