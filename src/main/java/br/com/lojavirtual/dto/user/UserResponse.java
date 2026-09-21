package br.com.lojavirtual.dto.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private Long id;
    private String name;
    private String nickname;
    private String cpf;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date birthDate;
    private String email;
    private Boolean enablePromotionalNotifications;
    private Boolean admin;
    private Boolean active;
    private Date createdAt;
    private Date updatedAt;
}
