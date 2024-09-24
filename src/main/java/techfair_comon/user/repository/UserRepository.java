package techfair_comon.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import techfair_comon.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
