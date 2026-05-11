import { useState } from 'react';

export default function CodeBlock({ language, title, code }) {
  const [copied, setCopied] = useState(false);

  const handleCopy = () => {
    navigator.clipboard.writeText(code);
    setCopied(true);
    setTimeout(() => setCopied(false), 2000);
  };

  return (
    <div className="bg-gray-100 rounded-lg overflow-hidden mb-4">
      <div className="bg-gray-800 text-white px-4 py-2 flex justify-between items-center">
        <span className="font-mono text-sm">
          {language ? `📄 ${language}` : 'Código'} {title && `- ${title}`}
        </span>
        <button
          onClick={handleCopy}
          className="text-xs bg-blue-600 hover:bg-blue-700 px-3 py-1 rounded transition-colors"
        >
          {copied ? '✓ Copiado' : 'Copiar'}
        </button>
      </div>
      <pre className="bg-gray-900 text-green-400 p-4 overflow-x-auto text-sm font-mono">
        <code>{code}</code>
      </pre>
    </div>
  );
}

