package medi.voli.apiREST.domain.consulta.validacoes.agendamento;

import medi.voli.apiREST.domain.ValidacaoException;
import medi.voli.apiREST.domain.consulta.ConsultaRepository;
import medi.voli.apiREST.domain.consulta.DadosAgendamento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;



@Component
public class ValidadorMedicoComOutraConsulta implements ValidadorAgendamentoConsulta{

    @Autowired
    private ConsultaRepository repository;

    public void validar(DadosAgendamento dados){
        var medicoPossuiOutraConsulta = repository.existsByMedicoIdAndDataAndMotivoCancelamentoNull(dados.idMedico(), dados.data());
        if (medicoPossuiOutraConsulta){
            throw new ValidacaoException("Medico possui outra consulta neste horario");
        }

    }
}
