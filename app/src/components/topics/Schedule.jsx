import TopicTemplate from '../TopicTemplate';
import { topics } from '../../data/projectTopics';

export default function Schedule() {
  return <TopicTemplate topic={topics.schedule} />;
}
