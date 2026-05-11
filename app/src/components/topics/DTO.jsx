import TopicCard from '../TopicCard';
import CodeBlock from '../CodeBlock';

export default function DTO() {
  return (
    <TopicCard
      emoji="📦"
      title="DTO (Data Transfer Object)"
      description="Padrão para transferência de dados entre camadas da aplicação"
    >
      <p className="text-gray-700 mb-4">
        DTOs são objetos simples usados para transferir dados entre o controller e o service,
        separando a camada de apresentação da camada de negócio.
      </p>

      <h3 className="text-xl font-semibold mb-3 text-gray-800">📊 Tipos de DTO</h3>

      <CodeBlock
        language="java"
        title="ProfessorRequestDTO"
        code={`@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfessorRequestDTO {

    @NotBlank(message = "Nome completo é obrigatório")
    private String nomeCompleto;

    @Email(message = "E-mail inválido")
    private String email;

    @NotBlank(message = "CEP é obrigatório")
    private String cep;

    private String cpf;
}`}
      />

      <CodeBlock
        language="java"
        title="ProfessorResponseDTO"
        code={`@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfessorResponseDTO {

    private Long id;
    private String nomeCompleto;
    private String email;
    private String endereco;
}`}
      />

      <div className="bg-blue-50 border-l-4 border-blue-500 p-4 rounded mb-4">
        <h4 className="font-semibold text-blue-900 mb-2">🎯 Por que usar DTOs?</h4>
        <ul className="text-gray-700 space-y-2 text-sm">
          <li>✓ Separa camada de apresentação da camada de negócio</li>
          <li>✓ Valida dados antes de chegar ao service</li>
          <li>✓ Expõe apenas campos necessários</li>
          <li>✓ Facilita versionamento de APIs</li>
          <li>✓ Melhora performance (menos dados transferidos)</li>
        </ul>
      </div>

      <h3 className="text-xl font-semibold mb-3 text-gray-800">🔄 Fluxo</h3>
      <div className="bg-gray-100 p-4 rounded text-center text-sm">
        <p className="font-monospace">
          Cliente → <strong>RequestDTO</strong> → Validação → Mapper → Entidade → Service
        </p>
        <p className="font-monospace mt-2">
          Entidade → Mapper → <strong>ResponseDTO</strong> → Cliente
        </p>
      </div>
    </TopicCard>
  );
}

