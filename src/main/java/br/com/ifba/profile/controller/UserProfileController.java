package br.com.ifba.profile.controller;

import br.com.ifba.profile.dto.UserProfileResponseDTO;
import br.com.ifba.profile.dto.UserProfileUpdateDTO;
import br.com.ifba.profile.service.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profiles")
public class UserProfileController {

    @Autowired
    private UserProfileService userProfileService;

    @GetMapping("/{username}")
    public ResponseEntity<UserProfileResponseDTO> getProfile(@PathVariable String username) {
        return ResponseEntity.ok(userProfileService.getProfileByUsername(username));
    }

    @PutMapping("/{username}")
    public ResponseEntity<UserProfileResponseDTO> updateProfile(@PathVariable String username, @RequestBody UserProfileUpdateDTO updateDTO) {
        return ResponseEntity.ok(userProfileService.updateProfile(username, updateDTO));
    }
}