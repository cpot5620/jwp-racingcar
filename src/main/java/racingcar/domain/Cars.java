package racingcar.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Cars {

    private static final int CARS_MIN_SIZE = 1;
    private static final String CARS_SIZE_ERROR = "[ERROR] 자동차 대수는 1이상이어야 합니다.";

    private List<Car> cars;

    public Cars(List<Car> cars) {
        validateCarsSize(cars);
        this.cars = new ArrayList<>(cars);
    }

    public List<Car> findAllWinner() {
        Car maxPositionCar = findMaxPositionCar();
        return cars.stream()
                .filter(car -> car.isSamePosition(maxPositionCar))
                .collect(Collectors.toList());
    }

    public List<Car> moveEachCar() {
        for (Car car : cars) {
            car.goForward();
        }
        return new ArrayList<>(cars);
    }

    // Todo: 나중에 중복되는 이름은 입력되면 예외처리 되도록 구현해야 한다.
    private void validateCarsSize(List<Car> cars) {
        if (cars.size() < CARS_MIN_SIZE) {
            throw new IllegalArgumentException(CARS_SIZE_ERROR);
        }
    }

    private Car findMaxPositionCar() {
        cars.sort(Car::compareTo);
        return cars.get(0);
    }

    public List<Car> getCars() {
        return cars;
    }
}
