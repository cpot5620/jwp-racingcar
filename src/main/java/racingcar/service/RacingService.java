package racingcar.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import racingcar.domain.*;
import racingcar.domain.entity.CarInfo;
import racingcar.domain.entity.Race;
import racingcar.dto.CarDto;
import racingcar.dto.RacingResultDto;
import racingcar.exception.RepositoryOutOfSpaceException;
import racingcar.repository.CarInfoRepository;
import racingcar.repository.RaceRepository;
import racingcar.utils.NumberGenerator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class RacingService {

    private final NumberGenerator numberGenerator;
    private final RaceRepository raceRepository;
    private final CarInfoRepository carInfoRepository;

    public RacingService(NumberGenerator numberGenerator, RaceRepository raceRepository, CarInfoRepository carInfoRepository) {
        this.numberGenerator = numberGenerator;
        this.raceRepository = raceRepository;
        this.carInfoRepository = carInfoRepository;
    }

    public int race(String names, int count) {
        Cars cars = initializeCars(names);
        Trial trial = Trial.of(count);
        playGame(cars, trial);
        return saveResult(trial, cars);
    }

    private Cars initializeCars(String names) {
        Names carNames = getCarNames(names);
        return saveCars(carNames);
    }

    private Names getCarNames(String names) {
        return Names.of(
                Name.of(Arrays.asList(names.split(",")))
        );
    }

    private void playGame(Cars cars, Trial trial) {
        for (int count = 0; count < trial.getValue(); count++) {
            cars.move();
        }
    }

    private Cars saveCars(Names names) {
        List<Car> cars = new ArrayList<>();
        names.nameIterator()
                .forEachRemaining(name -> cars.add(Car.of(name)));
        return new Cars(cars, numberGenerator);
    }

    private int saveResult(Trial trial, Cars cars) {
        final Optional<Integer> raceId = raceRepository.saveRace(new Race(trial));
        if (raceId.isEmpty()) {
            throw new RepositoryOutOfSpaceException();
        }
        int racingId = raceId.get();
        for (CarDto carDto : cars.getCarDtos()) {
            String name = carDto.getName();
            CarInfo carInfo = new CarInfo(racingId, name, carDto.getPosition(), cars.isWinnerContaining(name));
            Optional<Integer> carInfoId = carInfoRepository.saveCar(carInfo);
            if (carInfoId.isEmpty()) {
                throw new RepositoryOutOfSpaceException();
            }
        }
        return racingId;
    }

    public List<RacingResultDto> findAllResults() {
        ArrayList<RacingResultDto> racingResults = new ArrayList<>();

        List<Integer> raceIds = raceRepository.findAllId();
        for (Integer raceId : raceIds) {
            findRaceById(raceId).ifPresent(racingResults::add);
        }

        return racingResults;
    }

    public Optional<RacingResultDto> findRaceById(int raceId) {
        List<CarInfo> carInfos = carInfoRepository.findAllByRaceId(raceId);
        if (carInfos.isEmpty()) {
            return Optional.empty();
        }

        List<String> winnerNames = carInfos.stream()
                .filter(CarInfo::getIsWinner)
                .map(CarInfo::getName)
                .collect(Collectors.toList());

        List<CarDto> carDtos = carInfos.stream()
                .map(CarDto::new)
                .collect(Collectors.toList());

        return Optional.of(new RacingResultDto(winnerNames, carDtos));
    }
}
