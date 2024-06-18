package medi.voli.apiREST.domain.consulta;

import medi.voli.apiREST.domain.ValidacaoException;
import medi.voli.apiREST.domain.consulta.validacoes.agendamento.ValidadorAgendamentoConsulta;
import medi.voli.apiREST.domain.consulta.validacoes.cancelamento.ValidadorCancelamentoConsulta;
import medi.voli.apiREST.domain.medico.Medico;
import medi.voli.apiREST.domain.medico.MedicoRepository;
import medi.voli.apiREST.domain.paciente.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgendaDeConsultas {

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private List<ValidadorAgendamentoConsulta> validadores;

    @Autowired
    private List<ValidadorCancelamentoConsulta> validadoresCancelamento;

    public DadosDetalhadosConsulta agendar(DadosAgendamento dados){

        if(!pacienteRepository.existsById(dados.idPaciente())){
            throw new ValidacaoException("Id do paciente informado nao existe");
        }
        if(dados.idMedico() != null && !medicoRepository.existsById(dados.idMedico())){
            throw new ValidacaoException("Id do medico informado nao existe");
        }
        validadores.forEach(v ->v.validar(dados));

        var paciente = pacienteRepository.findById(dados.idPaciente()).get();
        var medico = escolhermedico(dados);

        if(medico==null){
            throw new ValidacaoException("Nao existe medico disponivel nesta data");
        }

        var consulta = new Consulta(null, medico, paciente, dados.data(), null);
        consultaRepository.save(consulta);

        return new DadosDetalhadosConsulta(consulta);
    }

    public void cancelar(DadosCancelamento dados) {
        if (!consultaRepository.existsById(dados.idConsulta())) {
            throw new ValidacaoException("Id da consulta informado não existe!");
        }

        validadoresCancelamento.forEach(v -> v.validar(dados));

        var consulta = consultaRepository.getReferenceById(dados.idConsulta());
        consulta.cancelar(dados.motivo());
    }

    private Medico escolhermedico(DadosAgendamento dados) {
        if(dados.idMedico() != null){
            return  medicoRepository.getReferenceById(dados.idMedico());
        }else if(dados.especialidade() == null){
            throw new ValidacaoException("Especialidade e obrigatoria quando o medico nao for escolhido");
        }else {
            return medicoRepository.escolherMedicoAleatoriamente(dados.especialidade(),dados.data());
        }

    }
}
