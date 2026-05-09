package br.com.alunoonline.api.validation.nome;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.text.Normalizer;
import java.util.Set;

// Validador que impede nomes com termos ofensivos definidos em lista local.
public class NomeSemPalavraoValidator implements ConstraintValidator<NomeSemPalavrao, String> {

    private static final Set<String> PALAVROES = Set.of(
            "palavrao",
            "xingamento",
            "ofensa"
    );

    @Override
    public boolean isValid(String nome, ConstraintValidatorContext context) {
        // Campo vazio segue responsabilidade do @NotBlank.
        if (nome == null || nome.isBlank()) {
            return true;
        }

        String nomeNormalizado = normalizar(nome);

        for (String palavrao : PALAVROES) {
            if (nomeNormalizado.contains(palavrao)) {
                return false;
            }
        }

        return true;
    }

    private String normalizar(String texto) {
        String semAcento = Normalizer.normalize(texto, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "");

        return semAcento.toLowerCase().replaceAll("[^a-z0-9 ]", " ");
    }
}

