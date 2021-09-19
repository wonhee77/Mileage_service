package triple.wonhee.mileageservice.service;

import static triple.wonhee.mileageservice.constant.ReviewPointConstant.CONTENT_POINT;
import static triple.wonhee.mileageservice.constant.ReviewPointConstant.FIRST_REVIEW_POINT;
import static triple.wonhee.mileageservice.constant.ReviewPointConstant.PHOTO_POINT;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import triple.wonhee.mileageservice.domain.ActionType;
import triple.wonhee.mileageservice.domain.Review;
import triple.wonhee.mileageservice.domain.ReviewPointHistory;
import triple.wonhee.mileageservice.domain.User;
import triple.wonhee.mileageservice.dto.EventRequestDto;
import triple.wonhee.mileageservice.repository.ReviewPointRepository;
import triple.wonhee.mileageservice.repository.ReviewRepository;
import triple.wonhee.mileageservice.repository.UserRepository;
import triple.wonhee.mileageservice.util.exception.JpaNullPointException;

@Service
@RequiredArgsConstructor
public class ReviewPointHistoryService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final ReviewPointRepository reviewPointRepository;

    @Transactional
    public ReviewPointHistory saveReviewPointHistory(EventRequestDto eventRequestDto) {
        ActionType action = eventRequestDto.getAction();
        String content = eventRequestDto.getContent();
        int attachedPhotoIdsSize = eventRequestDto.getAttachedPhotoIds().size();
        String placeId = eventRequestDto.getPlaceId();
        String userId = eventRequestDto.getUserId();
        String reviewId = eventRequestDto.getReviewId();

        User findUser = userRepository.findByUserId(userId).orElseThrow(
            () -> new JpaNullPointException("해당 id의 유저가 없습니다.")
        );

        if (action == ActionType.ADD) { //리뷰 작성

            List<Review> findReviewList = reviewRepository.findAllByPlaceId(placeId);
            //Review가 DB에 이미 저장되었다는 가정하에 해당 장소에 대한 findReviewList.size()가 1인 경우를 첫 리뷰로 판단
            boolean isFirstReview = findReviewList.size() == 1;
            int reviewPoint = calculatePointWith(content, attachedPhotoIdsSize, isFirstReview);
            findUser.plusPoint(reviewPoint);
            return saveReviewPointHistory(eventRequestDto, isFirstReview, reviewPoint, reviewPoint);

        } else if (action == ActionType.MOD) { //리뷰 수정

            //ReviewPointHistory 목록에서 Id를 역순으로 가져옴으로써 reviewId에 대한 가장 최근 이력 조회
            List<ReviewPointHistory> findReviewPointHistories = reviewPointRepository
                .findAllByReviewIdOrderByIdDesc(reviewId);
            ReviewPointHistory latestReviewPointHistory = findReviewPointHistories.get(0);

            boolean isFirstReview = latestReviewPointHistory.isFirstReview();
            int oldReviewPoint = latestReviewPointHistory.getReviewPoint();
            int newReviewPoint = calculatePointWith(content, attachedPhotoIdsSize, isFirstReview);
            int changedPoint = newReviewPoint - oldReviewPoint;
            findUser.plusPoint(changedPoint);
            return saveReviewPointHistory(eventRequestDto, isFirstReview, changedPoint, newReviewPoint);

        } else if (action == ActionType.DELETE) { // 리뷰 삭제

            //ReviewPointHistory 목록에서 Id를 역순으로 가져옴으로써 reviewId에 대한 가장 최근 이력 조회
            List<ReviewPointHistory> findReviewPointHistories = reviewPointRepository
                .findAllByReviewIdOrderByIdDesc(reviewId);
            ReviewPointHistory latestReviewPointHistory = findReviewPointHistories.get(0);

            findUser.plusPoint(-latestReviewPointHistory.getReviewPoint());
            return saveReviewPointHistory(eventRequestDto, latestReviewPointHistory.isFirstReview(),
                -latestReviewPointHistory.getReviewPoint(), 0);
        } else {
            throw new IllegalArgumentException("액션 정보를 찾을 수 없습니다.");
        }
    }

    private ReviewPointHistory saveReviewPointHistory(EventRequestDto eventRequestDto, boolean isFirstReview,
        int changedPoint, int reviewPoint) {
        ActionType action = eventRequestDto.getAction();
        String content = eventRequestDto.getContent();
        String reviewId = eventRequestDto.getReviewId();
        int attachedPhotoIdsSize = eventRequestDto.getAttachedPhotoIds().size();
        String placeId = eventRequestDto.getPlaceId();
        String userId = eventRequestDto.getUserId();

        ReviewPointHistory reviewPointHistory = ReviewPointHistory.builder()
            .action(action)
            .reviewId(reviewId)
            .attachedPhotoCount(attachedPhotoIdsSize)
            .userId(userId)
            .placeId(placeId)
            .content(content)
            .isFirstReview(isFirstReview)
            .changedPoint(changedPoint)
            .reviewPoint(reviewPoint)
            .build();

        reviewPointRepository.save(reviewPointHistory);

        return reviewPointHistory;
    }

    private int calculatePointWith(String content, int attachedPhotoCount, boolean isFirstReview) {
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


