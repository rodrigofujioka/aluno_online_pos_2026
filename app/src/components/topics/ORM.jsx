import TopicCard from '../TopicCard';
import CodeBlock from '../CodeBlock';
export default function ORM() {
  return (
    <TopicCard emoji="🗄️" title="ORM (Object-Relational Mapping)" description="Mapeia objetos Java para tabelas SQL">
      <p className="text-gray-700 mb-4">ORM elimina a necessidade de escrever SQL manualmente, mapeando automaticamente classes Java para tabelas de banco de dados.</p>
    </TopicCard>
  );
}
