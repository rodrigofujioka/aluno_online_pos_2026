package br.com.alunoonline.api.config;

import br.com.alunoonline.api.model.BlocoAula;
import br.com.alunoonline.api.repository.BlocoAulaRepository;
import com.github.javafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Configuration
public class BlocoAulaDataInitializer {

    @Bean
    @Profile({"dev", "hml"})
    CommandLineRunner initBlocosAula(BlocoAulaRepository blocoAulaRepository) {
        return args -> {
            if (blocoAulaRepository.count() > 0) {
                return;
            }

            Faker faker = new Faker(new Locale("pt", "BR"));
            List<BlocoAula> blocos = new ArrayList<>();

            // Cria blocos incrementais no formato: Bloco A - [conteudo faker].
            for (int i = 0; i < 8; i++) {
                char letra = (char) ('A' + i);
                String nomeBloco = String.format("Bloco %s - %s", letra, faker.educator().course());

                BigDecimal latitude = new BigDecimal("-23.55052000").add(BigDecimal.valueOf(i).multiply(new BigDecimal("0.00035000")));
                BigDecimal longitude = new BigDecimal("-46.63330800").add(BigDecimal.valueOf(i).multiply(new BigDecimal("0.00029000")));

                BlocoAula bloco = new BlocoAula(
                        null,
                        nomeBloco,
                        latitude,
                        longitude,
                        faker.name().fullName(),
                        faker.numerify("(11) 9####-####")
                );

                blocos.add(bloco);
            }

            blocoAulaRepository.saveAll(blocos);
        };
    }
}

