package medi.voli.apiREST.domain.medico;

import medi.voli.apiREST.domain.endereco.Endereco;

public record DadosDetalhadosMedico(Long id, String nome, String email, String crm, Especialidade especialidade, Endereco endereco) {
    public DadosDetalhadosMedico(Medico medico) {
        this(medico.getId(), medico.getNome(), medico.getEmail(), medico.getCrm(), medico.getEspecialidade(), medico.getEndereco());
    }
}
