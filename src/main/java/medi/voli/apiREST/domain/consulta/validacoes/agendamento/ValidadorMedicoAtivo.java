package medi.voli.apiREST.domain.consulta.validacoes.agendamento;

import medi.voli.apiREST.domain.ValidacaoException;
import medi.voli.apiREST.domain.consulta.DadosAgendamento;
import medi.voli.apiREST.domain.medico.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorMedicoAtivo implements ValidadorAgendamentoConsulta{

    @Autowired
    private MedicoRepository repository;

    public void validar(DadosAgendamento dados){
        if(dados.idMedico() == null){
            return;
        }

        var medicoEstaAtivo = repository.findAtivoById(dados.idMedico());
        if(!medicoEstaAtivo) {
            throw new ValidacaoException("Consulta nao pode ser agendada com medico indisponivel");
        }

    }
}
