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

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final ReviewPointRepository reviewPointRepository;

    @Transactional
    public void saveReviewPoint(EventRequestDto eventRequestDto) {
        ActionType action = eventRequestDto.getAction();
        String content = eventRequestDto.getContent();
        int attachedPhotoIdsSize = eventRequestDto.getAttachedPhotoIds().size();
        String userId = eventRequestDto.getUserId();
        String reviewId = eventRequestDto.getReviewId();

        User findUser = userRepository.findByUserId(userId).orElseThrow(
            () -> new NullPointerException("해당 id의 유저가 없습니다.")
        );

        Review review = reviewRepository.findById(reviewId).orElseThrow(
            () -> new NullPointerException("User가 해당 장소에 작성한 리뷰가 없습니다.")
        );

        if (action == ActionType.ADD) {
            //리뷰 작성
            boolean isFirstReview = review.isFirstReview();
            int reviewPoint = calculatePointWith(content, attachedPhotoIdsSize, isFirstReview);
            findUser.plusPoint(reviewPoint);
            saveReviewPointHistory(eventRequestDto, isFirstReview, reviewPoint, reviewPoint);
        } else if (action == ActionType.MOD) {
            //리뷰 수정
            int reviewPoint = review.getReviewPoint();
            int modifiedPoint = calculatePointWith(content, attachedPhotoIdsSize,
                review.isFirstReview());
            int changedPoint = modifiedPoint - reviewPoint;
            findUser.plusPoint(changedPoint);
            saveReviewPointHistory(eventRequestDto, review.isFirstReview(), changedPoint,
                modifiedPoint);
        } else if (action == ActionType.DELETE) {
            //리뷰 삭제
            findUser.plusPoint(-review.getReviewPoint());
            saveReviewPointHistory(eventRequestDto, review.isFirstReview(),
                -review.getReviewPoint(), 0);
        } else {
            throw new IllegalArgumentException("액션 정보를 찾을 수 없습니다.");
        }

    }

    @Transactional
    public void reviewIndependentSaveReviewPoint(EventRequestDto eventRequestDto) {
        ActionType action = eventRequestDto.getAction();
        String content = eventRequestDto.getContent();
        int attachedPhotoIdsSize = eventRequestDto.getAttachedPhotoIds().size();
        String placeId = eventRequestDto.getPlaceId();
        String userId = eventRequestDto.getUserId();
        String reviewId = eventRequestDto.getReviewId();

        User findUser = userRepository.findByUserId(userId).orElseThrow(
            () -> new NullPointerException("해당 id의 유저가 없습니다.")
        );

        boolean isFirstReview = false;
        List<ReviewPointHistory> findReviewPointHistoryList = reviewPointRepository
            .findAllByPlaceIdAndIsFirstReviewOrderByIdDesc(placeId, true);
        if (findReviewPointHistoryList.size() == 0) {
            isFirstReview = true;
        } else if (findReviewPointHistoryList.get(0).getAction() == ActionType.DELETE) {
            isFirstReview = true;
            // TODO 리뷰가 남아있는경우
        }

        if (action == ActionType.ADD) {
            //리뷰 작성
            int reviewPoint = calculatePointWith(content, attachedPhotoIdsSize, isFirstReview);
            findUser.plusPoint(reviewPoint);
            saveReviewPointHistory(eventRequestDto, isFirstReview, reviewPoint, reviewPoint);

        } else if (action == ActionType.MOD) {
            //리뷰 수정
            List<ReviewPointHistory> findReviewPointHistories = reviewPointRepository
                .findAllByReviewIdOrderByIdDesc(reviewId);
            ReviewPointHistory latestReviewPointHistory = findReviewPointHistories.get(0);
            int oldPoint = latestReviewPointHistory.getReviewPoint();
            int newPoint = calculatePointWith(content, attachedPhotoIdsSize, isFirstReview);
            int changedPoint = newPoint - oldPoint;
            findUser.plusPoint(changedPoint);
            saveReviewPointHistory(eventRequestDto, latestReviewPointHistory.isFirstReview(),
                changedPoint, newPoint);

        } else if (action == ActionType.DELETE) {
            //리뷰 삭제
            List<ReviewPointHistory> findReviewPointHistories = reviewPointRepository
                .findAllByReviewIdOrderByIdDesc(reviewId);
            ReviewPointHistory latestReviewPointHistory = findReviewPointHistories.get(0);
            findUser.plusPoint(-latestReviewPointHistory.getReviewPoint());
            saveReviewPointHistory(eventRequestDto, latestReviewPointHistory.isFirstReview(),
                -latestReviewPointHistory.getReviewPoint(), 0);
        } else {
            throw new IllegalArgumentException("액션 정보를 찾을 수 없습니다.");
        }

    }

    private void saveReviewPointHistory(EventRequestDto eventRequestDto, boolean isFirstReview,
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


