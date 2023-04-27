package racingcar.domain;

import java.util.Random;
import org.springframework.stereotype.Component;

@Component
public class RandomNumberGenerator implements NumberGenerator {

    private static final int RANDOM_NUMBER_MAX_RANGE = 10;

    private static final Random random = new Random();

    @Override
    public int generate() {
        return random.nextInt(RANDOM_NUMBER_MAX_RANGE);
    }
}
