package br.com.lojavirtual.dto.categoria;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoriaUpdateRequest {
    @NotEmpty
    private String nome;
    @NotNull
    private Long idCategoriaPai;
}
