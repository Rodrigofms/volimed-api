package medi.voli.apiREST.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import medi.voli.apiREST.domain.medico.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;


@RestController
@RequestMapping("/medicos")
@Tag(name = "Medicos", description = "Cadastro, consulta, atualizacao e ativacao de medicos")
@SecurityRequirement(name = "bearer-key")
class MedicoController {

    @Autowired
    private MedicoRepository REPOSITORY;

    @PostMapping
    @Transactional
    @Operation(summary = "Cadastrar", description = "Cadastra medico no sistema")
    public ResponseEntity cadastrar(@RequestBody @Valid DadosMedico dados, UriComponentsBuilder uriuilder){
        var medico =new Medico(dados);
        REPOSITORY.save(medico);
        var uri = uriuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();
        return ResponseEntity.created(uri).body(new DadosDetalhadosMedico(medico));
    }

    @GetMapping
    @Operation(summary = "Listar", description = "Lista todos medicos do sistema por paginas")
    public ResponseEntity<Page<DadosListagemMedico>> listar(@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao){


        var page = REPOSITORY.findAllByAtivoTrue(paginacao)
                .map(DadosListagemMedico::new);
        return ResponseEntity.ok(page);
    }

    @PutMapping
    @Transactional
    @Operation(summary = "Atualizar", description = "Atualiza medico no sistema")
    public ResponseEntity atualizar(@RequestBody @Valid DadosAtualizarMedico dados){
        var medico = REPOSITORY.getReferenceById(dados.id());
        medico.atualizarInformacoes(dados);
        return ResponseEntity.ok(new DadosDetalhadosMedico(medico));
    }

    @DeleteMapping("/{id}")
    @Transactional
    @Operation(summary = "Ativacao", description = "Torna medico no sistema inativo")
    public ResponseEntity excluir(@PathVariable Long id){
        var medico = REPOSITORY.getReferenceById(id);
        medico.excluir();

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Detalhar", description = "Procura medico por ID no sistema")
    public ResponseEntity detalhar(@PathVariable Long id){
        var medico = REPOSITORY.getReferenceById(id);
        return ResponseEntity.ok(new DadosDetalhadosMedico(medico));
    }
}
