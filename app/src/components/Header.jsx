export default function Header({ isDark, onToggleTheme }) {
  return (
    <header className="border-b border-white/20 bg-gradient-to-r from-slate-900 via-blue-900 to-slate-900 text-white shadow-xl">
      <div className="mx-auto flex max-w-7xl flex-col gap-4 px-6 py-9 md:px-10 md:py-10">
        <div className="flex items-start gap-6">
          <img
            src="/logo-javaspion.png"
            alt="JavaSpion Logo"
            className="h-24 w-auto md:h-32"
          />
          <div className="max-w-4xl space-y-3">
            <p className="inline-flex rounded-full bg-white/10 px-4 py-2 text-xs font-semibold uppercase tracking-[0.22em] text-blue-100">
              Projeto Spring Boot - Pós graduação
            </p>
          <h1 className="text-3xl font-extrabold leading-tight md:text-5xl">
            Aluno Online — páginas separadas por assunto
          </h1>
          <p className="max-w-3xl text-base leading-7 text-blue-100/90 md:text-lg">
            Conteúdo simplificado e enumerado para estudo , com exemplos reais vistos em sala de aula e links para o código do projeto.
          </p>
          </div>
        </div>

        <div className="flex flex-wrap items-center gap-3 text-sm">
          <span className="rounded-full bg-white/10 px-4 py-2 font-semibold text-white">Spring Boot 4.0.6</span>
          <span className="rounded-full bg-white/10 px-4 py-2 font-semibold text-white">Java 21</span>
          <button
            type="button"
            onClick={onToggleTheme}
            className="rounded-full bg-white px-4 py-2 font-semibold text-slate-900 transition hover:bg-slate-200"
          >
            {isDark ? '☀️ Tema claro' : '🌙 Tema dark'}
          </button>
          <a
            href="https://github.com/rodrigofujioka/aluno_online_pos_2026"
            target="_blank"
            rel="noreferrer"
            className={`rounded-full px-4 py-2 font-semibold transition ${
              isDark ? 'bg-slate-800 text-white hover:bg-slate-700' : 'bg-white text-slate-900 hover:bg-slate-200'
            }`}
          >
            Abrir GitHub do projeto
          </a>
        </div>
      </div>
    </header>
  );
}
