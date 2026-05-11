export default function TopicCard({ emoji, title, description, children, isDark }) {
  return (
    <section className={`rounded-3xl border p-6 shadow-sm md:p-8 ${
      isDark ? 'border-slate-700 bg-slate-900' : 'border-slate-200 bg-white'
    }`}>
      <header className={`mb-6 border-b pb-4 ${isDark ? 'border-slate-700' : 'border-slate-200'}`}>
        <p className="mb-2 text-sm font-semibold uppercase tracking-[0.22em] text-blue-600">
          {emoji} Tópico da aula
        </p>
        <h2 className={`text-3xl font-bold ${isDark ? 'text-slate-100' : 'text-slate-900'}`}>{title}</h2>
        <p className={`mt-3 max-w-4xl text-base leading-7 ${isDark ? 'text-slate-300' : 'text-slate-600'}`}>
          {description}
        </p>
      </header>
      {children}
    </section>
  );
}
