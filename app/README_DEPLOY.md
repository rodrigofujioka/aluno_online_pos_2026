# 🎓 Aluno Online - Landing Page

Documentação interativa das aulas de Spring Boot com páginas separadas por assunto.

## ✅ O que esta versão entrega

- Páginas separadas por assunto (`/assuntos/:slug`)
- Conteúdo simplificado em passos enumerados
- Exemplos reais do backend
- Caminho completo dos arquivos no projeto
- Botões com link direto para o GitHub do projeto

## 🔗 Repositório usado nos links

A landing page usa link fixo para o projeto:

`https://github.com/rodrigofujioka/aluno_online_pos_2026`

Nao é necessário configurar `.env` para abrir os arquivos.

## 🚀 Quickstart

```bash
cd app
npm install
npm run dev
```

Acesse: `http://localhost:5173`

## 📦 Build para produção

```bash
npm run build
npm run preview
```

## 🌐 Deploy

### Vercel
```bash
npm install -g vercel
vercel
```

### Netlify
```bash
npm run build
```

### GitHub Pages
```bash
npm run build
```

## 🧭 Rotas principais

- `/` → lista de assuntos
- `/assuntos/lombok`
- `/assuntos/dto`
- `/assuntos/spring-boot`
- `/assuntos/spring-data`
- `/assuntos/orm`
- `/assuntos/jpql`
- `/assuntos/schedule`
- `/assuntos/cache`
- `/assuntos/feign`
- `/assuntos/custom-bean-validation`
- `/assuntos/pageable`
- `/assuntos/logs`

## 📄 Estrutura

```text
app/
├── src/
│   ├── components/
│   │   ├── Header.jsx
│   │   ├── TopicCard.jsx
│   │   ├── TopicTemplate.jsx
│   │   └── CodeBlock.jsx
│   ├── pages/
│   │   ├── HomePage.jsx
│   │   └── TopicPage.jsx
│   ├── data/
│   │   └── projectTopics.js
│   ├── utils/
│   │   └── sourceLinks.js
│   └── App.jsx
└── package.json
```
