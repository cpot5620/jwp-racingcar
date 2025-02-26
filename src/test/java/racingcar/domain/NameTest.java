package racingcar.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@DisplayName("자동차 이름은")
class NameTest {

    @DisplayName("1자 이상, 5자 이하로 생성할 수 있다.")
    @ParameterizedTest
    @ValueSource(strings = {"쥬", "쥬니", "쥬니헙크1"})
    void validNameLengthTest(String name) {
        assertDoesNotThrow(() -> Name.from(name));
    }

    @DisplayName("1자 이상, 5자 이하가 아닌 경우, 생성할 수 없다.")
    @ParameterizedTest
    @ValueSource(strings = {"", "123456"})
    void invalidNameLengthTest(String name) {
        assertThatThrownBy(() -> Name.from(name))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
