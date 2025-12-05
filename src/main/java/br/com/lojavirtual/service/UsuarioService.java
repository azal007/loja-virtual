package br.com.lojavirtual.service;

import br.com.lojavirtual.dto.PageResponse;
import br.com.lojavirtual.dto.usuario.UsuarioPatchRequest;
import br.com.lojavirtual.dto.usuario.UsuarioRequest;
import br.com.lojavirtual.dto.usuario.UsuarioResponse;
import br.com.lojavirtual.dto.usuario.UsuarioUpdateRequest;
import br.com.lojavirtual.exception.BusinessException;
import br.com.lojavirtual.exception.EntityNotFoundException;
import br.com.lojavirtual.mapper.PageMapper;
import br.com.lojavirtual.mapper.UsuarioMapper;
import br.com.lojavirtual.model.Page;
import br.com.lojavirtual.model.Usuario;
import br.com.lojavirtual.repository.UsuarioDAO;
import jakarta.transaction.Transactional;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService extends BaseService<UsuarioDAO>{
    private final UsuarioDAO usuarioDAO;
    private final UsuarioMapper usuarioMapper;
    private final PageMapper<UsuarioResponse> usuarioResponsePageMapper;

    public UsuarioService (UsuarioDAO usuarioDAO, UsuarioMapper usuarioMapper, PageMapper<UsuarioResponse> usuarioResponsePageMapper) {
        super(usuarioDAO);
        this.usuarioDAO = usuarioDAO;
        this.usuarioMapper = usuarioMapper;
        this.usuarioResponsePageMapper = usuarioResponsePageMapper;
    }

    public UsuarioResponse buscarPorId(Long id) {
        return usuarioMapper.toResponse(validaBuscarPorId(id));
    }

//    public PageResponse<UsuarioResponse> listar(String nome, String cpf, String email, Boolean ativo, Integer numeroPagina, Integer tamanhoPagina) {
//        List<Usuario> usuario = usuarioDAO.listar(nome, cpf, email, ativo, numeroPagina, tamanhoPagina);
//        int totalElementos = obterTotalElementos();
//        int totalPaginas = (int) Math.ceil((double) totalElementos / tamanhoPagina);
//
//        Page page = new Page(numeroPagina, tamanhoPagina, totalElementos, totalPaginas);
//
//        return usuarioResponsePageMapper.toResponse(
//                page,
//                usuario.stream().map(usuarioMapper::toResponse).toList()
//        );
//    }

    public UsuarioResponse incluir(UsuarioRequest request) {
        String email = request.getEmail();
        Long id = request.getId();

        // TODO: REVISAR
        validaPossuiMesmoEmail(email, id);

        Usuario usuario = usuarioMapper.toEntity(request);

        return usuarioMapper.toResponse(usuarioDAO.incluir(usuario));
    }

    @Transactional
    public UsuarioResponse atualizar(Long id, UsuarioUpdateRequest request) {
        String email = request.getEmail();

        validaBuscarPorId(id);
        validaPossuiMesmoEmail(email, id);

        Usuario usuario = usuarioMapper.toEntityUpdate(request);
        return usuarioMapper.toResponse(usuarioDAO.atualizar(id, usuario));
    }

    @Transactional
    public UsuarioResponse atualizarParcial(Long id, UsuarioPatchRequest request) {
        String email = request.getEmail();

        validaBuscarPorId(id);
        validaPossuiMesmoEmail(email, id);

        Usuario usuario = usuarioMapper.toEntityPatch(request);
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

    private void validaPossuiMesmoEmail(String email, Long id) {
        Boolean possuiMesmoEmail = usuarioDAO.validaPossuiMesmoEmail(email, id);
        if (possuiMesmoEmail) {
            throw new BusinessException("O email informado já existe.");
        }
    }
}
