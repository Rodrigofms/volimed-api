package medi.voli.apiREST.domain.consulta;


import jakarta.validation.constraints.NotNull;

public record DadosCancelamento(
    @NotNull
    Long idConsulta,

    @NotNull
    MotivoCancelamento motivo) {
}
