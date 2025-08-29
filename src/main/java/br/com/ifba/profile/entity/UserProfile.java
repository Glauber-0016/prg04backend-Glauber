// Em: br/com/ifba/profile/entity/UserProfile.java (crie um novo pacote 'profile')

package br.com.ifba.profile.entity;

import br.com.ifba.infrastructure.entity.PersistenceEntity;
import br.com.ifba.user.entity.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Entity
@Table(name = "user_profiles")
@Data
@EqualsAndHashCode(callSuper = true)
public class UserProfile extends PersistenceEntity {
    @Lob
    private String bio;

    private String location;

    @Temporal(TemporalType.DATE)
    private Date birthDate;


    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;
}