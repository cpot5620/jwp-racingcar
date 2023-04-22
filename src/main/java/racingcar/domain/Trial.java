package racingcar.domain;

import java.util.Objects;

public class Trial {
    public static final String NOT_POSITIVE_NUMBER_EXCEPTION_MESSAGE = "양수의 숫자만 입력할 수 있습니다.";
    private final int trial;

    private Trial(int trial) {
        this.trial = trial;
    }

    public static Trial of(int number) {
        validateNumber(number);
        return new Trial(number);
    }

    private static void validateNumber(int number) {
        if (number <= 0) {
            throw new IllegalArgumentException(NOT_POSITIVE_NUMBER_EXCEPTION_MESSAGE);
        }
    }

    public int getValue() {
        return trial;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Trial trial1 = (Trial) o;
        return trial == trial1.trial;
    }

    @Override
    public int hashCode() {
        return Objects.hash(trial);
    }

    @Override
    public String toString() {
        return "Trial{" +
                "trial=" + trial +
                '}';
    }
}
