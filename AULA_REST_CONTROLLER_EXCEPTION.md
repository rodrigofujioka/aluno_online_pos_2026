# Aula: RestControllerException no Spring Boot

## Objetivo da aula
- Entender o papel do `@RestControllerAdvice`.
- Aplicar tratamento global de excecoes em APIs REST.
- Diferenciar erros de validacao, negocio e erro interno.
- Padronizar resposta de erro com `ErrorResponse`.

---

## Classe base da aula
Arquivo:
- `src/main/java/br/com/alunoonline/api/exception/RestControllerException.java`

A classe centraliza o tratamento de excecoes da aplicacao e evita `try/catch` repetido nos controllers.

---

## Visao geral do fluxo
1. Cliente chama endpoint (`POST`, `GET`, etc).
2. Controller/Service gera excecao.
3. `RestControllerException` intercepta com `@ExceptionHandler`.
4. Monta `ErrorResponse` padrao.
5. Retorna JSON com status HTTP correto.

---

## Estrutura da classe

### 1) `handleMethodArgumentNotValidException`
Captura `MethodArgumentNotValidException`.

Quando acontece:
- Erro em `@Valid @RequestBody` (DTO invalido).

O que faz:
- Le `FieldError` do `BindingResult`.
- Extrai mensagens de validacao.
- Retorna `400 Bad Request`.

Exemplo de uso:
- `@NotBlank`, `@Email`, `@Size`, `@Pattern` em DTO.

---

### 2) `handleConstraintViolationException`
Captura `ConstraintViolationException`.

Quando acontece:
- Violacao em parametros de request.
- Exemplo: `@PathVariable`, `@RequestParam`, validacao em camada de servico.

O que faz:
- Le lista de `ConstraintViolation`.
- Extrai mensagens.
- Retorna `400 Bad Request`.

---

### 3) `handleResponseStatusException`
Captura `ResponseStatusException`.

Quando acontece:
- Regra de negocio dispara erro com status controlado.
- Exemplo: `throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Professor nao encontrado")`.

O que faz:
- Mantem o status original (`404`, `409`, etc).
- Retorna mensagem de negocio no payload.

---

### 4) `handleGenericException`
Captura `Exception` (fallback).

Quando acontece:
- Erros inesperados nao tratados pelos handlers anteriores.

O que faz:
- Loga stack trace completo no servidor.
- Retorna `500 Internal Server Error` com mensagem generica.
- Evita vazar detalhe interno para cliente.

---

## Formato padrao de resposta (`ErrorResponse`)
Campos usados:
- `timestamp`
- `status`
- `error`
- `message`
- `validationErrors`
- `path`

Esse padrao facilita:
- debug
- monitoramento
- consistencia entre endpoints

---

## Exemplos de resposta para mostrar em aula

### Exemplo 1: Erro de validacao (400)
```json
{
  "timestamp": "2026-05-09T10:15:30",
  "status": 400,
  "error": "Bad Request",
  "message": "Erro de validacao nos campos da requisicao",
  "validationErrors": [
    "Nome completo e obrigatorio",
    "E-mail invalido"
  ],
  "path": "/professores"
}
```

### Exemplo 2: Erro de negocio (404)
```json
{
  "timestamp": "2026-05-09T10:16:00",
  "status": 404,
  "error": "Not Found",
  "message": "Professor nao encontrado",
  "validationErrors": null,
  "path": "/professores/999"
}
```

### Exemplo 3: Erro interno (500)
```json
{
  "timestamp": "2026-05-09T10:17:00",
  "status": 500,
  "error": "Internal Server Error",
  "message": "Ocorreu um erro interno no servidor",
  "validationErrors": null,
  "path": "/disciplinas"
}
```

---

## Pontos fortes da implementacao atual
- Tratamento centralizado para toda API.
- Logs com contexto (`metodo`, `path`, quantidade de erros).
- Padrao unico de payload de erro.
- Separacao clara entre validacao, negocio e erro interno.

---

## Boas praticas para reforcar com os alunos
- Use `@Valid` nos DTOs de entrada.
- Lance `ResponseStatusException` para erros de negocio.
- Nao exponha stack trace ao cliente.
- Sempre logue com contexto (endpoint, metodo, status).
- Mantenha mensagens de erro claras e orientadas ao consumidor da API.

---

## Desafio extra
- Criar excecao custom de negocio (ex.: `RegraNegocioException`).
- Adicionar novo `@ExceptionHandler` na mesma classe.
- Retornar `errorCode` no payload para facilitar integracao front-end.

---

## Encerramento
Com `RestControllerException`, a API fica:
- mais limpa
- mais padronizada
- mais facil de manter
- mais profissional no tratamento de erros

Use esta aula como base para qualquer modulo REST no projeto.

