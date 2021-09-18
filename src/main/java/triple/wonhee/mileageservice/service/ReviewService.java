package triple.wonhee.mileageservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import triple.wonhee.mileageservice.domain.ActionType;
import triple.wonhee.mileageservice.domain.Place;
import triple.wonhee.mileageservice.domain.User;
import triple.wonhee.mileageservice.dto.EventRequestDto;
import triple.wonhee.mileageservice.repository.PlaceRepository;
import triple.wonhee.mileageservice.repository.ReviewPointRepository;
import triple.wonhee.mileageservice.repository.ReviewRepository;
import triple.wonhee.mileageservice.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final PlaceRepository placeRepository;
    private final ReviewPointRepository reviewPointRepository;

    final int CONTENT_POINT = 1;
    final int PHOTO_POINT = 1;
    final int FIRST_REVIEW_POINT = 1;

    @Transactional
    public void saveReviewPoint(EventRequestDto eventRequestDto) {
        ActionType action = eventRequestDto.getAction();
        String content = eventRequestDto.getContent();
        int attachedPhotoIdsSize = eventRequestDto.getAttachedPhotoIds().size();
        String placeId = eventRequestDto.getPlaceId();
        String userId = eventRequestDto.getUserId();

        User findUser = userRepository.findByUserId(userId).orElseThrow(
            () -> new NullPointerException("해당 id의 유저가 없습니다.")
        );

        Place findPlace = placeRepository.findByPlaceId(placeId).orElseThrow(
            () -> new NullPointerException("해당 id의 장소가 없습니다.")
        );

        if (action == ActionType.ADD) {
            //리뷰 작성
            boolean isFirstReview =
                (reviewRepository.findAllByPlace(findPlace).size() == 1) ? true : false;
            int reviewPoint = calculatePointWith(content, attachedPhotoIdsSize, isFirstReview);
            findUser.plusPoint(reviewPoint);

        } else if (action == ActionType.MOD) {
            //리뷰 수정
        } else if (action == ActionType.DELETE) {
            //리뷰 삭제
        } else {
            throw new IllegalArgumentException("액션 정보를 찾을 수 없습니다.");
        }

    }


    private int calculatePointWith(String content, int attachedPhotoCount,
        boolean isFirstReview) {
        int totalPoint = 0;

        if (content.length() >= 1) {
            totalPoint += CONTENT_POINT;
        }
        if (attachedPhotoCount >= 1) {
            totalPoint += PHOTO_POINT;
        }
        if (isFirstReview) {
            totalPoint += FIRST_REVIEW_POINT;
        }
        return totalPoint;
    }
}


