package triple.wonhee.mileageservice.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review {

    @Id
    String reviewId;

    String reviewContent;

    boolean isFirstReview;

    int reviewPoint;

    @ManyToOne(fetch = FetchType.LAZY)
    User user;

    @ManyToOne(fetch = FetchType.LAZY)
    Place place;

    @Builder
    public Review(String reviewId, String reviewContent, boolean isFirstReview, int reviewPoint, User user, Place place) {
        this.reviewId = reviewId;
        this.reviewContent = reviewContent;
        this.isFirstReview = isFirstReview;
        this.reviewPoint = reviewPoint;
        this.user = user;
        this.place = place;
    }
}
