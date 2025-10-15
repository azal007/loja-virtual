package br.com.lojavirtual.service;

import br.com.lojavirtual.dto.UsuarioRequest;
import br.com.lojavirtual.dto.UsuarioResponse;
import br.com.lojavirtual.mapper.UsuarioMapper;
import br.com.lojavirtual.model.Usuario;
import br.com.lojavirtual.repository.UsuarioDAO;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {
    private final UsuarioDAO usuarioDAO;
    private final UsuarioMapper usuarioMapper;

    public UsuarioService (UsuarioDAO usuarioDAO, UsuarioMapper usuarioMapper) {
        this.usuarioDAO = usuarioDAO;
        this.usuarioMapper = usuarioMapper;
    }

    public UsuarioResponse incluir(UsuarioRequest request) {
        Usuario usuario = usuarioMapper.toEntity(request);
        return usuarioMapper.toResponse(usuarioDAO.incluir(usuario));
    }
}
