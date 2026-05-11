import { buildRepoFileUrl, hasRepoLinks } from '../utils/sourceLinks';

export default function StudyRoadmap({ docs }) {
  return (
    <section className="mb-10 rounded-3xl border border-slate-200 bg-white p-6 shadow-sm md:p-8">
      <div className="mb-4">
        <p className="mb-2 text-sm font-semibold uppercase tracking-[0.22em] text-indigo-600">
          Trilhas e materiais de apoio
        </p>
        <h2 className="text-2xl font-bold text-slate-900">📚 Materiais de aula e leitura guiada</h2>
        <p className="mt-2 text-sm leading-6 text-slate-600">
          Estes arquivos mostram as aulas que foram ministradas e ajudam o aluno a revisar o conteúdo direto no projeto.
        </p>
      </div>

      <div className="grid gap-4 md:grid-cols-2 xl:grid-cols-3">
        {docs.map((doc) => {
          const fileUrl = buildRepoFileUrl(doc.file);

          return (
            <article key={doc.file} className="rounded-2xl border border-slate-200 bg-slate-50 p-4">
              <div className="mb-2 flex items-center justify-between gap-3">
                <h3 className="text-base font-semibold text-slate-900">{doc.title}</h3>
                <span className="rounded-full bg-indigo-100 px-2 py-1 text-[11px] font-bold uppercase tracking-wide text-indigo-700">
                  .md
                </span>
              </div>
              <p className="text-sm leading-6 text-slate-600">{doc.description}</p>
              <p className="mt-3 text-xs font-semibold uppercase tracking-wide text-slate-500">Arquivo</p>
              <code className="mt-1 block break-all rounded-xl bg-white p-3 text-xs text-slate-800 shadow-sm">
                {doc.file}
              </code>

              {hasRepoLinks() && fileUrl ? (
                <a
                  href={fileUrl}
                  target="_blank"
                  rel="noreferrer"
                  className="mt-4 inline-flex rounded-full bg-slate-900 px-4 py-2 text-xs font-semibold text-white transition hover:bg-slate-700"
                >
                  Abrir documentação
                </a>
              ) : null}
            </article>
          );
        })}
      </div>
    </section>
  );
}
