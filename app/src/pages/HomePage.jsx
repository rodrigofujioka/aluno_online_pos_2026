import { Link } from 'react-router-dom';
import { topicPages, topics } from '../data/projectTopics';

const recommendedTrail = ['springBoot', 'dto', 'springData', 'pagination', 'feign', 'customBeanValidators', 'logs'];

export default function HomePage({ isDark }) {
  return (
    <main className="mx-auto w-full max-w-7xl px-4 py-6 md:px-8 md:py-7">
      <section className={`mb-5 rounded-3xl border p-4 shadow-sm md:p-5 ${
        isDark ? 'border-slate-700 bg-slate-900' : 'border-slate-200 bg-white'
      }`}>
        <p className="mb-1 text-xs font-semibold uppercase tracking-[0.22em] text-indigo-600">
          Trilha de estudo
        </p>
        <h2 className={`text-2xl font-bold md:text-3xl ${isDark ? 'text-slate-100' : 'text-slate-900'}`}>
          Escolha um assunto e estude por passos
        </h2>
        <p className={`mt-2 text-sm leading-6 md:text-base ${isDark ? 'text-slate-300' : 'text-slate-600'}`}>
          Cada página foi simplificada para os alunos: conceito rápido, passos numerados, arquivos do projeto e exemplos reais.
        </p>

        <div className="mt-3 flex flex-wrap gap-2">
          {topicPages.map((item) => (
            <Link
              key={`tag-${item.key}`}
              to={`/assuntos/${item.slug}`}
              className={`rounded-full border px-3 py-1 text-xs font-semibold transition ${
                isDark
                  ? 'border-slate-600 bg-slate-800 text-slate-200 hover:border-blue-500 hover:bg-slate-700 hover:text-blue-300'
                  : 'border-slate-300 bg-slate-50 text-slate-700 hover:border-blue-500 hover:bg-blue-50 hover:text-blue-700'
              }`}
            >
              {item.menuLabel}
            </Link>
          ))}
        </div>
      </section>

      <section className={`mb-5 rounded-3xl border p-4 shadow-sm md:p-5 ${
        isDark ? 'border-emerald-800 bg-slate-900' : 'border-emerald-200 bg-emerald-50'
      }`}>
        <p className={`mb-2 text-xs font-semibold uppercase tracking-[0.22em] ${isDark ? 'text-emerald-300' : 'text-emerald-700'}`}>
          Trilha recomendada para começar
        </p>
        <div className="flex flex-wrap gap-2">
          {recommendedTrail.map((key, index) => {
            const page = topicPages.find((topicPage) => topicPage.key === key);
            if (!page) return null;

            return (
              <Link
                key={`recommended-${key}`}
                to={`/assuntos/${page.slug}`}
                className={`rounded-full px-3 py-1 text-xs font-semibold transition ${
                  isDark
                    ? 'bg-slate-800 text-emerald-200 hover:bg-slate-700'
                    : 'bg-white text-emerald-700 shadow-sm hover:bg-emerald-100'
                }`}
              >
                {index + 1}. {topics[key].title}
              </Link>
            );
          })}
        </div>
      </section>

      <section className="grid gap-3 md:grid-cols-2 xl:grid-cols-3">
        {topicPages.map((item) => {
          const topic = topics[item.key];
          return (
            <article
              key={item.key}
              className={`rounded-2xl border p-4 shadow-sm ${
                isDark ? 'border-slate-700 bg-slate-900' : 'border-slate-200 bg-white'
              }`}
            >
              <p className={`text-[11px] font-semibold uppercase tracking-[0.2em] ${isDark ? 'text-slate-400' : 'text-slate-500'}`}>
                Assunto
              </p>
              <h3 className={`mt-1 text-lg font-bold md:text-xl ${isDark ? 'text-slate-100' : 'text-slate-900'}`}>
                {topic.emoji} {item.menuLabel}
              </h3>
              <p className={`mt-1 min-h-12 text-sm leading-6 ${isDark ? 'text-slate-300' : 'text-slate-600'}`}>
                {topic.description}
              </p>
              <Link
                to={`/assuntos/${item.slug}`}
                className="mt-3 inline-flex rounded-full bg-blue-600 px-4 py-2 text-sm font-semibold text-white transition hover:bg-blue-700"
              >
                Visualizar
              </Link>
            </article>
          );
        })}
      </section>
    </main>
  );
}
