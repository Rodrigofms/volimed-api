package medi.voli.apiREST.domain.paciente;

import jakarta.validation.constraints.NotNull;
import medi.voli.apiREST.domain.endereco.DadosEndereco;


public record DadosAtualizacaoPaciente(
        @NotNull
        Long id,
        String nome,
        String telefone,
        DadosEndereco endereco) {
}
