package triple.wonhee.mileageservice.dto;

import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import triple.wonhee.mileageservice.domain.ActionType;
import triple.wonhee.mileageservice.domain.EventType;

@Getter
public class EventRequestDto {

    EventType type;
    ActionType action;
    String reviewId;
    String content;
    List<String> attachedPhotoIds = new ArrayList<>();
    String userId;
    String placeId;

    @Builder
    public EventRequestDto(EventType type, ActionType action, String reviewId, String content,
        List<String> attachedPhotoIds, String userId, String placeId) {
        this.type = type;
        this.action = action;
        this.reviewId = reviewId;
        this.content = content;
        this.attachedPhotoIds = attachedPhotoIds;
        this.userId = userId;
        this.placeId = placeId;
    }
}
