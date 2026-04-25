package br.com.alunoonline.api.repository;

import br.com.alunoonline.api.model.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AlunoRepository
        extends JpaRepository<Aluno, Long> {

    Optional<Aluno> findByCpf(String cpf);

    @Query("SELECT a FROM Aluno a " +
            "WHERE a.cpf = :cpf")
    Optional<Aluno> buscaPorCpf(@Param("cpf") String cpf);



}
