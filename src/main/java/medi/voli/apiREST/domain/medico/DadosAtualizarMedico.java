package medi.voli.apiREST.domain.medico;

import jakarta.validation.constraints.NotNull;
import medi.voli.apiREST.domain.endereco.DadosEndereco;

public record DadosAtualizarMedico(
        @NotNull
        Long id,
        String nome,
        String telefone,
        DadosEndereco endereco) {
}
