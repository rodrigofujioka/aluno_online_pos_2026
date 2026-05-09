package br.com.alunoonline.api.validation.nome;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.constraints.NotBlank;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class NomeSemPalavraoValidatorTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    void deveAceitarNomeSemPalavrao() {
        NomeDTO dto = new NomeDTO("Marcos Silva");

        Set<ConstraintViolation<NomeDTO>> violations = validator.validate(dto);

        assertTrue(violations.isEmpty());
    }

    @Test
    void deveRejeitarNomeComPalavrao() {
        NomeDTO dto = new NomeDTO("Professor Palavrao Souza");

        Set<ConstraintViolation<NomeDTO>> violations = validator.validate(dto);

        assertEquals(1, violations.size());
    }

    @Test
    void deveRejeitarNomeComPalavraoMesmoComVariacaoDeCaixa() {
        NomeDTO dto = new NomeDTO("Professor pALavRaO Souza");

        Set<ConstraintViolation<NomeDTO>> violations = validator.validate(dto);

        assertEquals(1, violations.size());
    }

    @Test
    void deveAceitarNomeNuloOuVazioParaDelegarNotBlank() {
        NomeDTO dtoNulo = new NomeDTO(null);
        NomeDTO dtoVazio = new NomeDTO(" ");

        Set<ConstraintViolation<NomeDTO>> nuloViolations = validator.validate(dtoNulo);
        Set<ConstraintViolation<NomeDTO>> vazioViolations = validator.validate(dtoVazio);

        assertEquals(1, nuloViolations.size());
        assertEquals(1, vazioViolations.size());
    }

    private static class NomeDTO {

        @NotBlank
        @NomeSemPalavrao
        private final String nome;

        private NomeDTO(String nome) {
            this.nome = nome;
        }
    }
}

