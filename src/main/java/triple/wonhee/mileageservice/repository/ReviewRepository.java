package triple.wonhee.mileageservice.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import triple.wonhee.mileageservice.domain.Place;
import triple.wonhee.mileageservice.domain.Review;
import triple.wonhee.mileageservice.domain.User;

public interface ReviewRepository extends JpaRepository<Review, String> {

    @Query("select rv from Review as rv where rv.place.placeId = :placeId")
    List<Review> findAllByPlaceId(@Param("placeId") String placeId);

}
