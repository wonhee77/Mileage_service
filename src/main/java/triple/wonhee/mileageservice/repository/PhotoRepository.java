package triple.wonhee.mileageservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import triple.wonhee.mileageservice.domain.Photo;

public interface PhotoRepository extends JpaRepository<Photo, Long> {

}
