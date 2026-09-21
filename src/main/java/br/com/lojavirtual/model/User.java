package br.com.lojavirtual.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Long id;
    private String name;
    private String nickname;
    private String cpf;
    private Date birthDate;
    private String email;
    private String password;
    private Boolean enablePromotionalNotifications;
    private Boolean admin;
    private Boolean active;
    private Date createdAt;
    private Date updatedAt;
}
