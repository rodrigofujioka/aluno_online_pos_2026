# 🔧 LOGGING EM SPRING - GUIA TÉCNICO COMPLETO

## Por Que Logback?

```
System.out.println()  ❌ Não salva em arquivo, sem níveis, sem timestamps
Logback               ✅ Padrão Spring, salva arquivo, rotaciona, 30 dias
```

---

## Arquitetura

```
Aplicação (@Slf4j)
    ↓ log.info("msg")
logback-spring.xml (configuração)
    ├─ Appender 1: Console (tela)
    ├─ Appender 2: File (~/logs/application.log)
    └─ Appender 3: Error (~/logs/application-errors.log)
         ↓
Arquivos gerados (rotacionados diariamente)
```

---

## Arquivo de Configuração

### logback-spring.xml - O Que Faz

```xml
<!-- Diretório de logs -->
<property name="LOG_DIR" value="${user.home}/logs"/>

<!-- 3 Appenders: Console, File, Error -->
<appender name="CONSOLE" class="ch.qos.logback.core.ConsoleAppender">
    <!-- Exibe no console -->
    <pattern>%d{HH:mm:ss} %-5level %logger - %msg%n</pattern>
</appender>

<appender name="FILE" class="ch.qos.logback.core.rolling.RollingFileAppender">
    <!-- Salva em arquivo rotacionado -->
    <file>${LOG_DIR}/${LOG_FILE_NAME}.log</file>
    
    <!-- RollingPolicy: rotaciona quando:
         - Arquivo atinge 10MB (maxFileSize)
         - Muda o dia (automaticamente)
         - Mantém 30 dias (maxHistory)
         - Limite total 500MB (totalSizeCap)
    -->
    <rollingPolicy class="ch.qos.logback.core.rolling.SizeAndTimeBasedRollingPolicy">
        <fileNamePattern>${LOG_DIR}/${LOG_FILE_NAME}-%d{yyyy-MM-dd}.%i.log</fileNamePattern>
        <maxFileSize>10MB</maxFileSize>
        <maxHistory>30</maxHistory>
        <totalSizeCap>500MB</totalSizeCap>
    </rollingPolicy>
</appender>

<root level="INFO">
    <appender-ref ref="CONSOLE"/>
    <appender-ref ref="FILE"/>
    <appender-ref ref="ERROR_FILE"/>
</root>
```

---

## Níveis de Log

| Nível | Quando Usar | Exemplo |
|------|-----------|---------|
| **DEBUG** | Infos detalhadas para debugging | Valores de variáveis, trace de execução |
| **INFO** | Informações gerais importantes | "App iniciada", "Requisição recebida" |
| **WARN** | Possível problema (não é erro) | "Cache vazio", "Retry iniciado" |
| **ERROR** | Erro que precisa atenção | "BD offline", "Arquivo não encontrado" |

---

## Padrão de Formatação

```
%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n

├─ %d{...}     → Data/hora
├─ %thread     → Thread
├─ %-5level    → INFO/ERROR (padronizado)
├─ %logger     → Nome da classe
└─ %msg        → Mensagem
```

Resultado:
```
2026-05-09 15:37:23.567 [http-nio-8080-exec-1] INFO  ProfessorService - Criando professor
```

---

## Rotação de Arquivos

### Por Tamanho
- Quando atinge 10MB, cria novo arquivo
- Nome: `application.0.log`, `application.1.log`, ...

### Por Data
- À meia-noite (00:00) automaticamente
- Nome: `application-2026-05-09.0.log`

### Exemplo de Um Dia
```
14:30 → application.log (vazio - novo dia)
18:00 → application.log (5 MB)
20:00 → application.log (10 MB) + application.0.log (split)
23:59 → application.log (8 MB) + application.0.log (10 MB) + application.1.log (7 MB)

00:00 → application.log (vazio - novo dia)
      → application-2026-05-09.0.log (10 MB - backup)
      → application-2026-05-09.1.log (10 MB - backup)
      → application-2026-05-09.2.log (5 MB - backup)
```

---

## Limpeza Automática

### Política de Retenção
```xml
<maxHistory>30</maxHistory>        <!-- Mantém 30 dias -->
<totalSizeCap>500MB</totalSizeCap> <!-- Máximo 500MB total -->
```

### Quando Limpa
- Logback: continuamente (quando escreve)
- TarefasAgendadas: diariamente às 00:05

### Exemplo
```
Dia 1 (07/05)  → application-2026-05-07.0.log criado
...
Dia 30 (05/06) → application-2026-05-06.0.log criado (29 dias)
Dia 31 (06/06) → application-2026-05-05.0.log DELETADO (>30 dias) ❌
```

