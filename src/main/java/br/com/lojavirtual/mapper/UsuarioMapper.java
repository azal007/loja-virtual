package br.com.lojavirtual.mapper;

import br.com.lojavirtual.dto.UsuarioRequest;
import br.com.lojavirtual.dto.UsuarioResponse;
import br.com.lojavirtual.model.Usuario;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {

    public UsuarioResponse toResponse(Usuario usuario) {
        return new UsuarioResponse(
                usuario.getId(),
                usuario.getNome(),
                usuario.getApelido(),
                usuario.getCpf(),
                usuario.getDataNascimento(),
                usuario.getEmail(),
                usuario.getSenha(),
                usuario.isHabilitarNotificacoesPromocoes(),
                usuario.isAtivo(),
                usuario.getCriadoEm(),
                usuario.getCriadoEm()
        );
    }

    public Usuario toEntity(UsuarioRequest request) {
        return new Usuario(
                null,
                null,
                null,
                null,
                null,
                request.getEmail(),
                request.getSenha(),
                Boolean.FALSE,
                Boolean.TRUE,
                null,
                null
        );
    }
}
