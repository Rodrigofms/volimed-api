package medi.voli.apiREST.domain.consulta;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import medi.voli.apiREST.domain.medico.Especialidade;

import java.time.LocalDateTime;

public record DadosAgendamento(
        Long idMedico,

        @NotNull
        Long idPaciente,

        @NotNull
        @Future LocalDateTime data,
        Especialidade especialidade) {
}
