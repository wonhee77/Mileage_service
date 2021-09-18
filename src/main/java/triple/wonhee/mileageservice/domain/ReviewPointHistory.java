package triple.wonhee.mileageservice.domain;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(indexes = @Index(name = "i_review_point_history", columnList = "reviewId"))
public class ReviewPointHistory {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Enumerated(EnumType.STRING)
    ActionType action;
    String reviewId;
    int attachedPhotoCount;
    String userId;
    String placeId;
    String reviewContent;
    boolean isFirstReview;
    int changedPoint;
    int reviewPoint;

    @Builder
    public ReviewPointHistory(ActionType action, String reviewId, int attachedPhotoCount, String userId,
        String placeId, String content, boolean isFirstReview, int changedPoint, int reviewPoint) {
        this.action = action;
        this.reviewId = reviewId;
        this.attachedPhotoCount = attachedPhotoCount;
        this.userId = userId;
        this.placeId = placeId;
        this.reviewContent = content;
        this.isFirstReview = isFirstReview;
        this.changedPoint = changedPoint;
        this.reviewPoint = reviewPoint;
    }
}
