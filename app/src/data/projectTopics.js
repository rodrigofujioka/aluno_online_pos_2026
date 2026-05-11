export const topicPages = [
  { key: 'lombok', slug: 'lombok', menuLabel: '1) Lombok' },
  { key: 'dto', slug: 'dto', menuLabel: '2) DTO' },
  { key: 'springBoot', slug: 'spring-boot', menuLabel: '3) Spring Boot' },
  { key: 'springData', slug: 'spring-data', menuLabel: '4) Spring Data' },
  { key: 'orm', slug: 'orm', menuLabel: '5) ORM' },
  { key: 'jpql', slug: 'jpql', menuLabel: '6) JPQL' },
  { key: 'schedule', slug: 'schedule', menuLabel: '7) Schedule' },
  { key: 'cache', slug: 'cache', menuLabel: '8) Cache' },
  { key: 'feign', slug: 'feign', menuLabel: '9) Feign' },
  { key: 'customBeanValidators', slug: 'custom-bean-validation', menuLabel: '10) Custom Validator' },
  { key: 'pagination', slug: 'pageable', menuLabel: '11) Pageable' },
  { key: 'logs', slug: 'logs', menuLabel: '12) Logs' },
];

export const topics = {
  lombok: {
    emoji: '🏗️',
    title: 'Lombok',
    description: 'Reduz código repetitivo em entidades e DTOs.',
    quickSummary: 'Use Lombok para diminuir boilerplate e deixar o foco na regra de negócio.',
    lessonHighlights: [
      'Anotações como @Data e @NoArgsConstructor eliminam getters/setters e construtores manuais.',
      'A classe fica mais curta e fácil de explicar em aula.',
      'No projeto, Lombok aparece em model, dto e resposta de erro.',
    ],
    studySteps: [
      '1. Identifique anotações como @Data, @NoArgsConstructor e @AllArgsConstructor.',
      '2. Compare a classe com e sem Lombok para ver a redução de boilerplate.',
      '3. Repare que o mesmo padrão aparece em model, dto e exception response.',
    ],
    sourceDocs: [],
    filePaths: [
      'src/main/java/br/com/alunoonline/api/model/Professor.java',
      'src/main/java/br/com/alunoonline/api/dto/professor/ProfessorResponseDTO.java',
      'src/main/java/br/com/alunoonline/api/exception/ErrorResponse.java',
    ],
    examples: [
      {
        language: 'java',
        title: 'Professor.java',
        code: `@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "professor")
@Entity
public class Professor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nomeCompleto;
    private String email;
    private String cpf;
    private String endereco;
}`,
      },
    ],
  },
  dto: {
    emoji: '📦',
    title: 'DTO com ModelMapper',
    description: 'Separa contrato da API da entidade do banco.',
    quickSummary: 'DTO protege dados sensíveis e mantém o contrato da API estável mesmo com mudanças no banco.',
    lessonHighlights: [
      'No fluxo de Professor, o CPF existe na entidade, mas pode não ser exposto em listagens.',
      'RequestDTO recebe validações de entrada; ResponseDTO define o que sai para o cliente.',
      'ModelMapper reduz mapeamento manual entre DTO e entidade.',
    ],
    studySteps: [
      '1. Veja no RequestDTO as validações de entrada.',
      '2. Veja no ResponseDTO quais campos voltam para o cliente.',
      '3. No service, acompanhe o map() de DTO para entidade e vice-versa.',
    ],
    sourceDocs: ['DTO_COM_MAPPER.md'],
    filePaths: [
      'src/main/java/br/com/alunoonline/api/dto/professor/ProfessorRequestDTO.java',
      'src/main/java/br/com/alunoonline/api/dto/professor/ProfessorResponseDTO.java',
      'src/main/java/br/com/alunoonline/api/service/ProfessorService.java',
    ],
    examples: [
      {
        language: 'java',
        title: 'ProfessorRequestDTO.java',
        code: `@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfessorRequestDTO {
    @NotBlank(message = "Nome completo é obrigatório")
    @Size(min = 3, max = 100)
    @NomeSemPalavrao
    private String nomeCompleto;

    @Email(message = "E-mail inválido")
    @NotBlank(message = "E-mail é obrigatório")
    private String email;
}`,
      },
      {
        language: 'java',
        title: 'ProfessorService.java',
        code: `Professor professor = modelMapper.map(professorRequestDTO, Professor.class);
Professor professorSalvo = professorRepository.save(professor);
return modelMapper.map(professorSalvo, ProfessorResponseDTO.class);`,
      },
    ],
  },
  springBoot: {
    emoji: '🚀',
    title: 'Spring Boot',
    description: 'Classe principal habilita os recursos do projeto.',
    quickSummary: 'A classe principal centraliza os recursos da aplicação e simplifica bootstrap.',
    lessonHighlights: [
      '@SpringBootApplication combina configuração, scan e auto-configuração.',
      'Neste projeto também habilitamos cache, scheduling e Feign na mesma classe.',
      'Isso ajuda o aluno a ver a arquitetura geral logo no início.',
    ],
    studySteps: [
      '1. Entenda o papel de @SpringBootApplication.',
      '2. Veja as features ativadas com @EnableCaching, @EnableScheduling e @EnableFeignClients.',
      '3. Relacione essas anotações com os tópicos das outras páginas.',
    ],
    sourceDocs: [],
    filePaths: ['src/main/java/br/com/alunoonline/api/AlunoOnlineApplication.java'],
    examples: [
      {
        language: 'java',
        title: 'AlunoOnlineApplication.java',
        code: `@SpringBootApplication
@EnableCaching
@EnableScheduling
@EnableFeignClients
public class AlunoOnlineApplication {
    public static void main(String[] args) {
        SpringApplication.run(AlunoOnlineApplication.class, args);
    }
}`,
      },
    ],
  },
  springData: {
    emoji: '💾',
    title: 'Spring Data JPA',
    description: 'Acesso a dados com repository e paginação nativa.',
    quickSummary: 'Com JpaRepository você ganha CRUD e paginação sem SQL manual na maior parte dos casos.',
    lessonHighlights: [
      'Repository herda operações prontas de persistência.',
      'findAll(pageable) já retorna dados paginados.',
      'Service pode mapear entidade para DTO em cadeia com map().',
    ],
    studySteps: [
      '1. Veja que o repository herda JpaRepository.',
      '2. Confira como findAll(pageable) traz paginação sem SQL manual.',
      '3. Observe o map() para transformar entidade em DTO.',
    ],
    sourceDocs: [],
    filePaths: [
      'src/main/java/br/com/alunoonline/api/repository/ProfessorRepository.java',
      'src/main/java/br/com/alunoonline/api/service/ProfessorService.java',
    ],
    examples: [
      {
        language: 'java',
        title: 'ProfessorRepository.java',
        code: `@Repository
public interface ProfessorRepository extends JpaRepository<Professor, Long> {
}`,
      },
    ],
  },
  orm: {
    emoji: '🗄️',
    title: 'ORM',
    description: 'Mapeia classes Java para tabelas do banco.',
    quickSummary: 'ORM conecta o mundo orientado a objetos ao banco relacional com anotações JPA.',
    lessonHighlights: [
      '@Entity transforma a classe em entidade persistente.',
      '@Table define a tabela no banco.',
      '@Id e @GeneratedValue definem chave primária e estratégia de geração.',
    ],
    studySteps: [
      '1. Identifique @Entity e @Table.',
      '2. Veja como @Id e @GeneratedValue definem chave primária.',
      '3. Repare que cada atributo vira coluna no banco.',
    ],
    sourceDocs: [],
    filePaths: [
      'src/main/java/br/com/alunoonline/api/model/Professor.java',
      'src/main/java/br/com/alunoonline/api/model/Disciplina.java',
    ],
    examples: [
      {
        language: 'java',
        title: 'Professor.java',
        code: `@Table(name = "professor")
@Entity
public class Professor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nomeCompleto;
    private String email;
}`,
      },
    ],
  },
  jpql: {
    emoji: '🔍',
    title: 'JPQL',
    description: 'Query orientada a entidade Java.',
    quickSummary: 'JPQL consulta objetos (entidades), não tabelas diretamente.',
    lessonHighlights: [
      'findByCpf é query derivada pelo nome do método.',
      '@Query permite escrever consulta custom orientada à entidade.',
      '@Param deixa o código mais legível ao passar parâmetros nomeados.',
    ],
    studySteps: [
      '1. Compare findByCpf (derivada) com @Query (customizada).',
      '2. Repare que JPQL usa a entidade Aluno, não nome de tabela SQL.',
      '3. Use @Param para passar parâmetros nomeados.',
    ],
    sourceDocs: [],
    filePaths: ['src/main/java/br/com/alunoonline/api/repository/AlunoRepository.java'],
    examples: [
      {
        language: 'java',
        title: 'AlunoRepository.java',
        code: `Optional<Aluno> findByCpf(String cpf);

@Query("SELECT a FROM Aluno a WHERE a.cpf = :cpf")
Optional<Aluno> buscaPorCpf(@Param("cpf") String cpf);`,
      },
    ],
  },
  schedule: {
    emoji: '⏰',
    title: 'Schedule com Spring',
    description: 'Execução automática de tarefas por intervalo ou cron.',
    quickSummary: 'Agendamentos permitem automatizar tarefas técnicas e de negócio sem intervenção manual.',
    lessonHighlights: [
      'fixedRate dispara com intervalo fixo entre inícios de execução.',
      'fixedDelay espera o término para começar a próxima.',
      'cron dá flexibilidade para horários específicos (ex.: meia-noite).',
    ],
    studySteps: [
      '1. Veja diferença entre fixedRate, fixedDelay e cron.',
      '2. Confira exemplos diários e de dias úteis.',
      '3. Veja o caso real de limpeza/rotação de logs.',
    ],
    sourceDocs: [],
    filePaths: ['src/main/java/br/com/alunoonline/api/schedule/TarefasAgendadas.java'],
    examples: [
      {
        language: 'java',
        title: 'TarefasAgendadas.java',
        code: `@Scheduled(fixedRate = 10000)
public void tarefa1_FixedRate() {
    log.info("TAREFA 1 - FIXED RATE");
}

@Scheduled(cron = "0 0 0 * * ?")
public void tarefa_RotacaoLogsAoAmanhecer() {
    limparLogsAntigos(caminhoPasta);
}`,
      },
    ],
  },
  cache: {
    emoji: '⚡',
    title: 'Spring Cache',
    description: 'Cache para reduzir chamadas repetidas.',
    quickSummary: 'Cache melhora tempo de resposta para leituras repetidas e invalidação garante consistência.',
    lessonHighlights: [
      '@Cacheable guarda o resultado da consulta.',
      '@CacheEvict limpa o cache quando há alteração de dados.',
      'No projeto, a lista de alunos usa esse padrão.',
    ],
    studySteps: [
      '1. Use @Cacheable na leitura frequente.',
      '2. Use @CacheEvict na escrita para invalidar cache.',
      '3. Relacione cache com performance da listagem.',
    ],
    sourceDocs: [],
    filePaths: ['src/main/java/br/com/alunoonline/api/controller/AlunoController.java'],
    examples: [
      {
        language: 'java',
        title: 'AlunoController.java',
        code: `@CacheEvict(value = "LISTA_ALUNOS", allEntries = true)
@PostMapping
public void criarAluno(@Valid @RequestBody Aluno aluno) {
    alunoService.criarAluno(aluno);
}

@GetMapping
@Cacheable("LISTA_ALUNOS")
public List<Aluno> buscarTodosAlunos() {
    return alunoService.buscarTodosAlunos();
}`,
      },
    ],
  },
  feign: {
    emoji: '🔗',
    title: 'Feign / OpenFeign',
    description: 'Client HTTP declarativo para consumir ViaCEP.',
    quickSummary: 'Feign simplifica integração externa usando interface Java anotada.',
    lessonHighlights: [
      'No cadastro de professor, o CEP alimenta a consulta no ViaCEP.',
      'Service monta endereço automaticamente com os campos retornados.',
      'Tratamento de erro diferencia CEP inválido de falha externa.',
    ],
    studySteps: [
      '1. Veja a interface anotada com @FeignClient.',
      '2. Siga o fluxo do CEP no service até montagem do endereço.',
      '3. Observe tratamento de erro para API externa.',
    ],
    sourceDocs: ['AULA_FEIGN_OPENFEIGN.md'],
    filePaths: [
      'src/main/java/br/com/alunoonline/api/client/viacep/ViaCepClient.java',
      'src/main/java/br/com/alunoonline/api/service/ProfessorService.java',
    ],
    examples: [
      {
        language: 'java',
        title: 'ViaCepClient.java',
        code: `@FeignClient(name = "viaCepClient", url = "https://viacep.com.br/ws")
public interface ViaCepClient {
    @GetMapping("/{cep}/json/")
    ViaCepResponseDTO buscarCep(@PathVariable String cep);
}`,
      },
    ],
  },
  customBeanValidators: {
    emoji: '✅',
    title: 'Custom Bean Validation',
    description: 'Validação de regra de negócio com anotação custom.',
    quickSummary: 'Regras personalizadas complementam validações padrão para proteger o domínio.',
    lessonHighlights: [
      '@NotBlank valida presença; anotação custom valida conteúdo do texto.',
      'A regra custom fica centralizada e reaproveitável.',
      'O mesmo padrão usado em Professor pode ser replicado em Disciplina.',
    ],
    studySteps: [
      '1. Veja a anotação @NomeSemPalavrao.',
      '2. Veja o validador que implementa a regra.',
      '3. Veja a anotação aplicada no DTO de entrada.',
    ],
    sourceDocs: ['AULA_CUSTOM_BEAN_VALIDATION_E_PROMPTS_DISCIPLINA.md'],
    filePaths: [
      'src/main/java/br/com/alunoonline/api/validation/nome/NomeSemPalavrao.java',
      'src/main/java/br/com/alunoonline/api/validation/nome/NomeSemPalavraoValidator.java',
      'src/main/java/br/com/alunoonline/api/dto/professor/ProfessorRequestDTO.java',
    ],
    examples: [
      {
        language: 'java',
        title: 'NomeSemPalavrao.java',
        code: `@Documented
@Constraint(validatedBy = NomeSemPalavraoValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface NomeSemPalavrao {
    String message() default "Não é permitido palavrâo no nome do prof";
}`,
      },
      {
        language: 'java',
        title: 'ProfessorRequestDTO.java',
        code: `@NotBlank(message = "Nome completo é obrigatório")
@Size(min = 3, max = 100)
@NomeSemPalavrao
private String nomeCompleto;`,
      },
    ],
  },
  pagination: {
    emoji: '📄',
    title: 'Paginação com Pageable',
    description: 'Controle de página, tamanho e ordenação.',
    quickSummary: 'Pageable divide grandes listas e melhora desempenho da API.',
    lessonHighlights: [
      'page começa em 0, size controla quantidade e sort ordena resultado.',
      'Controller define padrão com @PageableDefault.',
      'Service retorna Page<DTO> com metadados úteis para a UI.',
    ],
    studySteps: [
      '1. Veja Pageable no controller com @PageableDefault.',
      '2. Veja Page<DTO> no retorno do service.',
      '3. Teste sort no formato campo,direcao (ex.: nomeCompleto,asc).',
    ],
    sourceDocs: ['BUSCA_PAGINADA_PROFESSOR.md'],
    filePaths: [
      'src/main/java/br/com/alunoonline/api/controller/ProfessorController.java',
      'src/main/java/br/com/alunoonline/api/service/ProfessorService.java',
    ],
    examples: [
      {
        language: 'java',
        title: 'ProfessorController.java',
        code: `@GetMapping
public Page<ProfessorResponseDTO> listarProfessores(
    @PageableDefault(page = 0, size = 10, sort = "nomeCompleto") Pageable pageable) {
    return professorService.listarProfessores(pageable);
}`,
      },
    ],
  },
  logs: {
    emoji: '📝',
    title: 'Logs',
    description: 'Monitoramento, auditoria e tratamento de erro.',
    quickSummary: 'Logs ajudam a entender comportamento da aplicação e diagnosticar falhas rapidamente.',
    lessonHighlights: [
      'Use níveis corretos: debug, info, warn e error.',
      'Tratamento global de exceção registra erro técnico e responde de forma padronizada.',
      'Projeto inclui exemplo de rotação e limpeza de logs agendada.',
    ],
    studySteps: [
      '1. Veja níveis de log (debug, info, warn, error).',
      '2. Veja logs no tratamento global de exceção.',
      '3. Veja o agendamento de limpeza/rotação de logs.',
    ],
    sourceDocs: ['LOGGING.md'],
    filePaths: [
      'src/main/java/br/com/alunoonline/api/example/ExemploLoggingService.java',
      'src/main/java/br/com/alunoonline/api/exception/RestControllerException.java',
      'src/main/java/br/com/alunoonline/api/schedule/TarefasAgendadas.java',
    ],
    examples: [
      {
        language: 'java',
        title: 'ExemploLoggingService.java',
        code: `log.debug("DEBUG: Variável x = {}", 42);
log.info("INFO: Processamento iniciado");
log.warn("WARN: Cache não foi encontrado, usando fallback");
log.error("ERROR: Falha ao conectar com banco de dados");`,
      },
      {
        language: 'java',
        title: 'RestControllerException.java',
        code: `@ExceptionHandler(Exception.class)
public ResponseEntity<ErrorResponse> handleGenericException(Exception ex, HttpServletRequest request) {
    log.error("Erro interno no servidor na requisição [{} {}]", request.getMethod(), request.getRequestURI(), ex);
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
}`,
      },
    ],
  },
};