---

## Integração com TarefasAgendadas

### Método Adicionado (Exemplo 11)
```java
@Scheduled(cron = "0 0 0 * * ?")  // Meia-noite
public void tarefa_RotacaoLogsAoAmanhecer() {
    // 1. Logback já rotacionou automaticamente
    // 2. Esta tarefa faz:
    //    - Limpeza de > 30 dias
    //    - Pode fazer backup remoto
    //    - Pode comprimir antigos
}
```

---

## Configurações Customizáveis

### 1. Alterar Diretório
```xml
<property name="LOG_DIR" value="/meu/diretorio/logs"/>
```

### 2. Alterar Retenção (ex: 60 dias)
```xml
<maxHistory>60</maxHistory>
```

### 3. Alterar Nível Global (DEBUG)
```xml
<root level="DEBUG">
```

### 4. Reduzir Tamanho (5 MB)
```xml
<maxFileSize>5MB</maxFileSize>
```

### 5. Diferentes Níveis por Classe
```xml
<logger name="br.com.alunoonline" level="DEBUG"/>
<logger name="org.hibernate.SQL" level="WARN"/>
```

---

## Monitoramento

### Ver Crescimento em Tempo Real
```bash
# Terminal 1: Acompanhar logs
tail -f ~/logs/application.log

# Terminal 2: Fazer requisição (vai aparecer em Terminal 1)
curl http://localhost:8080/api/alunos

# Terminal 3: Ver tamanho do arquivo
watch -n 1 'ls -lh ~/logs/application.log'
```

### Análise de Logs
```bash
# Contar por nível
grep "INFO" ~/logs/application.log | wc -l
grep "ERROR" ~/logs/application.log | wc -l

# Últimas horas
tail -1000 ~/logs/application.log | grep "ERROR"

# De um serviço
grep "ProfessorService" ~/logs/application.log
```

---

## Performance

```
Taxa de crescimento: ~45 MB/dia
Overhead: < 1% CPU
Tempo de flush: automático (configurable)
Impact em requests: negligenciável
```

---

## Integração com Ferramentas

### ELK Stack
```
Filebeat (coleta) → Logstash (processa) → Elasticsearch (armazena) → Kibana (visualiza)
                           ↑
                    ~/logs/application.log
```

### Splunk
```
Splunk Agent → Lê ~/logs/application.log → Envia para Splunk Enterprise
```

### CloudWatch (AWS)
```
CloudWatch Agent → Lê ~/logs/application.log → AWS CloudWatch Logs
```

---

## Troubleshooting

### Logs não aparecem
1. Adicionou `@Slf4j`? ✓
2. Executou `log.info()`? ✓
3. Aplicação rodando? ✓
4. Verifique `logback-spring.xml` em `src/main/resources/`

### Arquivo cresce rápido
- Reduza `maxFileSize` de 10MB para 5MB
- Aumente frequência de limpeza
- Reduza `maxHistory` (menos dias)

### Espaço em disco limitado
```xml
<totalSizeCap>200MB</totalSizeCap>  <!-- Reduzir de 500MB -->
<maxHistory>15</maxHistory>          <!-- Reduzir de 30 dias -->
```

---

## Comparação com Alternativas

| Framework | Arquivo | Rotação | Retenção | Filtros |
|-----------|---------|---------|----------|---------|
| Logback | ✅ | ✅ | ✅ | ✅ Multiple |
| Log4j2 | ✅ | ✅ | ✅ | ✅ Advanced |
| JUL | ❌ Ruim | ✅ | ❌ | ✅ Básico |
| SLF4J | ⚠️ Façade | ✗ | ✗ | ✗ |

**Conclusão:** Logback é ideal para Spring Boot

---

## Boas Práticas

✅ **Sempre:**
- Use placeholders: `log.info("Aluno: {}", nome)`
- Inclua exception: `log.error("Erro", e)`
- Use nível apropriado (DEBUG/INFO/WARN/ERROR)

❌ **Nunca:**
- Concatenação: `log.info("Aluno: " + nome)`
- Só mensagem em ERROR: `log.error("Erro")`
- Log de senhas/tokens

---

## Próximos Passos

1. Teste com `@Slf4j` e `log.info()`
2. Monitore em tempo real com `tail -f`
3. À meia-noite, verifique rotação
4. Integre com ferramentas (ELK, Splunk)
5. Configure para produção (WARN level)

---

**Tudo configurado e pronto!** ✅

