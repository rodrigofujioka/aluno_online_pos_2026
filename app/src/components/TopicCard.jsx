export default function TopicCard({ emoji, title, description, children }) {
  return (
    <div className="bg-white rounded-lg shadow-md hover:shadow-xl transition-shadow border-l-4 border-blue-500 p-6 mb-6">
      <h2 className="text-3xl mb-2">
        <span className="text-4xl mr-2">{emoji}</span> {title}
      </h2>
      <p className="text-gray-600 mb-4">{description}</p>
      {children}
    </div>
  );
}

