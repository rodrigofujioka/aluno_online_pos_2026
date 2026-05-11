import { useState } from 'react';

export default function CodeBlock({ language, title, code }) {
  const [copied, setCopied] = useState(false);

  const handleCopy = async () => {
    try {
      await navigator.clipboard.writeText(code);
      setCopied(true);
      setTimeout(() => setCopied(false), 1800);
    } catch {
      setCopied(false);
    }
  };

  return (
    <article className="overflow-hidden rounded-2xl border border-slate-200 bg-slate-950 shadow-lg">
      <div className="flex flex-wrap items-center justify-between gap-3 border-b border-slate-800 bg-slate-900 px-4 py-3">
        <div>
          <p className="text-xs font-semibold uppercase tracking-[0.2em] text-slate-300">
            {language ? `Linguagem: ${language}` : 'Código'}
          </p>
          {title && <p className="mt-1 text-sm font-semibold text-white">{title}</p>}
        </div>
        <button
          type="button"
          onClick={handleCopy}
          className="rounded-full bg-blue-600 px-4 py-2 text-xs font-semibold text-white transition hover:bg-blue-700"
        >
          {copied ? '✓ Copiado' : 'Copiar'}
        </button>
      </div>
      <pre className="overflow-x-auto p-4 text-sm leading-6 text-emerald-300">
        <code className="bg-transparent p-0 text-inherit">{code}</code>
      </pre>
    </article>
  );
}
