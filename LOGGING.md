# 🎯 LOGGING - Geração Automática e Rotação de Arquivos

## ✅ O Que Foi Implementado

Sistema de geração de logs que:
- ✨ Gera arquivos automaticamente (`~/logs/`)
- 🔄 Rotaciona diariamente (meia-noite)
- 🗑️ Limpa logs > 30 dias
- ⚙️ Tudo automático - sem código adicional

---

## 🚀 Como Usar (3 Linhas)

```java
@Slf4j              // ← Adicione isto
@Component
public class MinhaClasse {
    public void fazer() {
        log.info("Fazendo algo");     // ← Use isto
        log.error("Erro!", exception); // ← E isto
    }
}
```

**Pronto!** Logs em: `~/logs/application.log`

---

## 📁 Arquivos Criados

| Arquivo | Local | Propósito |
|---------|-------|----------|
| **logback-spring.xml** | `src/main/resources/` | Configura tudo ⚙️ |
| **TarefasAgendadas.java** (Ex.11) | `src/main/schedule/` | Limpeza à meia-noite 🌙 |
| **ExemploLoggingService.java** | `src/example/` | 9 exemplos de uso 📚 |

---

## 📚 Documentação

| Documento | Para Quem | Leia Se |
|-----------|-----------|--------|
| **LOGGING_GUIA_RAPIDO.md** | Professor em aula | Quer ensinar em 5 min |
| **LOGGING_TECNICO.md** | Alunos curiosos | Quer entender a fundo |

---

## 📊 O Que Acontece

```
Durante o dia:
├─ Você usa: log.info("msg")
└─ Salva em: ~/logs/application.log

À meia-noite:
├─ Logback rotaciona automaticamente
├─ application.log → application-2026-05-09.0.log (backup)
└─ TarefasAgendadas limpa arquivos > 30 dias

Resultado:
├─ Máximo: 30 dias de logs
├─ Limite: 500 MB
└─ Tudo automático ✅
```

---

## 💻 Teste Agora

```bash
# 1. Execute a aplicação
mvn spring-boot:run

# 2. Adicione @Slf4j em uma classe

# 3. Use log.info() em algum método

# 4. Acompanhe os logs
tail -f ~/logs/application.log
```

---

## 🎓 Níveis de Log

```java
log.debug("Detalhes...");        // Só em DEV
log.info("Iniciando...");         // Informações
log.warn("Cuidado!");            // Avisos
log.error("Erro!", exception);   // Erros críticos
```

---

## ✨ Exemplo Prático

```java
@Slf4j
@Service
public class AlunoService {
    
    public List<Aluno> listar() {
        log.info("📊 Buscando alunos");
        List<Aluno> alunos = repository.findAll();
        log.info("✅ Total: {} alunos", alunos.size());
        return alunos;
    }
}
```

Console + ~/logs/application.log:
```
15:37:23.567 INFO AlunoService - 📊 Buscando alunos
15:37:23.589 INFO AlunoService - ✅ Total: 156 alunos
```

---

## 🔍 Monitorar em Tempo Real

```bash
# Windows PowerShell
Get-Content "$env:USERPROFILE\logs\application.log" -Tail 50 -Wait

# Linux/Mac
tail -f ~/logs/application.log
```

---

## 📋 Resumo

| Recurso | Status |
|---------|--------|
| Geração automática | ✅ Ativo |
| Rotação diária | ✅ 00:00 |
| Limpeza (30 dias) | ✅ 00:05 |
| Limite (500 MB) | ✅ Configurado |
| Nível de log | ✅ INFO (dev) |

---

## 📖 Quer Entender Mais?

- **Aula rápida?** → `LOGGING_GUIA_RAPIDO.md`
- **Detalhes técnicos?** → `LOGGING_TECNICO.md`
- **Exemplos de código?** → `ExemploLoggingService.java`

---

**Tudo pronto para usar!** 🎉

Nenhuma configuração adicional necessária.

