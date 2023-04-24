package racingcar.api.advice;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import racingcar.dto.ErrorResult;
import racingcar.exception.RacingCarException;

@RestControllerAdvice
public class CarControllerAdvice {

    @ExceptionHandler
    public ResponseEntity<ErrorResult> handleException(RacingCarException racingCarException) {
        return ErrorResult.toResponseEntity(String.valueOf(racingCarException.getStatus().value()),
                        racingCarException.getMessage());
    }
}
