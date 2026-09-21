package br.com.lojavirtual.dto.user;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserPatchRequest {
    private String name;
    private String nickname;
    private String cpf;
    private Date birthDate;
    private String email;
    private Boolean enablePromotionalNotifications;
}
