package fun.aaronfang.qsbk.demo.repo;

import fun.aaronfang.qsbk.demo.model.PostClassEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PostClassRepo extends CrudRepository<PostClassEntity, Integer> {

    List<PostClassEntity> findPostClassEntitiesByStatus(byte status);

}
