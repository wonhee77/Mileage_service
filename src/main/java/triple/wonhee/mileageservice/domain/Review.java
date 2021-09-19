package triple.wonhee.mileageservice.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(indexes = @Index(name = "i_review", columnList = "place_place_id"))
public class Review {

    @Id @Column(name = "id")
    String reviewId;

    String reviewContent;

    @ManyToOne(fetch = FetchType.LAZY)
    User user;

    @ManyToOne(fetch = FetchType.LAZY)
    Place place;

    @Builder
    public Review(String reviewId, String reviewContent, User user, Place place) {
        this.reviewId = reviewId;
        this.reviewContent = reviewContent;
        this.user = user;
        this.place = place;
    }
}
