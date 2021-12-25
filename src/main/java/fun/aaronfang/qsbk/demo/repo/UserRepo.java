package fun.aaronfang.qsbk.demo.repo;

import fun.aaronfang.qsbk.demo.model.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface UserRepo extends CrudRepository<UserEntity, Integer> {
}
