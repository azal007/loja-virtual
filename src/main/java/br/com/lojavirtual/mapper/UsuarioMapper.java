package br.com.lojavirtual.mapper;

import br.com.lojavirtual.dto.usuario.UsuarioPatchRequest;
import br.com.lojavirtual.dto.usuario.UsuarioRequest;
import br.com.lojavirtual.dto.usuario.UsuarioResponse;
import br.com.lojavirtual.dto.usuario.UsuarioUpdateRequest;
import br.com.lojavirtual.model.Page;
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
                usuario.isHabilitarNotificacoesPromocoes(),
                usuario.isAtivo(),
                usuario.getCriadoEm(),
                usuario.getCriadoEm()
        );
    }

    public UsuarioResponse toResponse(Usuario usuario, Page page) {
        return new UsuarioResponse(
                usuario.getId(),
                usuario.getNome(),
                usuario.getApelido(),
                usuario.getCpf(),
                usuario.getDataNascimento(),
                usuario.getEmail(),
                usuario.isHabilitarNotificacoesPromocoes(),
                usuario.isAtivo(),
                usuario.getCriadoEm(),
                usuario.getCriadoEm(),
                new Page(
                        page.getNumeroPagina(),
                        page.getTamanhoPagina(),
                        page.getTotalElementos(),
                        page.getTotalPaginas()
                )
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

    public Usuario toEntityUpdate(UsuarioUpdateRequest request) {
        return new Usuario(
                null,
                request.getNome(),
                request.getApelido(),
                request.getCpf(),
                request.getDataNascimento(),
                request.getEmail(),
                null,
                request.getHabilitarNotificacoesPromocoes(),
                Boolean.TRUE,
                null,
                null
        );
    }

    public Usuario toEntityPatch(UsuarioPatchRequest request) {
        return new Usuario(
                null,
                request.getNome(),
                request.getApelido(),
                request.getCpf(),
                request.getDataNascimento(),
                request.getEmail(),
                null,
                request.getHabilitarNotificacoesPromocoes(),
                Boolean.TRUE,
                null,
                null
        );
    }
}
