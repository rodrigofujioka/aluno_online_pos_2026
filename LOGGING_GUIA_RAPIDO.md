# 📚 LOGGING EM SPRING - GUIA RÁPIDO PARA AULA

## ⚡ Em 2 Minutos

```java
// 1. Adicione @Slf4j
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class MinhaClasse {
    
    // 2. Use log em qualquer método
    public void processar() {
        log.info("Iniciando...");         // INFO
        log.debug("Variável x = {}", 42); // DEBUG
        log.warn("Aviso");                // WARN
        log.error("Erro!", exception);    // ERROR
    }
}
```

**Pronto!** Logs aparecem em:
- Tela (console)
- Arquivo: `~/logs/application.log`
- Erros também em: `~/logs/application-errors.log`

---

## 🎯 Exemplo Completo em Aula

### Seu Código
```java
@Slf4j
@Service
public class ProfessorService {
    
    public Professor criar(String nome) {
        log.info("🎓 Criando professor: {}", nome);
        
        try {
            Professor prof = new Professor(nome);
            prof = repository.save(prof);
            log.info("✅ Professor criado com ID: {}", prof.getId());
            return prof;
        } catch (Exception e) {
            log.error("❌ Erro ao criar professor", e);
            throw e;
        }
    }
}
```

### O Que Aparece no Console
```
15:37:23.567 [exec-1] INFO  ProfessorService - 🎓 Criando professor: Dr. Silva
15:37:23.589 [exec-1] INFO  ProfessorService - ✅ Professor criado com ID: 42
```

### O Que Fica Salvo em ~/logs/application.log
```
2026-05-09 15:37:23.567 [http-nio-8080-exec-1] INFO  br.com.alunoonline.api.service.ProfessorService - 🎓 Criando professor: Dr. Silva
2026-05-09 15:37:23.589 [http-nio-8080-exec-1] INFO  br.com.alunoonline.api.service.ProfessorService - ✅ Professor criado com ID: 42
```

---

## 4️⃣ Níveis de Log (Use Conforme a Situação)

```java
log.debug("🔍 Valor de x = {}", x);                      // Detalhes (só DEV)
log.info("ℹ️  Operação concluída");                       // Info importante
log.warn("⚠️  Cache vazio, usando fallback");             // Aviso
log.error("❌ Erro ao conectar com BD: {}", e, exception); // Erro crítico
```

---

## 📊 Monitoramento em Tempo Real

### Terminal (Ver últimas 50 linhas)
```bash
# Windows PowerShell
Get-Content "$env:USERPROFILE\logs\application.log" -Tail 50

# Linux/Mac
tail -50 ~/logs/application.log
```

### Terminal (Acompanhar em Tempo Real)
```bash
# Windows PowerShell
Get-Content "$env:USERPROFILE\logs\application.log" -Tail 50 -Wait

# Linux/Mac
tail -f ~/logs/application.log
```

---

## 🔄 O Que Acontece Automaticamente

| O Quê | Quando | Resultado |
|------|--------|----------|
| **Rotação** | 00:00 cada dia | `application.log` → `application-2026-05-09.0.log` |
| **Limpeza** | 00:05 cada dia | Deleta arquivos > 30 dias |
| **Limite** | Contínuo | Máximo 500 MB total |

---

## ✅ Estrutura de Arquivos Gerados

```
~/logs/
├── application.log              ← Atual (crescendo)
├── application-2026-05-09.0.log ← Backup (meia-noite)
├── application-2026-05-08.0.log ← Backup (dia anterior)
├── ... [próximos 28 dias] ...
├── application-errors.log       ← Erros atuais
└── application-errors-2026-05-09.0.log
```

---

## 🎓 Boas Práticas

✅ **Faça:**
```java
log.info("Operação: {}", parametro);     // Placeholders
log.error("Erro", exception);             // Sempre com exception
log.debug("x={}", x);                     // Debug em DEV apenas
```

❌ **Evite:**
```java
log.info("Operação: " + parametro);      // Concatenação
System.out.println("msg");                // Não salva em arquivo
log.error("Erro: " + e.toString());       // Perde stack trace
```

---

## 📖 Exemplos Práticos

### Exemplo 1: Listagem
```java
@Slf4j
@Service
public class AlunoService {
    public List<Aluno> listar() {
        log.info("📊 Buscando todos os alunos");
        List<Aluno> alunos = repository.findAll();
        log.info("✅ Total: {} alunos", alunos.size());
        return alunos;
    }
}
```

### Exemplo 2: Validação
```java
@Slf4j
@Service
public class ValidacaoService {
    public boolean validarCPF(String cpf) {
        log.debug("Validando CPF: {}", cpf);
        
        if (cpf.equals("00000000000")) {
            log.warn("⚠️ CPF inválido detectado");
            return false;
        }
        
        log.info("✅ CPF válido");
        return true;
    }
}
```

### Exemplo 3: Erro
```java
@Slf4j
@Service
public class BDService {
    public void sincronicar() {
        try {
            log.info("🔄 Iniciando sincronização");
            // operações...
            log.info("✅ Sincronização concluída");
        } catch (Exception e) {
            log.error("❌ Erro ao sincronizar", e);
            throw e;
        }
    }
}
```

---

## 🚀 Teste Agora!

1. Adicione `@Slf4j` em uma classe
2. Coloque `log.info("teste")` em algum método
3. Execute a aplicação
4. Procure em `~/logs/application.log`

---

## 🎯 Resumo dos 3 Arquivos Criados

| Arquivo | Localização | Propósito |
|---------|------------|----------|
| **logback-spring.xml** | `src/main/resources/` | Configura geração de logs ⚙️ |
| **TarefasAgendadas.java** (Exemplo 11) | `src/main/java/.../schedule/` | Rotação à meia-noite 🌙 |
| **ExemploLoggingService.java** | `src/main/java/.../example/` | 9 exemplos de uso 📚 |

---

Para entender a fundo: leia `LOGGING_TECNICO.md`

