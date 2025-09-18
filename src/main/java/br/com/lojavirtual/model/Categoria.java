package br.com.lojavirtual.model;

public class Categoria {
        private Long id;
        private String nome;
        private Long idCategoriaPai;
        private Boolean ativo;

    // polimorfismo de sobrecarga
    // polimorfismo de sobreescrita
    public Categoria() {}

    public Categoria(Long id, String nome, Long idCategoriaPai, Boolean ativo) {
        this.id = id;
        this.nome = nome;
        this.idCategoriaPai = idCategoriaPai;
        this.ativo = ativo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Long getIdCategoriaPai() {
        return idCategoriaPai;
    }

    public void setIdCategoriaPai(Long idCategoriaPai) {
        this.idCategoriaPai = idCategoriaPai;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }
}
