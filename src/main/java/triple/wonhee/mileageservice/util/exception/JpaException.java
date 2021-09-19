package triple.wonhee.mileageservice.util.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public class JpaException {
    private final String message;
    private final HttpStatus httpStatus;
}
