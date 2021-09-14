package triple.wonhee.mileageservice.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PHOTO")
public class Photo {

    @Id
    String photoId;
}
