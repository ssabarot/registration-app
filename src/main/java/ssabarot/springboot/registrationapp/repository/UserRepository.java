package ssabarot.springboot.registrationapp.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ssabarot.springboot.registrationapp.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
}
