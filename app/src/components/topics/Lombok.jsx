import TopicCard from '../TopicCard';
import CodeBlock from '../CodeBlock';

export default function Lombok() {
  return (
    <TopicCard
      emoji="🏗️"
      title="Lombok"
      description="Reduce repetição de código em classes Java com anotações simples"
    >
      <p className="text-gray-700 mb-4">
        Lombok é uma biblioteca que reduz a quantidade de código boilerplate (caldeiraria) em projetos Java,
        gerando automaticamente getters, setters, construtores e outras operações comuns.
      </p>

      <h3 className="text-xl font-semibold mb-3 text-gray-800">✨ Principais Anotações</h3>

      <CodeBlock
        language="java"
        title="Antes do Lombok"
        code={`public class Professor {
    private Long id;
    private String nomeCompleto;
    private String email;

    public Professor() {}

    public Professor(Long id, String nomeCompleto, String email) {
        this.id = id;
        this.nomeCompleto = nomeCompleto;
        this.email = email;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNomeCompleto() { return nomeCompleto; }
    public void setNomeCompleto(String nomeCompleto) { this.nomeCompleto = nomeCompleto; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}`}
      />

      <CodeBlock
        language="java"
        title="Depois do Lombok"
        code={`@Data
@AllArgsConstructor
@NoArgsConstructor
public class Professor {
    private Long id;
    private String nomeCompleto;
    private String email;
}`}
      />

      <div className="bg-blue-50 border-l-4 border-blue-500 p-4 rounded mb-4">
        <h4 className="font-semibold text-blue-900 mb-2">📌 Anotações Comuns</h4>
        <ul className="text-gray-700 space-y-2 text-sm">
          <li><code>@Data</code> - Gera getters, setters, equals, hashCode, toString</li>
          <li><code>@Getter</code> - Gera apenas getters</li>
          <li><code>@Setter</code> - Gera apenas setters</li>
          <li><code>@NoArgsConstructor</code> - Construtor sem argumentos</li>
          <li><code>@AllArgsConstructor</code> - Construtor com todos os campos</li>
          <li><code>@Slf4j</code> - Injeta logger SLF4J automaticamente</li>
        </ul>
      </div>

      <div className="bg-green-50 border-l-4 border-green-500 p-4 rounded">
        <h4 className="font-semibold text-green-900 mb-2">✅ Benefícios</h4>
        <ul className="text-gray-700 space-y-1 text-sm">
          <li>✓ Reduz código repetitivo</li>
          <li>✓ Menos erros de digitação</li>
          <li>✓ Código mais legível</li>
          <li>✓ Manutenção mais fácil</li>
        </ul>
      </div>
    </TopicCard>
  );
}

