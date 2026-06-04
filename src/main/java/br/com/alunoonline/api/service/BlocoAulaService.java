package br.com.alunoonline.api.service;

import br.com.alunoonline.api.model.BlocoAula;
import br.com.alunoonline.api.repository.BlocoAulaRepository;
import com.github.javafaker.Faker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Slf4j
@RequiredArgsConstructor
@Service
public class BlocoAulaService {

    private static final int TAMANHO_LOTE = 1000;
    private final BlocoAulaRepository blocoAulaRepository;

    public Page<BlocoAula> listarPaginado(Pageable pageable) {
        log.info("Listando blocos de aula com paginacao. Pagina: {} Tamanho: {} Ordenacao: {}",
                pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort());

        return blocoAulaRepository.findAll(pageable);
    }

    public List<BlocoAula> listarTodos() {
        log.info("Listando todos os blocos de aula sem paginacao");
        return blocoAulaRepository.findAll(Sort.by(Sort.Direction.ASC, "nomeBloco"));
    }

    @Transactional
    public int carregarBlocosAula(int quantidade) {
        long totalAntes = blocoAulaRepository.count();
        Faker faker = new Faker(new Locale("pt", "BR"));

        List<BlocoAula> lote = new ArrayList<>(TAMANHO_LOTE);

        for (int i = 0; i < quantidade; i++) {
            long indiceGlobal = totalAntes + i;
            BlocoAula bloco = criarBlocoAula(faker, indiceGlobal);
            lote.add(bloco);

            if (lote.size() == TAMANHO_LOTE) {
                blocoAulaRepository.saveAll(lote);
                lote.clear();
            }
        }

        if (!lote.isEmpty()) {
            blocoAulaRepository.saveAll(lote);
        }

        log.info("Carga finalizada para blocos de aula. Total inserido: {}", quantidade);
        return quantidade;
    }

    private BlocoAula criarBlocoAula(Faker faker, long indiceGlobal) {
        BigDecimal deslocamento = BigDecimal.valueOf(indiceGlobal)
                .multiply(new BigDecimal("0.00001000"));

        return new BlocoAula(
                null,
                String.format("Bloco %s - %s", indiceParaCodigo(indiceGlobal), faker.educator().course()),
                new BigDecimal("-23.55052000").add(deslocamento),
                new BigDecimal("-46.63330800").add(deslocamento),
                faker.name().fullName(),
                faker.numerify("(11) 9####-####")
        );
    }

    // Converte 0 -> A, 1 -> B ... 25 -> Z, 26 -> AA para manter nome incremental.
    private String indiceParaCodigo(long indice) {
        long valor = indice;
        StringBuilder codigo = new StringBuilder();

        do {
            int resto = (int) (valor % 26);
            codigo.insert(0, (char) ('A' + resto));
            valor = (valor / 26) - 1;
        } while (valor >= 0);

        return codigo.toString();
    }
}

