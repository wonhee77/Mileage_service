package triple.wonhee.mileageservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import triple.wonhee.mileageservice.dto.UserPointResponseDto;
import triple.wonhee.mileageservice.service.UserService;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("users/{id}/point")
    public UserPointResponseDto getUserPoint(@PathVariable("id") String userId) {
        return userService.getUserPoint(userId);
    }
}
