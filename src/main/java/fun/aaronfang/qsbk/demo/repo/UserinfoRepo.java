package fun.aaronfang.qsbk.demo.repo;

import fun.aaronfang.qsbk.demo.model.UserEntity;
import fun.aaronfang.qsbk.demo.model.UserinfoEntity;
import org.springframework.data.repository.CrudRepository;

public interface UserinfoRepo extends CrudRepository<UserinfoEntity, Integer> {

}
