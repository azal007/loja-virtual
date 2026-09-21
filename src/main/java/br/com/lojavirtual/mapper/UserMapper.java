package br.com.lojavirtual.mapper;

import br.com.lojavirtual.dto.user.UserPatchRequest;
import br.com.lojavirtual.dto.user.UserRequest;
import br.com.lojavirtual.dto.user.UserResponse;
import br.com.lojavirtual.dto.user.UserUpdateRequest;
import br.com.lojavirtual.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserResponse toResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getNickname(),
                user.getCpf(),
                user.getBirthDate(),
                user.getEmail(),
                user.getEnablePromotionalNotifications(),
                user.getAdmin(),
                user.getActive(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }

    public User toEntity(UserRequest request) {
        return new User(
                null,
                null,
                null,
                null,
                null,
                request.getEmail(),
                request.getPassword(),
                Boolean.FALSE,
                Boolean.FALSE,
                Boolean.TRUE,
                null,
                null
        );
    }

    public User toEntityUpdate(UserUpdateRequest request) {
        return new User(
                null,
                request.getName(),
                request.getNickname(),
                request.getCpf(),
                request.getBirthDate(),
                request.getEmail(),
                null,
                request.getEnablePromotionalNotifications(),
                Boolean.FALSE,
                Boolean.TRUE,
                null,
                null
        );
    }

    public User toEntityPatch(UserPatchRequest request) {
        return new User(
                null,
                request.getName(),
                request.getNickname(),
                request.getCpf(),
                request.getBirthDate(),
                request.getEmail(),
                null,
                request.getEnablePromotionalNotifications(),
                Boolean.FALSE,
                Boolean.TRUE,
                null,
                null
        );
    }
}
