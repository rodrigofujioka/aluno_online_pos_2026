import { useState } from 'react';
import Header from './components/Header';
import Lombok from './components/topics/Lombok';
import DTO from './components/topics/DTO';
import SpringBoot from './components/topics/SpringBoot';
import SpringData from './components/topics/SpringData';
import ORM from './components/topics/ORM';
import JPQL from './components/topics/JPQL';
import Schedule from './components/topics/Schedule';
import SpringCache from './components/topics/SpringCache';
import Feign from './components/topics/Feign';
import CustomBeanValidators from './components/topics/CustomBeanValidators';
import Pagination from './components/topics/Pagination';
import Logs from './components/topics/Logs';

function App() {
  const [activeTab, setActiveTab] = useState(0);

  const topics = [
    { name: 'Lombok', component: Lombok },
    { name: 'DTO', component: DTO },
    { name: 'Spring Boot', component: SpringBoot },
    { name: 'Spring Data', component: SpringData },
    { name: 'ORM', component: ORM },
    { name: 'JPQL', component: JPQL },
    { name: 'Schedule', component: Schedule },
    { name: 'Cache', component: SpringCache },
    { name: 'Feign', component: Feign },
    { name: 'Validators', component: CustomBeanValidators },
    { name: 'Pagination', component: Pagination },
    { name: 'Logs', component: Logs },
  ];

  const ActiveComponent = topics[activeTab].component;

  return (
    <div className="min-h-screen bg-gradient-to-br from-gray-50 to-gray-100">
      <Header />

      <div className="max-w-6xl mx-auto px-6 py-12">
        <div className="flex flex-wrap gap-2 mb-8 pb-6 border-b-2 border-gray-300">
          {topics.map((topic, index) => (
            <button
              key={index}
              onClick={() => setActiveTab(index)}
              className={`px-4 py-2 rounded-lg font-semibold transition-all ${
                activeTab === index
                  ? 'bg-blue-600 text-white shadow-lg'
                  : 'bg-white text-gray-700 border border-gray-300 hover:bg-gray-100'
              }`}
            >
              {topic.name}
            </button>
          ))}
        </div>

        <ActiveComponent />

        <div className="mt-16 pt-8 border-t border-gray-300 text-center text-gray-600">
          <p className="mb-2">📚 Projeto Aluno Online - Educação</p>
          <p className="text-sm">React + Vite | Criado com ❤️ para fins educacionais</p>
        </div>
      </div>
    </div>
  );
}

export default App;
