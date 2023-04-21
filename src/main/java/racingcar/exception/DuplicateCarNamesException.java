package racingcar.exception;

public class DuplicateCarNamesException extends CustomException {
    private static final int ERROR_NUMBER = 100;

    public DuplicateCarNamesException(String message) {
        super(ERROR_NUMBER, message);
    }
}
