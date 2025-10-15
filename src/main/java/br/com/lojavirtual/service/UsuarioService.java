package br.com.lojavirtual.service;

import br.com.lojavirtual.dto.UsuarioRequest;
import br.com.lojavirtual.dto.UsuarioResponse;
import br.com.lojavirtual.exception.EntityNotFoundException;
import br.com.lojavirtual.mapper.UsuarioMapper;
import br.com.lojavirtual.model.Usuario;
import br.com.lojavirtual.repository.UsuarioDAO;
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
        try {
            return usuarioMapper.toResponse(usuarioDAO.buscarPorId(id));
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException(Usuario.class.getSimpleName(), id);
        }

    }

    public List<UsuarioResponse> listar(String nome, String cpf, String email, Boolean ativo) {
        List<Usuario> usuario = usuarioDAO.listar(nome, cpf, email, ativo);

        return usuario.stream().map(usuarioMapper::toResponse).toList();
    }

    public UsuarioResponse incluir(UsuarioRequest request) {
        Usuario usuario = usuarioMapper.toEntity(request);
        return usuarioMapper.toResponse(usuarioDAO.incluir(usuario));
    }
}
