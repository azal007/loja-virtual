package br.com.lojavirtual.security.service;

import br.com.lojavirtual.exception.BusinessException;
import br.com.lojavirtual.model.Usuario;
import br.com.lojavirtual.repository.UsuarioDAO;
import br.com.lojavirtual.security.dto.LoginRequest;
import br.com.lojavirtual.security.dto.LoginResponse;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UsuarioDAO usuarioDAO;
    private final JwtService jwtService;

    public AuthService(UsuarioDAO usuarioDAO, JwtService jwtService) {
        this.usuarioDAO = usuarioDAO;
        this.jwtService = jwtService;
    }

    public LoginResponse autenticar(LoginRequest request) {
        Usuario usuario = usuarioDAO.buscarPorEmail(request.getEmail());

        if (usuario == null) {
            throw new BusinessException("Credenciais invalidas");
        }

        if (!usuario.isAtivo()) {
            throw new BusinessException("Usuario inativo");
        }

        // Comparacao de senha em texto plano (conforme solicitado)
        if (!request.getSenha().equals(usuario.getSenha())) {
            throw new BusinessException("Credenciais invalidas");
        }

        String token = jwtService.generateToken(usuario.getEmail(), usuario.getId());

        return new LoginResponse(
            token,
            "Bearer",
            usuario.getId(),
            usuario.getEmail(),
            usuario.getNome()
        );
    }
}
