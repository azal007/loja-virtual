package br.com.lojavirtual.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Page {
    private int numeroPagina;
    private int tamanhoPagina;
    private int totalElementos;
    private int totalPaginas;
}
