package triple.wonhee.mileageservice.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    String userId;

    int userPoint;

    public void plusPoint(int point) {
        userPoint += point;
    }

    public User(String userId, int userPoint) {
        this.userId = userId;
        this.userPoint = userPoint;
    }
}
