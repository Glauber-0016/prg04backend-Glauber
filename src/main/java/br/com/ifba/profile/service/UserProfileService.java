package br.com.ifba.profile.service;

import br.com.ifba.infrastructure.exception.ResourceNotFoundException;
import br.com.ifba.profile.dto.UserProfileResponseDTO;
import br.com.ifba.profile.dto.UserProfileUpdateDTO;
import br.com.ifba.profile.entity.UserProfile;
import br.com.ifba.profile.repository.UserProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserProfileService {

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Transactional(readOnly = true)
    public UserProfileResponseDTO getProfileByUsername(String username) {
        UserProfile profile = userProfileRepository.findByUserName(username)
                .orElseThrow(() -> new ResourceNotFoundException("Perfil não encontrado para o usuário: " + username));
        return toResponseDTO(profile);
    }

    @Transactional
    public UserProfileResponseDTO updateProfile(String username, UserProfileUpdateDTO updateDTO) {
        UserProfile profile = userProfileRepository.findByUserName(username)
                .orElseThrow(() -> new ResourceNotFoundException("Perfil não encontrado para o usuário: " + username));


        profile.setBio(updateDTO.getBio());
        profile.setLocation(updateDTO.getLocation());
        profile.setBirthDate(updateDTO.getBirthDate());

        UserProfile updatedProfile = userProfileRepository.save(profile);
        return toResponseDTO(updatedProfile);
    }

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