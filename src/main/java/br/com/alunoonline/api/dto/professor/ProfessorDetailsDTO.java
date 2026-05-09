package br.com.alunoonline.api.dto.professor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfessorDetailsDTO {

    // DTO focado no endpoint de detalhe por id.
    private Long id;
    private String nomeCompleto;
    private String email;
    private String endereco;

    // CPF vai mascarado para preservar privacidade.
    private String cpfMascarado;
}

