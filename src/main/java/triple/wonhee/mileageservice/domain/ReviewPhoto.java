package triple.wonhee.mileageservice.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "REVIEW_PHOTO")
public class ReviewPhoto {

    @Id
    String reviewPhotoId;
}
