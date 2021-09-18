package triple.wonhee.mileageservice.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import triple.wonhee.mileageservice.domain.ReviewPointHistory;

@Repository
public interface ReviewPointRepository extends JpaRepository<ReviewPointHistory, Long> {

    List<ReviewPointHistory> findAllByPlaceIdAndIsFirstReviewOrderByIdDesc(String placeId, boolean firstReview);

    List<ReviewPointHistory> findAllByReviewIdOrderByIdDesc(String reviewId);

}
