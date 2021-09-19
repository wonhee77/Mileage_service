package triple.wonhee.mileageservice.util.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class JpaNullPointExceptionHandler {

    @ExceptionHandler(value = { JpaNullPointException.class })
    public ResponseEntity<Object> handleApiRequestException(JpaNullPointException ex) {
        JpaException jpaException = new JpaException(
                ex.getMessage(),
                HttpStatus.NOT_FOUND
        );

        return new ResponseEntity<>(
            jpaException,
                HttpStatus.NOT_FOUND
        );
    }
}
