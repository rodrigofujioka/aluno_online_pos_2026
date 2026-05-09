package br.com.alunoonline.api.controller;

import br.com.alunoonline.api.model.Aluno;
import br.com.alunoonline.api.service.AlunoService;
import br.com.alunoonline.api.util.CpfUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/alunos")
public class AlunoController {

    private final AlunoService alunoService;

    @CacheEvict(value = "LISTA_ALUNOS",allEntries = true)
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void criarAluno(@Valid @RequestBody Aluno aluno) {
        log.info("Recebida requisição para criar aluno. Nome: {} Email: {} CPF: {}",
                aluno.getNomeCompleto(), aluno.getEmail(), CpfUtils.formatCpf(aluno.getCpf()));
        Aluno alunoCriado = alunoService.criarAluno(aluno);
        log.info("Aluno criado com sucesso. ID: {} Email: {} CPF: {}",
                alunoCriado.getId(), alunoCriado.getEmail(), CpfUtils.formatCpf(alunoCriado.getCpf()));

    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Cacheable("LISTA_ALUNOS")
    public List<Aluno> buscarTodosAlunos() {
        log.info("Recebida requisição para listar todos os alunos");
        List<Aluno> alunos = alunoService.buscarTodosAlunos();
        log.info("Listagem de alunos concluída. Total retornado: {}", alunos.size());
        return alunos;
    }

    @GetMapping("/cpf/{cpf}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<Aluno> buscarAlunoPorCpf(@PathVariable String cpf) {
        log.info("Recebida requisição para buscar aluno por CPF: {}", CpfUtils.formatCpf(cpf));
        Optional<Aluno> aluno = alunoService.buscarPorCpf(cpf);
        log.info("Consulta por CPF concluída. Aluno encontrado: {}", aluno.isPresent());
        return aluno;
    }


    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<Aluno> buscarAlunoPorId(@PathVariable Long id) {
        log.info("Recebida requisição para buscar aluno por ID: {}", id);
        Optional<Aluno> aluno = alunoService.buscarAlunoPorId(id);
        log.info("Consulta por ID concluída. ID: {} Encontrado: {}", id, aluno.isPresent());
        return aluno;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletarAlunoPorId(@PathVariable Long id) {
        log.info("Recebida requisição para deletar aluno. ID: {}", id);
        alunoService.deletarAlunoPorId(id);
        log.info("Requisição de deleção concluída com sucesso. ID: {}", id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void atualizarAlunoPorId(@Valid @RequestBody Aluno aluno, @PathVariable Long id) {
        log.info("Recebida requisição para atualizar aluno. ID: {} Novo email: {} Novo CPF: {}",
                id, aluno.getEmail(), CpfUtils.formatCpf(aluno.getCpf()));
        alunoService.atualizarAlunoPorId(id, aluno);
        log.info("Requisição de atualização concluída com sucesso. ID: {}", id);
    }


}
