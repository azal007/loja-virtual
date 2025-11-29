package br.com.lojavirtual.mapper;

import br.com.lojavirtual.dto.PageResponse;
import br.com.lojavirtual.model.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PageMapper<T> {
    public PageResponse<T> toResponse(Page page, List<T> conteudo) {
        return new PageResponse<>(page, conteudo);
    }
}
