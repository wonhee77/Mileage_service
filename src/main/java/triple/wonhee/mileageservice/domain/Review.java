package triple.wonhee.mileageservice.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
//@Table(indexes = @Index(name = "i_place", columnList = "place"))
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review {

    @Id
    String reviewId;

    String reviewContent;

    boolean isFirstReview;

    @ManyToOne(fetch = FetchType.LAZY)
    User user;

    @ManyToOne(fetch = FetchType.LAZY)
    Place place;

    @Builder
    public Review(String reviewContent, boolean isFirstReview, User user, Place place) {
        this.reviewContent = reviewContent;
        this.isFirstReview = isFirstReview;
        this.user = user;
        this.place = place;
    }
}
