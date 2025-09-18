package br.com.lojavirtual.repository;

import br.com.lojavirtual.model.Categoria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public class CategoriaDAO {
    @Autowired
    JdbcTemplate jdbcTemplate;

//    public List<Categoria> listar() {
//        return List.of(new Categoria(1L, "Hardware"), new Categoria(2L, "Periféricos"));
//    }

//    public void incluir(Categoria categoria) {
//        System.out.println("Incluindo nova categoria");
//    }

    public Categoria buscarPorId(Long id) {
        return jdbcTemplate.queryForObject("SELECT * FROM Categoria WHERE id = ? AND ativo = TRUE", new BeanPropertyRowMapper<Categoria>(Categoria.class), id);
    }

    public List<Categoria> listar(){
        return jdbcTemplate.query("SELECT * FROM Categoria WHERE ativo = TRUE", new BeanPropertyRowMapper<Categoria>(Categoria.class));
    }

    public int incluir(Categoria categoria) {
        return jdbcTemplate.update("INSERT INTO Categoria (nome, id_categoria_pai) VALUES (?, ?)", new Object[] {categoria.getNome(), categoria.getIdCategoriaPai()});
    }

    public int atualizar(Long id, Categoria categoria) {
        return jdbcTemplate.update("UPDATE Categoria SET nome = ?, ativo = ? WHERE  (id) = ?", new Object[] {categoria.getNome(), categoria.getAtivo(), id});
    }

    public int excluir(Long id) {
        return jdbcTemplate.update("UPDATE Categoria SET ativo = FALSE  WHERE id = ?", id);
    }

    public boolean obterFolhas(Categoria categoria) {
        return Boolean.TRUE.equals(jdbcTemplate.queryForObject("SELECT COUNT(id) = 0 FROM Categoria WHERE id_categoria_pai = ? AND ativo = TRUE", Boolean.class, categoria.getIdCategoriaPai()));
    }
}
