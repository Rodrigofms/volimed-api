package medi.voli.apiREST.domain.consulta.validacoes.agendamento;

import medi.voli.apiREST.domain.ValidacaoException;
import medi.voli.apiREST.domain.consulta.ConsultaRepository;
import medi.voli.apiREST.domain.consulta.DadosAgendamento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorPacienteSemOutraConsulta implements ValidadorAgendamentoConsulta{

    @Autowired
    private ConsultaRepository repository;

    public void validar(DadosAgendamento dados) {
        var primeiroHorario = dados.data().withHour(7);
        var ultimoHorario = dados.data().withHour(18);
        var pacientePossuiOutraConsulta = repository.existsByPacienteIdAndDataBetween(dados.idPaciente(), primeiroHorario, ultimoHorario);
        if (pacientePossuiOutraConsulta) {
            throw new ValidacaoException("Paciente ja possui uma consulta agendada");
        }
    }
}
