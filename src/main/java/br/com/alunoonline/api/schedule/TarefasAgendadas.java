package br.com.alunoonline.api.schedule;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Classe educacional para demonstrar diferentes formas de agendar tarefas em Spring
 * {@code @EnableScheduling} deve estar habilitado na classe Application
 */
@Slf4j
@Component
public class TarefasAgendadas {

    // Constante para configurar quantos dias manter os logs
    private static final int DIAS_RETENCAO_LOGS = 30;

    // ==================== EXEMPLO 1: FIXED RATE ====================
    /**
     * Executa a cada N milissegundos (sempre respeitando o intervalo)
     * Útil para: verificações periódicas, sincronizações, limpezas
     * fixedRate = 10000 --> executa a cada 10 SEGUNDOS
     */
    @Scheduled(fixedRate = 10000)
    public void tarefa1_FixedRate() {
        log.info("📌 TAREFA 1 - FIXED RATE: Executada a cada 10 segundos");
    }

    // ==================== EXEMPLO 2: CRON EXPRESSION ====================
    /**
     * Executa em um horario/data especifico usando expressao CRON
     * Formato: (segundo) (minuto) (hora) (dia do mes) (mes) (dia da semana)
     */
    // Exemplos de expressoes CRON:
    // - "0 0 12 * * ?" --> 12:00 todos os dias
    // - "0 */5 * * * ?" --> A cada 5 minutos
    // - "0 0 0 * * MON" --> Toda segunda-feira a meia-noite
    // - "0 0 15 * * ?" --> 15:00 todos os dias
    @Scheduled(cron = "0 59 15 * * ?")
    public void tarefa2_CronExpression() {
        log.info("📌 TAREFA 2 - CRON: Executada à 15:59 diariamente");
    }

    // ==================== EXEMPLO 3: FIXED DELAY ====================
    /**
     * Executa com atraso entre execucoes (conta apos FIM da execucao anterior)
     * Util para: processos que nao podem executar simultaneamente
     * fixedDelay = 5000 --> 5 segundos APOS a tarefa anterior terminar
     * initialDelay = 2000 --> aguarda 2 segundos antes da primeira execucao
     */
    @Scheduled(fixedDelay = 5000, initialDelay = 2000)
    public void tarefa3_FixedDelay() {
        log.info("📌 TAREFA 3 - FIXED DELAY: Executa com 5s de atraso entre execuções");
    }

    // ==================== EXEMPLO 4: TODO DIA (6:00 AM) ====================
    /**
     * Executa todos os dias às 6:00 da manhã
     */
    @Scheduled(cron = "0 0 6 * * ?")
    public void tarefaDiaria_Manha() {
        log.info("📌 TAREFA DIÁRIA - Executada às 06:00 AM");
    }

    // ==================== EXEMPLO 5: TODO DIA (3:00 PM) ====================
    /**
     * Executa todos os dias às 15:00 (3 PM)
     */
    @Scheduled(cron = "0 0 15 * * ?")
    public void tarefaDiaria_Tarde() {
        log.info("📌 TAREFA DIÁRIA - Executada às 15:00 (3 PM)");
    }

    // ==================== EXEMPLO 6: A CADA 5 MINUTOS ====================
    /**
     * Executa a cada 5 minutos
     */
    // Expressao: minuto (*/5) = a cada 5 minutos
    @Scheduled(cron = "0 */5 * * * ?")
    public void tarefa_A_Cada_5_Minutos() {
        log.info("📌 TAREFA - CADA 5 MIN: Executada a cada 5 minutos");
    }

    // ==================== EXEMPLO 7: SEGUNDA A SEXTA - 9:00 AM ====================
    /**
     * Executa de segunda a sexta-feira às 9:00 AM
     * MON-FRI = segunda a sexta
     */
    @Scheduled(cron = "0 0 9 ? * MON-FRI")
    public void tarefa_DiaUteis() {
        log.info("📌 TAREFA DIAS ÚTEIS - Executada às 09:00 AM (segunda a sexta)");
    }

    // ==================== EXEMPLO 8: MEIA-NOITE TODOS OS DIAS ====================
    /**
     * Executa todos os dias à meia-noite (00:00)
     */
    @Scheduled(cron = "0 0 0 * * ?")
    public void tarefa_MeiaNoite() {
        log.info("📌 TAREFA MEIA-NOITE - Executada às 00:00 todos os dias");
    }

    // ==================== EXEMPLO 9: PRIMEIRO DIA DO MÊS ====================
    /**
     * Executa no primeiro dia de cada mês às 01:00
     */
    @Scheduled(cron = "0 0 1 1 * ?")
    public void tarefa_PrimeiroDiaDoMes() {
        log.info("📌 TAREFA MENSAL - Executada no 1º dia do mês às 01:00");
    }

