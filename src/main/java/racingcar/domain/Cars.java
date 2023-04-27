package racingcar.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Cars {

    private static final int CARS_MIN_SIZE = 2;

    private final List<Car> cars;

    public Cars(List<Car> cars) {
        validate(cars);
        this.cars = new ArrayList<>(cars);
    }

    private void validate(List<Car> cars) {
        if (cars.size() < CARS_MIN_SIZE) {
            throw new IllegalArgumentException("자동차 대수는 2 이상이어야 합니다.");
        }

        if (hasDuplicateName(cars)) {
            throw new IllegalArgumentException("중복된 자동차 이름이 존재합니다.");
        }
    }

    private boolean hasDuplicateName(List<Car> cars) {
        return cars.size() != cars.stream()
                .map(Car::getName)
                .distinct()
                .count();
    }

    public void move(NumberGenerator numberGenerator) {
        for (Car car : cars) {
            car.move(numberGenerator.generate());
        }
    }

    public List<Car> findAllWinner() {
        Car maxPositionCar = findMaxPositionCar();
        return findSamePositionCars(maxPositionCar);
    }

    private Car findMaxPositionCar() {
        return cars.stream()
                .max(Car::compareTo)
                .orElseThrow(() -> new IllegalArgumentException("Cars가 비어있습니다."));
    }

    private List<Car> findSamePositionCars(Car maxPositionCar) {
        return cars.stream()
                .filter(car -> car.isSamePosition(maxPositionCar))
                .collect(Collectors.toList());
    }

    public List<Car> getCars() {
        return cars;
    }
}
