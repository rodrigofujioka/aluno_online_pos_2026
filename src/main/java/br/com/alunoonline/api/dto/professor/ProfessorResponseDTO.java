package br.com.alunoonline.api.dto.professor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfessorResponseDTO {

    // Identificador unico gerado no banco.
    private Long id;

    // Dados principais usados nas listagens e telas.
    private String nomeCompleto;
    private String email;

    // Campo de contato textual no retorno principal.
    private String endereco;
}

