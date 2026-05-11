import TopicCard from './TopicCard';
import CodeBlock from './CodeBlock';
import { buildRepoFileUrl, buildRepoTreeUrl, hasRepoLinks } from '../utils/sourceLinks';

function PathTree({ path }) {
  const segments = path.replace(/\\/g, '/').split('/').filter(Boolean);

  return (
    <div className="rounded-2xl bg-slate-950 p-4 text-sm text-slate-100 shadow-inner">
      <p className="mb-3 text-xs font-semibold uppercase tracking-[0.22em] text-slate-400">Prévia da árvore</p>
      <div className="space-y-1 font-mono">
        {segments.map((segment, index) => {
          const indent = index * 16;
          const prefix = index === 0 ? '📁 ' : '└─ ';

          return (
            <div key={`${path}-${segment}-${index}`} style={{ paddingLeft: `${indent}px` }}>
              <span className="text-slate-300">{prefix}</span>
              <span className={index === segments.length - 1 ? 'text-emerald-300' : 'text-slate-100'}>
                {segment}
              </span>
            </div>
          );
        })}
      </div>
    </div>
  );
}

function FileLinks({ path, isDark }) {
  const fileUrl = buildRepoFileUrl(path);
  const treeUrl = buildRepoTreeUrl(path);

  return (
    <div className="flex flex-wrap gap-3">
      {fileUrl ? (
        <a
          href={fileUrl}
          target="_blank"
          rel="noreferrer"
          className="rounded-full bg-blue-600 px-4 py-2 text-xs font-semibold text-white transition hover:bg-blue-700"
        >
          Abrir arquivo
        </a>
      ) : null}
      {treeUrl ? (
        <a
          href={treeUrl}
          target="_blank"
          rel="noreferrer"
          className={`rounded-full border px-4 py-2 text-xs font-semibold transition ${
            isDark
              ? 'border-slate-600 bg-slate-800 text-slate-200 hover:border-slate-500 hover:bg-slate-700'
              : 'border-slate-300 bg-white text-slate-700 hover:border-slate-400 hover:bg-slate-50'
          }`}
        >
          Ver pasta
        </a>
      ) : null}
    </div>
  );
}

export default function TopicTemplate({ topic, isDark }) {
  return (
    <TopicCard emoji={topic.emoji} title={topic.title} description={topic.description} isDark={isDark}>
      <div className="space-y-6">
        <section className={`rounded-2xl border p-4 ${
          isDark ? 'border-slate-700 bg-slate-900' : 'border-slate-200 bg-slate-50'
        }`}>
          <h3 className={`mb-2 text-lg font-semibold ${isDark ? 'text-slate-100' : 'text-slate-800'}`}>
            Resumo simplificado
          </h3>
          <p className={`text-sm leading-6 ${isDark ? 'text-slate-300' : 'text-slate-700'}`}>{topic.quickSummary}</p>
          <ul className={`mt-3 space-y-1 text-sm leading-6 ${isDark ? 'text-slate-300' : 'text-slate-700'}`}>
            {topic.lessonHighlights.map((item) => (
              <li key={item}>• {item}</li>
            ))}
          </ul>
        </section>

        {topic.sourceDocs.length > 0 ? (
          <section className={`rounded-2xl border p-4 ${
            isDark ? 'border-indigo-800 bg-slate-900' : 'border-indigo-100 bg-indigo-50'
          }`}>
            <h3 className={`mb-2 text-lg font-semibold ${isDark ? 'text-indigo-200' : 'text-indigo-900'}`}>
              Material de apoio da aula
            </h3>
            <div className="flex flex-wrap gap-2">
              {topic.sourceDocs.map((doc) => (
                <a
                  key={doc}
                  href={buildRepoFileUrl(doc)}
                  target="_blank"
                  rel="noreferrer"
                  className={`rounded-full px-3 py-1 text-xs font-semibold shadow-sm transition ${
                    isDark
                      ? 'bg-slate-800 text-indigo-200 hover:bg-slate-700'
                      : 'bg-white text-indigo-700 hover:bg-indigo-100'
                  }`}
                >
                  {doc}
                </a>
              ))}
            </div>
          </section>
        ) : null}

        <section className={`rounded-2xl border p-4 ${
          isDark ? 'border-slate-700 bg-slate-900' : 'border-slate-200 bg-slate-50'
        }`}>
          <h3 className={`mb-3 text-lg font-semibold ${isDark ? 'text-slate-100' : 'text-slate-800'}`}>
            Passo a passo para estudar
          </h3>
          <ol className={`space-y-2 text-sm leading-6 ${isDark ? 'text-slate-300' : 'text-slate-700'}`}>
            {topic.studySteps.map((step) => (
              <li key={step}>{step}</li>
            ))}
          </ol>
        </section>

        <section className={`rounded-2xl border p-4 ${
          isDark ? 'border-blue-900 bg-slate-900' : 'border-blue-100 bg-blue-50'
        }`}>
          <h3 className={`mb-3 text-lg font-semibold ${isDark ? 'text-blue-200' : 'text-blue-900'}`}>
            Onde encontrar no projeto
          </h3>
          <div className="space-y-4">
            {topic.filePaths.map((path) => (
              <div key={path} className={`rounded-2xl p-4 shadow-sm ${isDark ? 'bg-slate-800' : 'bg-white'}`}>
                <div className="mb-3 flex flex-wrap items-center justify-between gap-3">
                  <div>
                    <p className={`text-xs font-semibold uppercase tracking-[0.2em] ${isDark ? 'text-slate-400' : 'text-slate-500'}`}>
                      Arquivo
                    </p>
                    <code className={`mt-1 block bg-transparent p-0 text-sm ${isDark ? 'text-slate-100' : 'text-slate-900'}`}>
                      {path}
                    </code>
                  </div>
                  {hasRepoLinks() ? <FileLinks path={path} isDark={isDark} /> : null}
                </div>
                <PathTree path={path} />
              </div>
            ))}
          </div>
        </section>

        <section>
          <h3 className={`mb-3 text-lg font-semibold ${isDark ? 'text-slate-100' : 'text-slate-800'}`}>
            Exemplos reais do projeto
          </h3>
          <div className="space-y-4">
            {topic.examples.map((example) => (
              <CodeBlock
                key={`${topic.title}-${example.title}`}
                language={example.language}
                title={example.title}
                code={example.code}
              />
            ))}
          </div>
        </section>
      </div>
    </TopicCard>
  );
}
