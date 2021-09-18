package triple.wonhee.mileageservice.dto;

import lombok.Getter;

@Getter
public class UserPointResponseDto {

    int userPoint;

    public UserPointResponseDto(int userPoint) {
        this.userPoint = userPoint;
    }
}
