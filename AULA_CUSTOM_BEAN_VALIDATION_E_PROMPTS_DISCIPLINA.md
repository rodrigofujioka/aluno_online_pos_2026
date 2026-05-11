# Aula: Custom Bean Validation + Prompts para IA (Disciplina)

## Objetivo da aula
- Entender como criar uma validacao customizada no Spring Boot.
- Revisar o que ja foi feito em `Professor`.
- Reaplicar o mesmo padrao em `Disciplina` com boas praticas.
- Entregar um pacote de prompts para alunos usarem em outras IAs com agentes.

---

## 1) O que ja existe no projeto (base da aula)

Arquivos de referencia:
- `src/main/java/br/com/alunoonline/api/validation/nome/NomeSemPalavrao.java`
- `src/main/java/br/com/alunoonline/api/validation/nome/NomeSemPalavraoValidator.java`
- `src/main/java/br/com/alunoonline/api/dto/professor/ProfessorRequestDTO.java`
- `src/test/java/br/com/alunoonline/api/validation/nome/NomeSemPalavraoValidatorTest.java`

### Fluxo atual (Professor)
1. A anotacao `@NomeSemPalavrao` declara a regra custom.
2. O validador `NomeSemPalavraoValidator` implementa a logica.
3. O DTO `ProfessorRequestDTO` usa `@NomeSemPalavrao` em `nomeCompleto`.
4. O teste valida cenarios aceitos e rejeitados.

Resumo didatico:
- `@NotBlank` cuida de vazio/nulo.
- `@NomeSemPalavrao` cuida de conteudo ofensivo.
- As regras se complementam.

---

## 2) Como aplicar o mesmo padrao em Disciplina

### Situacao atual de Disciplina
Arquivos hoje:
- `src/main/java/br/com/alunoonline/api/model/Disciplina.java`
- `src/main/java/br/com/alunoonline/api/controller/DisciplinaController.java`
- `src/main/java/br/com/alunoonline/api/service/DisciplinaService.java`

Hoje o controller recebe a entidade diretamente:
- `criarDisciplina(@RequestBody Disciplina disciplina)`

### Melhoria recomendada para aula
1. Criar `DisciplinaRequestDTO` com validacoes Bean Validation.
2. Trocar controller para usar `@Valid @RequestBody DisciplinaRequestDTO`.
3. Mapear DTO -> entidade no service (ou mapper).
4. Reusar `@NomeSemPalavrao` no nome da disciplina (se regra de negocio fizer sentido).
5. Criar teste para o novo DTO/validacoes.

### Exemplo de regras para Disciplina (didatico)
- `nome`: `@NotBlank`, `@Size`, `@NomeSemPalavrao`
- `descricao`: `@NotBlank`, `@Size`
- `cargaHoraria`: `@NotNull`, `@Min(20)`, `@Max(200)`
- `professorId`: `@NotNull`

---

## 3) Sequencia de implementacao (checklist para alunos)

- [ ] Criar DTO `DisciplinaRequestDTO`.
- [ ] Adicionar validacoes padrao de Bean Validation.
- [ ] Reusar `@NomeSemPalavrao` no campo `nome`.
- [ ] Atualizar `DisciplinaController` para `@Valid`.
- [ ] Ajustar `DisciplinaService` para receber DTO e persistir entidade.
- [ ] Criar testes de validacao para `DisciplinaRequestDTO`.
- [ ] Rodar testes e validar mensagens de erro.

---

## 4) Prompts prontos para usar em outras IAs com agentes

> Dica para os alunos: sempre informe o contexto do projeto, os caminhos dos arquivos e o resultado esperado.

### Prompt 1 - Diagnostico do estado atual
```text
Voce e um assistente de codigo. Analise o projeto Spring Boot e faca um diagnostico para aplicar Custom Bean Validation em Disciplina, copiando o padrao ja usado em Professor.

Contexto:
- Existe @NomeSemPalavrao em src/main/java/br/com/alunoonline/api/validation/nome/
- Professor usa ProfessorRequestDTO com validacoes
- Disciplina hoje usa entidade direto no controller

Quero:
1) Lista objetiva do que ja existe
2) Gaps tecnicos para Disciplina
3) Plano em passos pequenos, sem alterar ainda
4) Arquivos que precisarao ser criados/alterados
```

### Prompt 2 - Implementacao completa em Disciplina
```text
Implemente Custom Bean Validation na entidade Disciplina reaproveitando o mesmo padrao de Professor.

Regras:
- Criar DisciplinaRequestDTO com validacoes:
  - nome: @NotBlank, @Size(3,100), @NomeSemPalavrao
  - descricao: @NotBlank, @Size(5,255)
  - cargaHoraria: @NotNull, @Min(20), @Max(200)
  - professorId: @NotNull
- Alterar DisciplinaController para usar @Valid @RequestBody DisciplinaRequestDTO
- Ajustar DisciplinaService para mapear DTO -> entidade Disciplina
- Manter codigo limpo e didatico para alunos
- Nao quebrar o que ja funciona no projeto

No final, mostre:
1) Lista de arquivos alterados
2) Trechos principais das mudancas
3) Como testar rapidamente
```

