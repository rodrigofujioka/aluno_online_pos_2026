package br.com.alunoonline.api.controller;
import br.com.alunoonline.api.model.BlocoAula;
import br.com.alunoonline.api.service.BlocoAulaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Map;
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/blocos-aula")
public class BlocoAulaController {
    private final BlocoAulaService blocoAulaService;
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<BlocoAula> listarBlocosPaginado(
            @PageableDefault(page = 0, size = 10, sort = "nomeBloco") Pageable pageable) {
        return blocoAulaService.listarPaginado(pageable);
    }
    @GetMapping("/todos")
    @ResponseStatus(HttpStatus.OK)
    public List<BlocoAula> listarBlocosSemPaginacao() {
        return blocoAulaService.listarTodos();
    }
    @PostMapping("/carga/10000")
    @ResponseStatus(HttpStatus.CREATED)
    public Map<String, Object> carregarDezMilRegistros() {
        log.info("Recebida requisicao para carga de 10 mil registros de blocos de aula");
        int inseridos = blocoAulaService.carregarBlocosAula(10_000);
        return Map.of(
                "mensagem", "Carga executada com sucesso",
                "registrosInseridos", inseridos
        );
    }
}
