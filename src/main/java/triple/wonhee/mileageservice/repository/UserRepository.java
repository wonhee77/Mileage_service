package triple.wonhee.mileageservice.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import triple.wonhee.mileageservice.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUserId(String userId);

}
