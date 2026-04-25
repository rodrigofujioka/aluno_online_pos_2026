package br.com.alunoonline.api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "aluno")
@Entity
public class Aluno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank (message = "Nome Completo não pode ser nulo")
    @Size (min = 3, max = 100, message = "Nome Completo deve ter entre 3 e 100 caracteres")
    private String nomeCompleto;

    @CPF
    private String cpf;

    @Email
    private String email;
}
