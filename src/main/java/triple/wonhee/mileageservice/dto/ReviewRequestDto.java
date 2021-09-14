package triple.wonhee.mileageservice.dto;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Getter
public class ReviewRequestDto {

    Enum type;
    Enum action;
    String reviewId;
    String content;
    List attachedPhotoIds = new ArrayList();
    String userId;
    String placeId;

}
