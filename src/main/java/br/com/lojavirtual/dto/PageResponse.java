package br.com.lojavirtual.dto;

import br.com.lojavirtual.model.Page;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PageResponse<T> {
    private Page infos;
    private List<T> resultados;
}
