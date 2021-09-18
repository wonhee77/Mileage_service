package triple.wonhee.mileageservice.dto;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import triple.wonhee.mileageservice.domain.ActionType;
import triple.wonhee.mileageservice.domain.EventType;

@Getter
public class
EventRequestDto {

    EventType type;
    ActionType action;
    String reviewId;
    String content;
    List attachedPhotoIds = new ArrayList<>();
    String userId;
    String placeId;
}
