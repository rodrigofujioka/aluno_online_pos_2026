import { useEffect, useState } from 'react';
import Header from './components/Header';
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import HomePage from './pages/HomePage';
import TopicPage from './pages/TopicPage';

function getInitialTheme() {
  const savedTheme = localStorage.getItem('aluno_online_theme');
  if (savedTheme === 'dark' || savedTheme === 'light') {
    return savedTheme;
  }

  return window.matchMedia('(prefers-color-scheme: dark)').matches ? 'dark' : 'light';
}

function App() {
  const [theme, setTheme] = useState(() => getInitialTheme());
  const isDark = theme === 'dark';

  useEffect(() => {
    document.documentElement.classList.toggle('dark', isDark);
    localStorage.setItem('aluno_online_theme', theme);
  }, [theme, isDark]);

  const toggleTheme = () => {
    setTheme((currentTheme) => (currentTheme === 'dark' ? 'light' : 'dark'));
  };

  return (
    <BrowserRouter>
      <div className={`min-h-screen ${isDark ? 'bg-slate-950 text-slate-100' : 'bg-slate-100 text-slate-900'}`}>
        <Header isDark={isDark} onToggleTheme={toggleTheme} />
        <Routes>
          <Route path="/" element={<HomePage isDark={isDark} />} />
          <Route path="/assuntos/:slug" element={<TopicPage isDark={isDark} />} />
          <Route path="*" element={<HomePage isDark={isDark} />} />
        </Routes>
      </div>
    </BrowserRouter>
  );
}

export default App;
