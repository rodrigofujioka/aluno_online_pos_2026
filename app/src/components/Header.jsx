export default function Header() {
  return (
    <header className="bg-gradient-to-r from-blue-600 to-blue-800 text-white py-12 shadow-lg">
      <div className="max-w-6xl mx-auto px-6">
        <h1 className="text-5xl font-bold mb-4">🎓 Aluno Online - Documentação</h1>
        <p className="text-xl opacity-90">
          Guia completo de tecnologias e padrões utilizados no projeto Spring Boot educacional
        </p>
        <div className="mt-4 flex gap-3 text-sm">
          <span className="bg-white/20 px-3 py-1 rounded-full">Spring Boot 4.0.6</span>
          <span className="bg-white/20 px-3 py-1 rounded-full">Java 21</span>
          <span className="bg-white/20 px-3 py-1 rounded-full">React + Vite</span>
        </div>
      </div>
    </header>
  );
}

