package br.com.lojavirtual.dto.user;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {
    private Long id;
    @NotEmpty
    private String email;
    @NotEmpty
    private String password;
}
