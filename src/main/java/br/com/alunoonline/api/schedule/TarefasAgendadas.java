package br.com.alunoonline.api.schedule;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TarefasAgendadas {

    @Scheduled(fixedRate = 10000)
    public void tarefa1() {
        log.info("Tarefa 1 executada");
    }

    @Scheduled(cron = "0 59 15 * * ?")
    public void tarefa2() {
        log.info("Tarefa 2 executada");
    }
}
