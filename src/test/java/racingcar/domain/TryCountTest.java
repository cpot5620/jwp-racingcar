package racingcar.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@DisplayName("시도 횟수가")
class TryCountTest {

    @DisplayName("1회 이상인 경우 정상 동작한다.")
    @ParameterizedTest
    @ValueSource(ints = {1, 10, 100})
    public void validTryCountTest(int count) {
        assertDoesNotThrow(() -> TryCount.from(count));
    }

    @DisplayName("1회 미만인 경우 오류가 발생한다.")
    @ParameterizedTest
    @ValueSource(ints = {-100, -30, 0})
    public void invalidTryCountTest(int count) {
        Assertions.assertThatThrownBy(() -> TryCount.from(count))
                .isInstanceOf(IllegalArgumentException.class);
    }

}
