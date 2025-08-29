package br.com.ifba.profile.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.Date;

@Data
@AllArgsConstructor
public class UserProfileResponseDTO {
    private Long userId;
    private String username;
    private String bio;
    private String location;
    private Date birthDate;
}