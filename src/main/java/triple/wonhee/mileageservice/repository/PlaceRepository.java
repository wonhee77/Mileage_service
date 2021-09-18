package triple.wonhee.mileageservice.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import triple.wonhee.mileageservice.domain.Place;

public interface PlaceRepository extends JpaRepository<Place, Long> {

    Optional<Place> findByPlaceId(String placeId);

}
