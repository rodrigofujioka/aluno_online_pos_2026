package br.com.alunoonline.api.example;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * EXEMPLO EDUCACIONAL: Como usar Logging em uma aplicação Spring
 *
 * Este componente demonstra as melhores práticas de logging que
 * funcionam com a configuração do logback-spring.xml
 *
 * Os logs serão:
 * 1. Exibidos no console durante desenvolvimento
 * 2. Salvos em ~/logs/application.log
 * 3. Rotacionados automaticamente todos os dias
 * 4. Mantidos por 30 dias, depois deletados
 */
@Slf4j
@Component
public class ExemploLoggingService {

    /**
     * Exemplo 1: Log simples de informação
     */
    public void exemplo1_LogInfo() {
        log.info("✅ Operação iniciada com sucesso");
        log.info("📊 Total de alunos cadastrados: {}", 150);
    }

    /**
     * Exemplo 2: Log com níveis diferentes
     */
    public void exemplo2_NiveisDeLog() {
        log.debug("🔍 DEBUG: Variável x = {}", 42);
        log.info("ℹ️  INFO: Processamento iniciado");
        log.warn("⚠️  WARN: Cache não foi encontrado, usando fallback");
        log.error("❌ ERROR: Falha ao conectar com banco de dados");
    }

    /**
     * Exemplo 3: Log em um método que processa uma lista
     */
    public void exemplo3_ProcessarAlunos(List<String> alunos) {
        log.info("🎓 Iniciando processamento de {} alunos", alunos.size());

        for (String aluno : alunos) {
            try {
                log.debug("   Processando aluno: {}", aluno);
                // Simulando processamento
                Thread.sleep(100);
                log.debug("   ✓ Aluno {} processado com sucesso", aluno);
            } catch (InterruptedException e) {
                log.error("❌ Erro ao processar aluno {}: {}", aluno, e.getMessage());
            }
        }

        log.info("✅ Processamento de alunos concluído");
    }

    /**
     * Exemplo 4: Log com tratamento de exceção
     */
    public void exemplo4_TratamentoErro() {
        try {
            log.info("🔄 Iniciando conexão com banco de dados");

            // Simulando erro
            throw new RuntimeException("Connection timeout");

        } catch (Exception e) {
            log.error("❌ ERRO CRÍTICO: Falha ao conectar com BD", e);
            // e = exceção completa com stack trace
        }
    }

    /**
     * Exemplo 5: Log para auditoria (importantes para rastrear ações)
     */
    public void exemplo5_Auditoria(String usuario, String acao) {
        log.info("🔐 AUDITORIA: Usuário '{}' executou ação '{}'", usuario, acao);
        log.info("   Timestamp: {}", LocalDateTime.now());
        log.info("   IP: 192.168.1.100");
    }

    /**
     * Exemplo 6: Log de performance
     */
    public void exemplo6_Performance() {
        log.info("⏱️  Iniciando cálculo complexo");
        long inicio = System.currentTimeMillis();

        // Simular operação
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            log.error("Erro na simulação", e);
        }

        long duracao = System.currentTimeMillis() - inicio;

        if (duracao > 1500) {
            log.warn("⚠️  Performance: Operação levou {}ms (lento)", duracao);
        } else {
            log.info("✅ Performance: Operação levou {}ms (rápido)", duracao);
        }
    }

    /**
     * Exemplo 7: Padrão de logging estruturado (melhor para parsing)
     */
    public void exemplo7_LogEstruturado(String entityType, Long entityId, String status) {
        log.info("EVENT=entity_update|TYPE={}|ID={}|STATUS={}|TIMESTAMP={}",
                 entityType, entityId, status, LocalDateTime.now());
    }

    /**
     * Exemplo 8: Logging com condicional (debug em desenvolvimento)
     */
    public void exemplo8_CondicionalDebug(String dados) {
        if (log.isDebugEnabled()) {
            log.debug("🔍 DEBUG DETALHADO: Dados recebidos = {}", dados);
            log.debug("   Tamanho: {} bytes", dados.length());
            log.debug("   Tipo: String");
        }

        // Este log sempre aparece
        log.info("Operação realizada");
    }

    /**
     * Exemplo 9: Combinando múltiplas informações
     */
    public void exemplo9_LogCompleto(
            String operacao,
            Object entrada,
            Object resultado,
            long duracao) {

        String separador = "============================================================";
        log.info(separador);
        log.info("OPERAÇÃO: {}", operacao);
        log.info("ENTRADA: {}", entrada);
        log.info("RESULTADO: {}", resultado);
        log.info("DURAÇÃO: {}ms", duracao);
        log.info(separador);
    }

    /**
     * Exemplo de execução
     */
    public static void main(String[] args) {
        // Esta classe seria injetada via @Autowired em um Bean real
        ExemploLoggingService service = new ExemploLoggingService();

        service.exemplo1_LogInfo();
        System.out.println("\n");

        service.exemplo2_NiveisDeLog();
        System.out.println("\n");

        List<String> alunos = new ArrayList<>();
        alunos.add("João Silva");
        alunos.add("Maria Santos");
        alunos.add("Pedro Oliveira");
        service.exemplo3_ProcessarAlunos(alunos);
        System.out.println("\n");

        service.exemplo4_TratamentoErro();
        System.out.println("\n");

        service.exemplo5_Auditoria("admin", "criar_professor");
        System.out.println("\n");

        service.exemplo6_Performance();
        System.out.println("\n");

        service.exemplo7_LogEstruturado("Professor", 123L, "ATIVO");
    }
}


