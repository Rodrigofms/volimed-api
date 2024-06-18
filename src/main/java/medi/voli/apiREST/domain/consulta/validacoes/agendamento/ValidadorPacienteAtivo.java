package medi.voli.apiREST.domain.consulta.validacoes.agendamento;

import medi.voli.apiREST.domain.ValidacaoException;
import medi.voli.apiREST.domain.consulta.DadosAgendamento;
import medi.voli.apiREST.domain.paciente.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorPacienteAtivo implements ValidadorAgendamentoConsulta{

    @Autowired
    private PacienteRepository repository;

    public void validar(DadosAgendamento dados) {
        if (dados.idMedico() == null) {
            return;
        }

        var pacienteEstaAtivo = repository.findAtivoById(dados.idPaciente());
        if (!pacienteEstaAtivo) {
            throw new ValidacaoException("Consulta nao pode ser agendada com medico indisponivel");
        }
    }
}
