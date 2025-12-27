package br.com.lojavirtual.security.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {

    @NotEmpty(message = "Email e obrigatorio")
    @Email(message = "Email invalido")
    private String email;

    @NotEmpty(message = "Senha e obrigatoria")
    private String senha;
}
