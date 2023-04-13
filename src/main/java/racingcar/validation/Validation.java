package racingcar.validation;

import racingcar.constant.ExceptionMessage;

public class Validation {
    private static final int MIN_TRY_COUNT_INPUT = 1;

    public static void validateCarNames(String carNames) {
        if (carNames.isEmpty()) {
            throw new IllegalArgumentException(
                    ExceptionMessage.CAR_NAME_NOT_INPUT_MESSAGE.getExceptionMessage());
        }

        if (carNames.isBlank()) {
            throw new IllegalArgumentException(
                    ExceptionMessage.CAR_NAME_BLANK_MESSAGE.getExceptionMessage());
        }
    }

    public static void validateTryCount(int tryCount) {
        if (tryCount < MIN_TRY_COUNT_INPUT) {
            throw new IllegalArgumentException(
                    ExceptionMessage.TRY_COUNT_NOT_POSITIVE_MESSAGE.getExceptionMessage());
        }
    }
}
