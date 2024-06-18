package medi.voli.apiREST.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import medi.voli.apiREST.domain.consulta.AgendaDeConsultas;
import medi.voli.apiREST.domain.consulta.DadosAgendamento;
import medi.voli.apiREST.domain.consulta.DadosDetalhadosConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/consultas")
@Tag(name = "Consultas")
@SecurityRequirement(name = "bearer-key")
public class ConsultaController {

    @Autowired
    private AgendaDeConsultas agenda;

    @PostMapping
    @Transactional
    @Operation(summary = "Agendar", description = "Agenda nova consulta")
    public ResponseEntity agendar(@RequestBody @Valid DadosAgendamento dados){
        var dto =agenda.agendar(dados);
        return ResponseEntity.ok(dto);
    }
}
