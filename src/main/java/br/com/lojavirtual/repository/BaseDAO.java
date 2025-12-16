package br.com.lojavirtual.repository;

import br.com.lojavirtual.exception.IntegrationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Objects;

@Slf4j
public abstract class BaseDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private final String tabela;

    public BaseDAO() {
        this.tabela = obterNomeEntidade().toLowerCase();
    }

    public String obterNomeEntidade () {
        String nomeEntidade = getClass().getSimpleName();
        int tamanho = nomeEntidade.length();

        if (nomeEntidade.endsWith("DAO")) {
            nomeEntidade = nomeEntidade.substring(0, tamanho - 3);
        }
        return nomeEntidade;
    }

    public Boolean verificaPossuiMesmoNome(String nome, Long id) {
        try {
            return jdbcTemplate.queryForObject("SELECT EXISTS (SELECT 1 FROM " + this.tabela + " WHERE nome = ? AND id <> ?)", Boolean.class, nome, id);
        } catch (Exception e) {
            log.error("Ocorreu um erro ao verificar se existe algum registro de {} com mesmo nome do id informado ({}).", this.tabela, id, e);
            throw new IntegrationException();
        }
    }
}
