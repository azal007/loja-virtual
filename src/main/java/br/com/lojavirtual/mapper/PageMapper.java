package br.com.lojavirtual.mapper;

import br.com.lojavirtual.dto.PageResponse;
import br.com.lojavirtual.model.PageInfo;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PageMapper<T> {
    public PageResponse<T> toResponse(PageInfo pageInfo, List<T> content) {
        return new PageResponse<>(pageInfo, content);
    }
}
