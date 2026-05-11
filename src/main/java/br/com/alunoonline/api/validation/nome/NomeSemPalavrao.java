package br.com.alunoonline.api.validation.nome;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// Annotation customizada para bloquear nomes com palavras ofensivas.
@Documented
@Constraint(validatedBy = NomeSemPalavraoValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface NomeSemPalavrao {

    String message() default "Não é permitido palavrâo no nome do prof";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