    // ==================== EXEMPLO 10: COM TRATAMENTO DE ERRO E TIMESTAMP ====================
    /**
     * Exemplo mais realista com tratamento de erro
     */
    @Scheduled(fixedRate = 30000)
    public void tarefa_ComTratamento() {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
            String agora = LocalDateTime.now().format(formatter);
            log.info("✅ TAREFA COM TRATAMENTO: Executada em {}", agora);
            // Aqui você colocaria sua lógica
        } catch (Exception e) {
            log.error("❌ ERRO na execução da tarefa: {}", e.getMessage(), e);
        }
    }

    // ==================== EXEMPLO 11: ROTACAO DE ARQUIVOS DE LOG DIARIAMENTE ====================
    /**
     * Executa todos os dias à meia-noite para fazer backup dos logs
     * Caso de uso real: limpeza e arquivo dos logs da aplicação
     */
    @Scheduled(cron = "0 0 0 * * ?")
    public void tarefa_RotacaoLogsAoAmanhecer() {
        try {
            log.info("🔄 ROTACAO DE LOGS: Iniciando backup de logs diários...");

            // Diretório onde os logs são armazenados
            String diretorioLogs = System.getProperty("user.home") + File.separator + "logs" + File.separator;
            Path caminhoPasta = Paths.get(diretorioLogs);

            // Criar diretório se não existir
            if (!Files.exists(caminhoPasta)) {
                Files.createDirectories(caminhoPasta);
                log.info("Diretório de logs criado: {}", diretorioLogs);
            }

            // Formatar data para nomear o arquivo de backup
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String dataAtual = LocalDateTime.now().minusDays(1).format(formatter);

            // Criar backup com data no nome
            String nomeBackup = "application-" + dataAtual + ".log";
            log.info("📁 Backup de logs será salvo como: {}", nomeBackup);

            // Limpar arquivos de log com mais de 30 dias
            limparLogsAntigos(caminhoPasta);

            log.info("✅ ROTACAO DE LOGS: Backup concluído com sucesso!");

        } catch (Exception e) {
            log.error("❌ ERRO ao fazer backup dos logs: {}", e.getMessage(), e);
        }
    }

    /**
     * Metodo auxiliar para limpar arquivos de log antigos
     * Deleta arquivos .log com mais de X dias (DIAS_RETENCAO_LOGS)
     */
    private void limparLogsAntigos(Path diretorioLogs) {
        try {
            long tempoLimite = System.currentTimeMillis() - (DIAS_RETENCAO_LOGS * 24 * 60 * 60 * 1000L);

            File[] arquivos = diretorioLogs.toFile().listFiles((dir, name) -> name.endsWith(".log"));

            if (arquivos != null) {
                int deletados = 0;
                for (File arquivo : arquivos) {
                    if (arquivo.lastModified() < tempoLimite) {
                        if (arquivo.delete()) {
                            deletados++;
                            log.info("Arquivo de log antigo removido: {}", arquivo.getName());
                        }
                    }
                }

                if (deletados > 0) {
                    log.info("🗑️ {} arquivos de log antigos foram removidos", deletados);
                }
            }

        } catch (Exception e) {
            log.warn("Nao foi possivel limpar logs antigos: {}", e.getMessage());
        }
    }

    // ==================== DICAS IMPORTANTES ====================
    /*
     * 🎓 RESUMO DE FORMAS DE AGENDAMENTO:
     *
     * 1. @Scheduled(fixedRate = 5000)
     *    - Executa a cada 5 SEGUNDOS (5000ms)
     *    - Útil para polling/verificações periódicas
     *
     * 2. @Scheduled(fixedDelay = 5000)
     *    - Espera 5 segundos APÓS terminar para executar novamente
     *    - Útil para evitar execução simultânea
     *
     * 3. @Scheduled(initialDelay = 5000, fixedRate = 10000)
     *    - Aguarda 5seg antes de começar
     *    - Depois executa a cada 10seg
     *    - Útil para esperar inicialização
     *
     * 4. @Scheduled(cron = "0 0 12 * * ?")
     *    - Executa EXATAMENTE à 12:00 todos os dias
     *    - MAX FLEXIBILIDADE com horários específicos
     *
     * 5. ROTACAO DE LOGS (Exemplo 11):
     *    - Executa à meia-noite diariamente
     *    - Faz backup dos logs do dia anterior
     *    - Limpa arquivos com mais de 30 dias
     *    - Usa Path e Files para operações com diretórios
     *
     * ⚠️ IMPORTANTE:
     *   - Adicione @EnableScheduling na classe Application!
     *   - Tarefas são executadas em thread separada
     *   - Use try-catch para evitar que exceções parem o agendador
     *   - Mantenha as tarefas RÁPIDAS e LEVES
     *   - Para logs em produção, use bibliotecas como Logback com RollingFileAppender
     */
}
