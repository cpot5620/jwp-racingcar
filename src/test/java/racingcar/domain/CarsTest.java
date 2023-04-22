package racingcar.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import racingcar.utils.NumberGenerator;
import racingcar.utils.RandomNumberGenerator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class CarsTest {
    Cars cars;
    Car car = Car.of(Name.of("test"));
    Car foxCar = Car.of(Name.of("fox"));

    private static Cars makeCarsWithMockValues(Integer... testNumbers) {
        return new Cars(new ArrayList<>(),
                new TestRandomNumberGenerator(
                        Arrays.stream(testNumbers).collect(Collectors.toList())
                ));
    }

    @Test
    @DisplayName("차 추가 테스트")
    void givenCar_whenSavingCar_thenSavesCar() {
        ArrayList<Car> carHolder = new ArrayList<>();
        cars = new Cars(carHolder, new RandomNumberGenerator());

        cars.saveCar(car);

        Assertions.assertThat(carHolder)
                .as("Cars.saveCar 메소드를 호출하면 Cars 생성 시 인자로 들어간 ArrayList에 저장하므로," +
                        "carHolder의 변화 여부를 테스트함으로써 저장 기능이 제대로 동작하는 지 테스트할 수 있다.")
                .hasSize(1)
                .containsExactly(car);
    }

    @Test
    @DisplayName("move 실행시 value가 1 증가한다.")
    void givenCarsHavingACar_whenMovesCar_thenAddsPosition() {
        cars = makeCarsWithMockValues(4);
        cars.saveCar(car);

        cars.move();

        int position = car.getPosition();
        assertThat(position).isEqualTo(1L);
    }

    @Test
    @DisplayName("position 값이 제일 높은 Car의 이름을 반환한다.")
    void givenMovedCar_whenFindsWinnerNames_thenReturnsWinnerName() {
        cars = makeCarsWithMockValues(3, 4);
        cars.saveCar(car);
        cars.saveCar(foxCar);
        cars.move();

        List<String> winners = cars.getWinnerNames();

        assertThat(winners).containsExactly("fox");
    }

    @Test
    @DisplayName("position 값이 제일 높은 Car가 여러 대라면 모든 우승자 이름을 반환한다.")
    void givenCarsHavingMultipleWinners_whenFindsWinnerNames_thenReturnsAllWinnerNames() {
        cars = makeCarsWithMockValues(4, 4);
        cars.saveCar(car);
        cars.saveCar(foxCar);
        cars.move();

        List<String> winnerNames = cars.getWinnerNames();

        assertThat(winnerNames).containsExactly("test", "fox");
    }

    static class TestRandomNumberGenerator implements NumberGenerator {
        private final Iterator<Integer> testNumber;

        public TestRandomNumberGenerator(List<Integer> testNumber) {
            this.testNumber = testNumber.iterator();
        }

        @Override
        public int generateNumber() {
            while (testNumber.hasNext()) {
                return testNumber.next();
            }

            throw new IllegalArgumentException();
        }
    }
}