import { Link, Navigate, useParams } from 'react-router-dom';
import TopicTemplate from '../components/TopicTemplate';
import { topicPages, topics } from '../data/projectTopics';

export default function TopicPage({ isDark }) {
  const { slug } = useParams();
  const page = topicPages.find((item) => item.slug === slug);

  if (!page) {
    return <Navigate to="/" replace />;
  }

  return (
    <main className="mx-auto w-full max-w-7xl px-4 py-10 md:px-8">
      <div className="mb-6 flex flex-wrap items-center gap-3">
        <Link
          to="/"
          className={`rounded-full border px-4 py-2 text-sm font-semibold transition ${
            isDark
              ? 'border-slate-600 bg-slate-900 text-slate-100 hover:bg-slate-800'
              : 'border-slate-300 bg-white text-slate-700 hover:bg-slate-50'
          }`}
        >
          ← Voltar para assuntos
        </Link>
        <span
          className={`rounded-full px-3 py-2 text-xs font-semibold uppercase tracking-[0.18em] ${
            isDark ? 'bg-indigo-900 text-indigo-200' : 'bg-indigo-100 text-indigo-700'
          }`}
        >
          {page.menuLabel}
        </span>
      </div>

      <TopicTemplate topic={topics[page.key]} isDark={isDark} />
    </main>
  );
}
