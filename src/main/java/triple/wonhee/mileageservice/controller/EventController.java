package triple.wonhee.mileageservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import triple.wonhee.mileageservice.domain.EventType;
import triple.wonhee.mileageservice.dto.EventRequestDto;
import triple.wonhee.mileageservice.service.ReviewService;

@RestController
@RequiredArgsConstructor
public class EventController {

    private final ReviewService reviewService;

    @PostMapping("/events")
    public void getEvent(@RequestBody EventRequestDto eventRequestDto) {
        if (eventRequestDto.getType() == EventType.REVIEW) {
            reviewService.saveReviewPoint(eventRequestDto);
        }
    }
}
