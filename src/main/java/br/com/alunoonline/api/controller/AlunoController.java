package br.com.alunoonline.api.controller;

import br.com.alunoonline.api.model.Aluno;
import br.com.alunoonline.api.service.AlunoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void criarAluno(@Valid @RequestBody Aluno aluno) {
        log.info("Criando Aluno: {}", aluno);
        Aluno alunoCriado = alunoService.criarAluno(aluno);
        log.info("Aluno Criado ID: {} Email : {}", alunoCriado.getId(), aluno.getEmail());

    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Aluno> buscarTodosAlunos() {
        return alunoService.buscarTodosAlunos();
    }

    @GetMapping("/cpf/{cpf}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<Aluno> buscarAlunoPorCpf(@PathVariable String cpf) {
        return alunoService.buscarPorCpf(cpf);
    }


    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<Aluno> buscarAlunoPorId(@PathVariable Long id) {
        return alunoService.buscarAlunoPorId(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletarAlunoPorId(@PathVariable Long id) {
        alunoService.deletarAlunoPorId(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void atualizarAlunoPorId(@Valid @RequestBody Aluno aluno, @PathVariable Long id) {
        alunoService.atualizarAlunoPorId(id, aluno);
    }

}
