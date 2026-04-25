package br.com.alunoonline.api.service;

import br.com.alunoonline.api.model.Aluno;
import br.com.alunoonline.api.repository.AlunoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AlunoService {

    private final AlunoRepository alunoRepository;

    public Optional<Aluno> buscarPorCpf(String cpf) {
        return alunoRepository.findByCpf(cpf);
    }

    public Aluno criarAluno(Aluno aluno) {
        return alunoRepository.save(aluno);
    }

    public List<Aluno> buscarTodosAlunos() {
        return alunoRepository.findAll();
    }

    public Optional<Aluno> buscarAlunoPorId(Long id) {
        return alunoRepository.findById(id);
    }

    public void deletarAlunoPorId(Long id) {
        alunoRepository.deleteById(id);
    }

    public void atualizarAlunoPorId(Long id, Aluno alunoDoFront) {

        // Pegar o dado atual do BD para depois atualizar
        Optional<Aluno> alunoDoBanco = buscarAlunoPorId(id);

        // Validar se o aluno existe no BD
        if (alunoDoBanco.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Aluno não encontrado no Banco no BD"
            );
        }

        Aluno alunoParaEditar = alunoDoBanco.get();

        // Alterar cada um dos campos
        alunoParaEditar.setNomeCompleto(alunoDoFront.getNomeCompleto());
        alunoParaEditar.setEmail(alunoDoFront.getEmail());
        alunoParaEditar.setCpf(alunoDoFront.getCpf());

        alunoRepository.save(alunoParaEditar);

    }

}
