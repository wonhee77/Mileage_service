package triple.wonhee.mileageservice.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import triple.wonhee.mileageservice.domain.Place;
import triple.wonhee.mileageservice.domain.Review;
import triple.wonhee.mileageservice.domain.User;

public interface ReviewRepository extends JpaRepository<Review, String> {

    List<Review> findAllByPlace(Place place);

    Optional<Review> findByUserAndPlace(User user, Place place);

}
