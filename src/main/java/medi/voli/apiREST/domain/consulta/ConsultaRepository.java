package medi.voli.apiREST.domain.consulta;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface ConsultaRepository extends JpaRepository<Consulta,Long> {
    Boolean existsByPacienteIdAndDataBetween(Long idPaciente, LocalDateTime primeiroHorario, LocalDateTime ultimoHorario);


    Boolean existsByMedicoIdAndDataAndMotivoCancelamentoNull(Long idMedico, LocalDateTime data);
}
