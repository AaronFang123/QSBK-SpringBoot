package fun.aaronfang.qsbk.demo.repo;

import fun.aaronfang.qsbk.demo.model.TopicClassEntity;
import fun.aaronfang.qsbk.demo.model.TopicEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TopicClassRepo extends CrudRepository<TopicClassEntity, Integer> {

    @Query(value = "select * from topic_class tc where tc.status = 1", nativeQuery = true)
    List<TopicClassEntity> getAvailableTopicClass();
}
