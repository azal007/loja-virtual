package br.com.lojavirtual.service;

import br.com.lojavirtual.dto.PageResponse;
import br.com.lojavirtual.dto.user.UserPatchRequest;
import br.com.lojavirtual.dto.user.UserRequest;
import br.com.lojavirtual.dto.user.UserResponse;
import br.com.lojavirtual.dto.user.UserUpdateRequest;
import br.com.lojavirtual.exception.BusinessException;
import br.com.lojavirtual.exception.EntityNotFoundException;
import br.com.lojavirtual.mapper.PageMapper;
import br.com.lojavirtual.mapper.UserMapper;
import br.com.lojavirtual.model.PageInfo;
import br.com.lojavirtual.model.User;
import br.com.lojavirtual.repository.UserDAO;
import jakarta.transaction.Transactional;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService extends BaseService<UserDAO>{
    private final UserDAO userDAO;
    private final UserMapper userMapper;
    private final PageMapper<UserResponse> userResponsePageMapper;

    public UserService (UserDAO userDAO, UserMapper userMapper, PageMapper<UserResponse> userResponsePageMapper) {
        super(userDAO);
        this.userDAO = userDAO;
        this.userMapper = userMapper;
        this.userResponsePageMapper = userResponsePageMapper;
    }

    public UserResponse findById(Long id) {
        return userMapper.toResponse(validateFindById(id));
    }

    public PageResponse<UserResponse> list(String name, String cpf, String email, Boolean active, Integer pageNumber, Integer pageSize) {
        List<User> users = userDAO.list(name, cpf, email, active, pageNumber, pageSize);

        int totalElements = userDAO.countList(name, cpf, email, active);
        int totalPages = (int) Math.ceil((double) totalElements / pageSize);

        PageInfo pageInfo = new PageInfo(pageNumber, pageSize, totalElements, totalPages);

        return userResponsePageMapper.toResponse(
                pageInfo,
                users.stream().map(userMapper::toResponse).toList()
        );
    }

    public UserResponse insert(UserRequest request) {
        String email = request.getEmail();
        Long id = request.getId();

        validateHasSameEmail(email, id);

        User user = userMapper.toEntity(request);

        return userMapper.toResponse(userDAO.insert(user));
    }

    @Transactional
    public UserResponse update(Long id, UserUpdateRequest request) {
        String email = request.getEmail();

        validateFindById(id);
        validateHasSameEmail(email, id);

        User user = userMapper.toEntityUpdate(request);
        return userMapper.toResponse(userDAO.update(id, user));
    }

    @Transactional
    public UserResponse updatePartial(Long id, UserPatchRequest request) {
        String email = request.getEmail();

        validateFindById(id);
        validateHasSameEmail(email, id);

        User user = userMapper.toEntityPatch(request);
        return userMapper.toResponse(userDAO.update(id, user));
    }

    @Transactional
    public void delete(Long id) {
        validateFindById(id);
        userDAO.delete(id);
    }

    private User validateFindById(Long id) {
        try {
            return userDAO.findById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException(User.class.getSimpleName(), id);
        }
    }

    private void validateHasSameEmail(String email, Long id) {
        Boolean hasSameEmail = userDAO.hasSameEmail(email, id);
        if (hasSameEmail) {
            throw new BusinessException("The informed email already exists.");
        }
    }
}
