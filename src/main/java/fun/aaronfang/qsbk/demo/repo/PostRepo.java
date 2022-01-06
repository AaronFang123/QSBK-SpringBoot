package fun.aaronfang.qsbk.demo.repo;

import fun.aaronfang.qsbk.demo.model.PostEntity;
import org.springframework.data.repository.CrudRepository;

public interface PostRepo extends CrudRepository<PostEntity, Integer> {

}
