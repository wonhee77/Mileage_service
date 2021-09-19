package triple.wonhee.mileageservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import triple.wonhee.mileageservice.domain.User;
import triple.wonhee.mileageservice.dto.UserPointResponseDto;
import triple.wonhee.mileageservice.repository.UserRepository;
import triple.wonhee.mileageservice.util.exception.JpaNullPointException;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public UserPointResponseDto getUserPoint(String userId) {
        User user = userRepository.findByUserId(userId).orElseThrow(
            () -> new JpaNullPointException("해당 id의 유저가 없습니다.")
        );
        UserPointResponseDto userPointResponseDto = new UserPointResponseDto(user.getUserPoint());
        return userPointResponseDto;
    }
}
