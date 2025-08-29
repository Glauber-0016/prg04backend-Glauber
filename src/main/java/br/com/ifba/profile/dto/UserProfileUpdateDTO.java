package br.com.ifba.profile.dto;

import lombok.Data;
import java.util.Date;

@Data
public class UserProfileUpdateDTO {
    private String bio;
    private String location;
    private Date birthDate;
}