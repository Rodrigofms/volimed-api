package medi.voli.apiREST.controller;

import medi.voli.apiREST.domain.consulta.AgendaDeConsultas;
import medi.voli.apiREST.domain.consulta.DadosAgendamento;
import medi.voli.apiREST.domain.consulta.DadosDetalhadosConsulta;
import medi.voli.apiREST.domain.medico.Especialidade;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class ConsultaControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<DadosAgendamento> dadosAgendamentoTest;

    @Autowired
    private JacksonTester<DadosDetalhadosConsulta> dadosDetalhadosConsultaTest;

    @MockBean
    private AgendaDeConsultas agendaDeConsultas;

    @Test
    @DisplayName("Deveria devolver codigo HTTP 400 quando info's estao invalidas")
    @WithMockUser
    void agendar_cenario1() throws Exception {
        mvc.perform(post("/consultas"))
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()));
    }

    @Test
    @DisplayName("Deveria devolver codigo HTTP 200 quando info's estao validas")
    @WithMockUser
    void agendar_cenario2() throws Exception {
        var data = LocalDateTime.now().plusHours(1);
        var especialidade = Especialidade.DERMATOLOGIA;

        var dadosDetalhamento = new DadosDetalhadosConsulta(null, 2L, 5L, data);

        when(agendaDeConsultas.agendar(any())).thenReturn(dadosDetalhamento);

        MvcResult response = mvc.perform(post("/consultas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dadosAgendamentoTest.write(
                                new DadosAgendamento(2L, 2L, data, especialidade)
                        ).getJson()))
                .andExpect(status().is(HttpStatus.OK.value()))
                .andReturn();

        var jsonEsperado = dadosDetalhadosConsultaTest.write(
                dadosDetalhamento).getJson();

        assertThat(response.getResponse().getContentAsString()).isEqualTo(jsonEsperado);
    }

}