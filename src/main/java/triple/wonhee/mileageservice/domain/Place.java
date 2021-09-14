package triple.wonhee.mileageservice.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PLACE")
public class Place {

    @Id
    String placeId;
}
