package br.com.lojavirtual.security.service;

import br.com.lojavirtual.exception.BusinessException;
import br.com.lojavirtual.model.User;
import br.com.lojavirtual.repository.UserDAO;
import br.com.lojavirtual.security.dto.LoginRequest;
import br.com.lojavirtual.security.dto.LoginResponse;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserDAO userDAO;
    private final JwtService jwtService;

    public AuthService(UserDAO userDAO, JwtService jwtService) {
        this.userDAO = userDAO;
        this.jwtService = jwtService;
    }

    public LoginResponse authenticate(LoginRequest request) {
        User user = userDAO.findByEmail(request.getEmail());

        if (user == null) {
            throw new BusinessException("Invalid credentials");
        }

        if (!user.getActive()) {
            throw new BusinessException("Inactive user");
        }

        if (!request.getPassword().equals(user.getPassword())) {
            throw new BusinessException("Invalid credentials");
        }

        String token = jwtService.generateToken(user.getEmail(), user.getId());

        return new LoginResponse(
            token,
            "Bearer",
            user.getId(),
            user.getEmail(),
            user.getName(),
            user.getAdmin()
        );
    }
}