### Prompt 3 - Criar testes de validacao
```text
Crie testes de validacao para DisciplinaRequestDTO, no mesmo estilo de NomeSemPalavraoValidatorTest.

Cenarios minimos:
1) Deve aceitar disciplina valida
2) Deve rejeitar nome em branco
3) Deve rejeitar nome com palavra ofensiva
4) Deve rejeitar cargaHoraria menor que 20
5) Deve rejeitar cargaHoraria maior que 200
6) Deve rejeitar professorId nulo

Use JUnit 5 e jakarta.validation.Validator.
Mostre somente arquivos novos/alterados e explique os casos de teste em bullets.
```

### Prompt 4 - Refino pedagogico (comentarios e clareza)
```text
Revise o codigo implementado para aula.

Objetivo:
- Melhorar nomes de metodos e variaveis
- Adicionar comentarios curtos apenas onde necessario
- Garantir leitura facil para alunos iniciantes
- Manter padrao do projeto (packages, DTO, service, controller)

Entregue:
1) Sugestoes de refatoracao
2) Codigo final refatorado
3) Justificativa curta de cada ajuste
```

### Prompt 5 - Revisao critica (modo code review)
```text
Faca code review focado em riscos e regressao da implementacao de validacao em Disciplina.

Procure por:
- Bugs de validacao
- Possiveis NPEs
- Erros de mapeamento DTO -> entidade
- Falta de testes
- Mensagens de erro inconsistentes

Formato da resposta:
1) Findings por severidade com arquivo/linha
2) Lacunas de teste
3) Checklist do que corrigir antes de merge
```

### Prompt 6 - Versao "agente executor" (fim-a-fim)
```text
Atue como agente executor e implemente fim-a-fim a validacao custom em Disciplina, espelhando Professor.

Passos obrigatorios:
1) Ler arquivos atuais de Professor e Disciplina
2) Criar/alterar arquivos necessarios
3) Criar testes de validacao
4) Rodar compilacao/testes
5) Entregar resumo final objetivo

Restricoes:
- Nao remover funcionalidade existente
- Nao alterar contratos sem necessidade
- Manter padrao de codigo do projeto
```

---

## 5) Prompt unico (copiar e colar) para os alunos

Use este prompt quando quiserem "fazer tudo" de uma vez:

```text
Voce e um especialista em Spring Boot.
Quero replicar em Disciplina o mesmo padrao de validacao custom ja aplicado em Professor.

Projeto:
- Java + Spring Boot
- Validacao custom existente:
  - src/main/java/br/com/alunoonline/api/validation/nome/NomeSemPalavrao.java
  - src/main/java/br/com/alunoonline/api/validation/nome/NomeSemPalavraoValidator.java
- DTO de referencia:
  - src/main/java/br/com/alunoonline/api/dto/professor/ProfessorRequestDTO.java
- Disciplina atual:
  - src/main/java/br/com/alunoonline/api/model/Disciplina.java
  - src/main/java/br/com/alunoonline/api/controller/DisciplinaController.java
  - src/main/java/br/com/alunoonline/api/service/DisciplinaService.java

Tarefa:
1) Criar DisciplinaRequestDTO com Bean Validation
2) Reusar @NomeSemPalavrao no nome da disciplina
3) Trocar controller para @Valid + DTO
4) Ajustar service para mapear DTO -> entidade
5) Criar testes de validacao da Disciplina
6) Rodar compilacao/testes e reportar resultado

Formato de entrega:
- Lista de arquivos alterados
- Codigo final por arquivo
- Explicacao curta do que foi feito
- Comandos para validar localmente
```

---

## 6) Roteiro de aula (40 a 60 min)

- 10 min: Revisao de `@NomeSemPalavrao` e validador.
- 10 min: Diferenca entre receber entidade vs DTO no controller.
- 15 min: Implementacao guiada de `DisciplinaRequestDTO` + `@Valid`.
- 10 min: Testes de validacao.
- 5 a 15 min: Alunos executando os prompts em IA e comparando respostas.

---

## 7) Criterios de avaliacao dos alunos (Faça uma auto crítica como aluno, entendeu o que o Fuji passou ? )

- Entendeu separacao de responsabilidades (`@NotBlank` vs custom validator).
- Aplicou `@Valid` no endpoint corretamente.
- Nao acoplou validacao na entidade quando era caso de DTO.
- Criou testes cobrindo sucesso e falha.
- Consegue explicar por que reaproveitar o validador melhora consistencia.

---

## Encerramento
Este arquivo ja contem:
- Aula de Custom Bean Validation (com base no projeto real).
- Prompts reutilizaveis para outras IAs com agentes.
- Um prompt unico pronto para execucao fim-a-fim.

Pode entregar este unico `.md` para a turma.

