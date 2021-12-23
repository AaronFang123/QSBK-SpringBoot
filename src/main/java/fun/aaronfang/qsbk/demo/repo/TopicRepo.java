package fun.aaronfang.qsbk.demo.repo;

import fun.aaronfang.qsbk.demo.model.TopicEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TopicRepo extends CrudRepository<TopicEntity, Integer> {
    List<TopicEntity> findTopicEntityByTopicClassId(int topicClassId, Pageable pageable);
}
