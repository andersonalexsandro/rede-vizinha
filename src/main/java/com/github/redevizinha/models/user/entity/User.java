package com.github.redevizinha.models.user.entity;

import com.github.redevizinha.models.connection.entity.Connection;
import com.github.redevizinha.models.directMessage.entity.DirectMessage;
import com.github.redevizinha.utils.BaseEntity;
import jakarta.validation.constraints.*;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "users")
public class User extends BaseEntity{

    @NotBlank(message = "O nome não pode estar vazio")
    @Size(min = 3, max = 100, message = "O nome deve ter entre 3 e 100 caracteres")
    @Column(nullable = false, length = 100)
    private String name;

    @NotBlank(message = "O username não pode estar vazio")
    @Size(min = 3, max = 50, message = "O username deve ter entre 3 e 50 caracteres")
    @Column(nullable = false, length = 50, unique = true)
    private String username;

    @NotBlank(message = "O email não pode estar vazio")
    @Email(message = "Email deve ser válido")
    @Column(nullable = false, unique = true, length = 120)
    private String email;

    @NotBlank(message = "A senha não pode estar vazia")
    @Size(min = 8, message = "A senha deve ter pelo menos 8 caracteres")
    @Column(nullable = false)
    private String password;

    @Pattern(regexp = "\\+?[0-9]{10,15}", message = "Telefone deve conter entre 10 e 15 dígitos (pode começar com +)")
    @Column(length = 20)
    private String phone;

    @Past(message = "A data de nascimento deve estar no passado")
    @Temporal(TemporalType.DATE)
    @Column(name = "birth_date")
    private Date birthDate;

    @Size(max = 255, message = "A URL da foto não pode ter mais que 255 caracteres")
    @Column(name = "photo_url", length = 255)
    private String photoUrl;

    @Size(max = 500, message = "A bio pode ter no máximo 500 caracteres")
    private String bio;

    @Column(name = "is_public_profile", nullable = false)
    private boolean isPublicProfile = true;

    @OneToMany(mappedBy = "requester", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Connection> sentConnections = new HashSet<>();

    @OneToMany(mappedBy = "receiver", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Connection> receivedConnections = new HashSet<>();

    @OneToMany(mappedBy = "sender", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<DirectMessage> sentMessages = new HashSet<>();

    @OneToMany(mappedBy = "receiver", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<DirectMessage> receivedMessages = new HashSet<>();
}
