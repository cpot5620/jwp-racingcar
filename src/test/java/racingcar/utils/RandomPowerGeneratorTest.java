package racingcar.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RandomPowerGeneratorTest {

    @DisplayName("난수의 범위는 0 이상 9 이하이다.")
    @Test
    public void randomNumberRangeTest() {
        int randomPower = RandomPowerGenerator.createRandomPower();

        assertThat(randomPower).isLessThanOrEqualTo(9)
                .isGreaterThanOrEqualTo(0);
    }
}
