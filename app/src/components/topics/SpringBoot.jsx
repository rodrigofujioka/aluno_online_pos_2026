import TopicCard from '../TopicCard';
import CodeBlock from '../CodeBlock';

export default function SpringBoot() {
  return (
    <TopicCard
      emoji="🚀"
      title="Spring Boot"
      description="Framework Java para criar aplicações faster, easier, and faster"
    >
      <p className="text-gray-700 mb-4">
        Spring Boot simplifica a criação de aplicações Spring produção-ready através de:
        configuração automática, dependências pré-configuradas e embedded servers.
      </p>

      <h3 className="text-xl font-semibold mb-3 text-gray-800">🏃 Estrutura Básica</h3>

      <CodeBlock
        language="java"
        title="AlunoOnlineApplication.java"
        code={`@SpringBootApplication
@EnableScheduling
public class AlunoOnlineApplication {
    public static void main(String[] args) {
        SpringApplication.run(AlunoOnlineApplication.class, args);
    }
}

// @SpringBootApplication = @Configuration + @ComponentScan + @EnableAutoConfiguration
// @EnableScheduling = Ativa o processador de tarefas agendadas`}
      />

      <h3 className="text-xl font-semibold mb-3 text-gray-800">⚙️ Componentes Essenciais</h3>

      <div className="grid grid-cols-1 md:grid-cols-2 gap-4 mb-4">
        <div className="bg-emerald-50 border-l-4 border-emerald-500 p-4 rounded">
          <h4 className="font-semibold text-emerald-900 mb-2">@Component</h4>
          <p className="text-gray-700 text-sm">Classe gerenciada pelo Spring</p>
        </div>
        <div className="bg-emerald-50 border-l-4 border-emerald-500 p-4 rounded">
          <h4 className="font-semibold text-emerald-900 mb-2">@Service</h4>
          <p className="text-gray-700 text-sm">Lógica de negócio</p>
        </div>
        <div className="bg-emerald-50 border-l-4 border-emerald-500 p-4 rounded">
          <h4 className="font-semibold text-emerald-900 mb-2">@Repository</h4>
          <p className="text-gray-700 text-sm">Acesso a dados</p>
        </div>
        <div className="bg-emerald-50 border-l-4 border-emerald-500 p-4 rounded">
          <h4 className="font-semibold text-emerald-900 mb-2">@RestController</h4>
          <p className="text-gray-700 text-sm">Endpoints REST</p>
        </div>
      </div>

      <div className="bg-yellow-50 border-l-4 border-yellow-500 p-4 rounded">
        <h4 className="font-semibold text-yellow-900 mb-2">📌 application.properties</h4>
        <CodeBlock
          language="properties"
          code={`spring.application.name=aluno-online
spring.datasource.url=jdbc:postgresql://localhost:5432/aluno_online
spring.datasource.username=postgres
spring.datasource.password=password
spring.jpa.hibernate.ddl-auto=update
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
server.port=8080`}
        />
      </div>
    </TopicCard>
  );
}

