package triple.wonhee.mileageservice.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import java.util.ArrayList;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import triple.wonhee.mileageservice.domain.ActionType;
import triple.wonhee.mileageservice.domain.Review;
import triple.wonhee.mileageservice.domain.ReviewPointHistory;
import triple.wonhee.mileageservice.domain.User;
import triple.wonhee.mileageservice.dto.EventRequestDto;
import triple.wonhee.mileageservice.repository.ReviewPointRepository;
import triple.wonhee.mileageservice.repository.ReviewRepository;
import triple.wonhee.mileageservice.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class ReviewPointHistoryServiceTest {

    @Mock
    ReviewRepository reviewRepository;

    @Mock
    UserRepository userRepository;

    @Mock
    ReviewPointRepository reviewPointRepository;

    @InjectMocks
    ReviewPointHistoryService reviewPointHistoryService;

    @Nested
    @DisplayName("ActionType이 ADD인 경우")
    class saveReviewPointAdd {

        @Test
        @DisplayName("첫 리뷰인 경우(findReviewList.size() == 1), reviewPoint +1 검증")
        public void firstReview() {
            //given
            List<String> attachedPhotoIds = new ArrayList<>();

            EventRequestDto eventRequestDto = EventRequestDto.builder()
                .action(ActionType.ADD)
                .placeId("placeId1")
                .userId("userId1")
                .attachedPhotoIds(attachedPhotoIds)
                .content("")
                .build();

            User user = User.builder().userId("userId").userPoint(0).build();

            List<Review> findReviewList = new ArrayList<>();
            Review review = Review.builder().reviewId("review1").build();
            findReviewList.add(review);

            given(reviewRepository.findAllByPlaceId("placeId1")).willReturn(findReviewList);
            given(userRepository.findByUserId("userId1")).willReturn(
                java.util.Optional.ofNullable(user));
            given(reviewPointRepository.save(any())).willReturn(any());

            //when

            ReviewPointHistory reviewPointHistory = reviewPointHistoryService
                .saveReviewPointHistory(eventRequestDto);

            //then
            Assertions.assertThat(reviewPointHistory.isFirstReview()).isEqualTo(true);
            Assertions.assertThat(reviewPointHistory.getReviewPoint()).isEqualTo(1);
            Assertions.assertThat(user.getUserPoint()).isEqualTo(1);
        }

        @Test
        @DisplayName("첫 리뷰가 아닌 경우(findReviewList.size() == 2), reviewPoint +0 검증")
        public void notFirstReview() {
            //given
            List<String> attachedPhotoIds = new ArrayList<>();

            EventRequestDto eventRequestDto = EventRequestDto.builder()
                .action(ActionType.ADD)
                .placeId("placeId1")
                .userId("userId1")
                .attachedPhotoIds(attachedPhotoIds)
                .content("")
                .build();

            User user = User.builder().userId("userId").userPoint(0).build();

            List<Review> findReviewList = new ArrayList<>();
            Review oldReview = Review.builder().reviewId("review").build();
            findReviewList.add(oldReview);
            Review review = Review.builder().reviewId("review1").build();
            findReviewList.add(review);

            given(reviewRepository.findAllByPlaceId("placeId1")).willReturn(findReviewList);
            given(userRepository.findByUserId("userId1")).willReturn(
                java.util.Optional.ofNullable(user));
            given(reviewPointRepository.save(any())).willReturn(any());

            //when

            ReviewPointHistory reviewPointHistory = reviewPointHistoryService
                .saveReviewPointHistory(eventRequestDto);

            //then
            Assertions.assertThat(reviewPointHistory.isFirstReview()).isEqualTo(false);
            Assertions.assertThat(reviewPointHistory.getReviewPoint()).isEqualTo(0);
            Assertions.assertThat(user.getUserPoint()).isEqualTo(0);
        }
    }
}