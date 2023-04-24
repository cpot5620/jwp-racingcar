package racingcar.dao.entity;

import racingcar.domain.Car;
import racingcar.domain.RacingGame;

import java.util.ArrayList;
import java.util.List;

public class EntityMapper {

    public static RacingGameEntity toRacingGameEntity(RacingGame racingGame) {
        return new RacingGameEntity(racingGame.getTotalRound());
    }

    public static List<CarEntity> toCarEntities(Integer gameId, RacingGame racingGame) {
        List<CarEntity> carEntities = new ArrayList<>();
        List<Car> cars = racingGame.getCars();

        for (Car car : cars) {
            carEntities.add(new CarEntity(gameId, car.getName(), car.getPosition()));
        }

        return carEntities;
    }
}
