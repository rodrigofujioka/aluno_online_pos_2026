package br.com.alunoonline.api.controller;

import br.com.alunoonline.api.dto.professor.ProfessorRequestDTO;
import br.com.alunoonline.api.dto.professor.ProfessorResponseDTO;
import br.com.alunoonline.api.dto.professor.ProfessorDetailsDTO;
import br.com.alunoonline.api.service.ProfessorService;
import br.com.alunoonline.api.util.CpfUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/professores")
public class ProfessorController {

    // Service centraliza as regras de negocio do fluxo de professor.
    private final ProfessorService professorService;

    // Cria professor a partir de um DTO de entrada validado.
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProfessorResponseDTO criarProfessor(@Valid @RequestBody ProfessorRequestDTO professorRequestDTO) {
        // Loga dados de identificacao com CPF mascarado para nao expor dado sensivel.
        log.info("Recebida requisição para criar professor. Nome: {} Email: {} CPF: {}",
                professorRequestDTO.getNomeCompleto(), professorRequestDTO.getEmail(),
                CpfUtils.formatCpf(professorRequestDTO.getCpf()));

        // Delega persistencia e mapeamento para a camada de servico.
        ProfessorResponseDTO professorCriado = professorService.criarProfessor(professorRequestDTO);

        log.info("Professor criado com sucesso. ID: {} Email: {}",
                professorCriado.getId(), professorCriado.getEmail());

        return professorCriado;
    }

    // Lista professores de forma paginada para evitar respostas muito grandes.
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(
            summary = "Listar professores com paginacao",
            description = "Use sort no formato campo,direcao. Ex.: sort=nomeCompleto,asc. " +
                    "Nao use formato JSON como [\"nomeCompleto\"]."
    )
    @Parameters({
            @Parameter(name = "page", in = ParameterIn.QUERY, description = "Numero da pagina (inicia em 0)", example = "0"),
            @Parameter(name = "size", in = ParameterIn.QUERY, description = "Quantidade de itens por pagina", example = "10"),
            @Parameter(
                    name = "sort",
                    in = ParameterIn.QUERY,
                    description = "Ordenacao no formato campo,direcao. Pode repetir o parametro para multiplos campos. " +
                            "Exemplos: sort=nomeCompleto,asc ou sort=nomeCompleto,asc&sort=email,desc",
                    array = @ArraySchema(schema = @Schema(type = "string", example = "nomeCompleto,asc"))
            )
    })
    public Page<ProfessorResponseDTO> listarProfessores(
            @PageableDefault(page = 0, size = 10, sort = "nomeCompleto") Pageable pageable) {

        log.info("Recebida requisição para listar professores paginados. Página: {} Tamanho: {} Ordenação: {}",
                pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort());

        Page<ProfessorResponseDTO> professores = professorService.listarProfessores(pageable);

        log.info("Listagem paginada de professores concluída. Página atual: {} Total de elementos: {} Total de páginas: {} Elementos retornados: {}",
                professores.getNumber(), professores.getTotalElements(), professores.getTotalPages(),
                professores.getNumberOfElements());

        return professores;
    }

    // Busca o detalhe de um professor pelo id informado na URL.
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProfessorDetailsDTO buscarProfessorPorId(@PathVariable Long id) {
        log.info("Recebida requisição para buscar professor por ID: {}", id);

        // Retorna DTO de detalhe com CPF mascarado para manter seguranca do retorno.
        ProfessorDetailsDTO professor = professorService.buscarProfessorPorId(id);

        log.info("Consulta de professor por ID concluída. ID: {} Email: {} CPF: {}",
                professor.getId(), professor.getEmail(), professor.getCpfMascarado());

        return professor;
    }
}
