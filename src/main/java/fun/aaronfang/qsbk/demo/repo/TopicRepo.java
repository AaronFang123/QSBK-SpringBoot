package fun.aaronfang.qsbk.demo.repo;

import fun.aaronfang.qsbk.demo.model.TopicEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface TopicRepo extends CrudRepository<TopicEntity, Integer> {
    List<TopicEntity> findTopicEntityByTopicClassId(int topicClassId, Pageable pageable);

    List<TopicEntity> findTopicEntityByType(int type, Pageable pageable);


    @Query(value = "SELECT *," +
            "(SELECT COUNT(*) AS tp_count " +
            "FROM `post` INNER JOIN `topic_post` `pivot` ON `pivot`.`post_id`=`post`.`id` " +
            "WHERE  ( `pivot`.`topic_id` =topic.id )) AS `post_count`," +
            "(SELECT COUNT(*) AS tp_count FROM `post` " +
            "INNER JOIN `topic_post` `pivot` ON `pivot`.`post_id`=`post`.`id` " +
            "WHERE  `post`.`create_time` BETWEEN :start AND :end  " +
            "AND ( `pivot`.`topic_id` =topic.id )) AS `todaypost_count` " +
            "FROM `topic` WHERE  `type` = 1 LIMIT 10", nativeQuery = true)
    List<Map<String, Object>> getHottestTopic(@Param("start") int start, @Param("end") int end);

}
