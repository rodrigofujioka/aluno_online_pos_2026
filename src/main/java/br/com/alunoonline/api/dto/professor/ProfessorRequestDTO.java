package br.com.alunoonline.api.dto.professor;

import br.com.alunoonline.api.validation.nome.NomeSemPalavrao;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfessorRequestDTO {

    // Nome exibido para alunos e telas administrativas.
    @NotBlank(message = "Nome completo é obrigatório")
    @Size(min = 3, max = 100, message = "Nome completo deve ter entre 3 e 100 caracteres")
    @NomeSemPalavrao
    private String nomeCompleto;

    // E-mail de contato institucional do professor.
    @Email(message = "E-mail inválido")
    @NotBlank(message = "E-mail é obrigatório")
    private String email;

    // CPF e validado para garantir consistencia do cadastro.
    @CPF(message = "CPF inválido")
    @NotBlank(message = "CPF é obrigatório")
    private String cpf;

    // Endereco livre por enquanto; sera enriquecido depois com ViaCEP.
    private String endereco;
}

