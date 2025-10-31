package br.com.lojavirtual.service;

import br.com.lojavirtual.dto.usuario.UsuarioRequest;
import br.com.lojavirtual.dto.usuario.UsuarioResponse;
import br.com.lojavirtual.dto.usuario.UsuarioUpdateRequest;
import br.com.lojavirtual.exception.BusinessException;
import br.com.lojavirtual.exception.EntityNotFoundException;
import br.com.lojavirtual.mapper.UsuarioMapper;
import br.com.lojavirtual.model.Usuario;
import br.com.lojavirtual.repository.UsuarioDAO;
import jakarta.transaction.Transactional;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {
    private final UsuarioDAO usuarioDAO;
    private final UsuarioMapper usuarioMapper;

    public UsuarioService (UsuarioDAO usuarioDAO, UsuarioMapper usuarioMapper) {
        this.usuarioDAO = usuarioDAO;
        this.usuarioMapper = usuarioMapper;
    }

    public UsuarioResponse buscarPorId(Long id) {
        return usuarioMapper.toResponse(validaBuscarPorId(id));
    }

    public List<UsuarioResponse> listar(String nome, String cpf, String email, Boolean ativo, Integer numeroPagina, Integer tamanhoPagina) {
        List<Usuario> usuario = usuarioDAO.listar(nome, cpf, email, ativo, numeroPagina, tamanhoPagina);

        return usuario.stream().map(usuarioMapper::toResponse).toList();
    }

    public UsuarioResponse incluir(UsuarioRequest request) {
        String email = request.getEmail();
        Usuario usuario = usuarioMapper.toEntity(request);

        validaPossuiMesmoEmail(email);

        return usuarioMapper.toResponse(usuarioDAO.incluir(usuario));
    }

    @Transactional
    public UsuarioResponse atualizar(Long id, UsuarioUpdateRequest request) {
        String email = request.getEmail();

        validaBuscarPorId(id);
        validaPossuiMesmoEmail(email);

        Usuario usuario = usuarioMapper.toEntityUpdate(request);
        return usuarioMapper.toResponse(usuarioDAO.atualizar(id, usuario));
    }

    @Transactional
    public void excluir(Long id) {
        validaBuscarPorId(id);
        usuarioDAO.excluir(id);
    }

    private Usuario validaBuscarPorId(Long id) {
        try {
            return usuarioDAO.buscarPorId(id);
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException(Usuario.class.getSimpleName(), id);
        }
    }

    private void validaPossuiMesmoEmail(String email) {
        Boolean possuiMesmoEmail = usuarioDAO.validaPossuiMesmoEmail(email);
        if (possuiMesmoEmail) {
            throw new BusinessException("O email informado já existe.");
        }
    }
}
