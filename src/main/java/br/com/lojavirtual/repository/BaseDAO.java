package br.com.lojavirtual.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

@Slf4j
public abstract class BaseDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private final String tabela;

    public BaseDAO() {
        this.tabela = obterNomeEntidade() + "s";
//        log.info("Nome da tabela: " +  this.tabela);
    }

    public String obterNomeEntidade () {
        String nomeEntidade = getClass().getSimpleName();
        int tamanho = nomeEntidade.length();

        if (nomeEntidade.endsWith("DAO")) {
            nomeEntidade = nomeEntidade.substring(0, tamanho - 3);
        }
        return nomeEntidade;
    }
    public Boolean verificaPossuiMesmoNome(String nome) {
        return jdbcTemplate.queryForObject("SELECT EXISTS (SELECT 1 FROM " + this.tabela + " WHERE nome = ?)", Boolean.class, nome);
    }
}
