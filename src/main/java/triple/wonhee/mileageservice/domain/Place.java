package triple.wonhee.mileageservice.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Place {

    @Id @Column(name = "id")
    String placeId;

    String placeName;

    @Builder
    public Place(String placeId, String placeName) {
        this.placeId = placeId;
        this.placeName = placeName;
    }
}
