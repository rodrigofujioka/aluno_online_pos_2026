package br.com.alunoonline.api.service;

import br.com.alunoonline.api.model.Aluno;
import br.com.alunoonline.api.repository.AlunoRepository;
import br.com.alunoonline.api.util.CpfUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class AlunoService {

    private final AlunoRepository alunoRepository;

    public Optional<Aluno> buscarPorCpf(String cpf) {
        log.info("Iniciando busca de aluno por CPF: {}", CpfUtils.formatCpf(cpf));
        Optional<Aluno> aluno = alunoRepository.findByCpf(cpf);

        if (aluno.isPresent()) {
            log.info("Aluno localizado por CPF. ID: {} Email: {} CPF: {}",
                    aluno.get().getId(), aluno.get().getEmail(), CpfUtils.formatCpf(aluno.get().getCpf()));
        } else {
            log.warn("Nenhum aluno encontrado para o CPF: {}", CpfUtils.formatCpf(cpf));
        }

        return aluno;
    }

    public Aluno criarAluno(Aluno aluno) {
        log.info("Persistindo novo aluno. Nome: {} Email: {} CPF: {}",
                aluno.getNomeCompleto(), aluno.getEmail(), CpfUtils.formatCpf(aluno.getCpf()));
        Aluno alunoSalvo = alunoRepository.save(aluno);
        log.info("Aluno persistido com sucesso. ID: {} Email: {}",
                alunoSalvo.getId(), alunoSalvo.getEmail());
        return alunoSalvo;
    }

    public List<Aluno> buscarTodosAlunos() {
        log.info("Buscando todos os alunos cadastrados");
        List<Aluno> alunos = alunoRepository.findAll();
        log.info("Consulta de alunos finalizada. Total encontrado: {}", alunos.size());
        return alunos;
    }

    public Optional<Aluno> buscarAlunoPorId(Long id) {
        log.info("Buscando aluno por ID: {}", id);
        Optional<Aluno> aluno = alunoRepository.findById(id);

        if (aluno.isPresent()) {
            log.info("Aluno localizado por ID. ID: {} Email: {} CPF: {}",
                    aluno.get().getId(), aluno.get().getEmail(), CpfUtils.formatCpf(aluno.get().getCpf()));
        } else {
            log.warn("Nenhum aluno encontrado para o ID: {}", id);
        }

        return aluno;
    }

    public void deletarAlunoPorId(Long id) {
        log.info("Solicitada exclusão de aluno. ID: {}", id);
        Optional<Aluno> aluno = buscarAlunoPorId(id);

        if (aluno.isEmpty()) {
            log.warn("Não foi possível excluir o aluno. ID não encontrado: {}", id);
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Aluno não encontrado no banco de dados"
            );
        }

        alunoRepository.deleteById(id);
        log.info("Aluno removido com sucesso. ID: {} Email: {}",
                id, aluno.get().getEmail());
    }

    public void atualizarAlunoPorId(Long id, Aluno alunoDoFront) {
        log.info("Solicitada atualização de aluno. ID: {} Novo email: {} Novo CPF: {}",
                id, alunoDoFront.getEmail(), CpfUtils.formatCpf(alunoDoFront.getCpf()));

        // Pegar o dado atual do BD para depois atualizar
        Optional<Aluno> alunoDoBanco = buscarAlunoPorId(id);

        // Validar se o aluno existe no BD
        if (alunoDoBanco.isEmpty()) {
            log.warn("Não foi possível atualizar o aluno. ID não encontrado: {}", id);
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Aluno não encontrado no banco de dados"
            );
        }

        Aluno alunoParaEditar = alunoDoBanco.get();

        // Alterar cada um dos campos
        alunoParaEditar.setNomeCompleto(alunoDoFront.getNomeCompleto());
        alunoParaEditar.setEmail(alunoDoFront.getEmail());
        alunoParaEditar.setCpf(alunoDoFront.getCpf());

        Aluno alunoAtualizado = alunoRepository.save(alunoParaEditar);
        log.info("Aluno atualizado com sucesso. ID: {} Email: {} CPF: {}",
                alunoAtualizado.getId(), alunoAtualizado.getEmail(), CpfUtils.formatCpf(alunoAtualizado.getCpf()));

    }

}
