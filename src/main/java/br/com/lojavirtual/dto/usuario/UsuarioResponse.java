package br.com.lojavirtual.dto.usuario;

import br.com.lojavirtual.model.Page;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioResponse {
    private Long id;
    private String nome;
    private String apelido;
    private String cpf;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dataNascimento;
    private String email;
    private boolean habilitarNotificacoesPromocoes;
    private boolean ativo;
    private Date criadoEm;
    private Date atualizadoEm;
    private Page page;

    public UsuarioResponse(Long id, String nome, String apelido, String cpf, Date dataNascimento, String email, boolean habilitarNotificacoesPromocoes, boolean ativo, Date criadoEm, Date atualizadoEm) {
        this.id = id;
        this.nome = nome;
        this.apelido = apelido;
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
        this.email = email;
        this.habilitarNotificacoesPromocoes = habilitarNotificacoesPromocoes;
        this.ativo = ativo;
        this.criadoEm = criadoEm;
        this.atualizadoEm = atualizadoEm;
    }
}
