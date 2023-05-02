package racingcar.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import racingcar.dao.RacingCarGameDao;
import racingcar.dao.RacingCarPlayerDao;
import racingcar.domain.Names;
import racingcar.domain.RacingCar;
import racingcar.domain.RacingCars;
import racingcar.domain.TryCount;
import racingcar.dto.RacingCarDto;
import racingcar.dto.RacingCarResponse;

@Service
public class RacingGameService {

    private final RacingCarGameDao racingCarDao;
    private final RacingCarPlayerDao racingCarPlayerDao;

    public RacingGameService(final RacingCarGameDao racingCarDao, final RacingCarPlayerDao racingCarPlayerDao) {
        this.racingCarDao = racingCarDao;
        this.racingCarPlayerDao = racingCarPlayerDao;
    }

    public RacingCarResponse play(final List<String> inputNames, final int inputCount) {
        final RacingCars racingCars = createRacingCars(inputNames);
        final TryCount tryCount = createTryCount(inputCount);

        moveAllCars(racingCars, tryCount);

        final int gameId = racingCarDao.insertGame(racingCars, tryCount);
        racingCarPlayerDao.insertGameLog(racingCars, gameId);

        return createRacingCarResponse(racingCars);
    }

    private RacingCars createRacingCars(final List<String> names) {
        return new RacingCars(new Names(names)
                .getNames()
                .stream()
                .map(RacingCar::new)
                .collect(Collectors.toUnmodifiableList()));
    }

    private TryCount createTryCount(final int tryCount) {
        return new TryCount(tryCount);
    }

    private void moveAllCars(final RacingCars racingCars, TryCount tryCount) {
        while (tryCount.isOpportunity()) {
            racingCars.moveAll();
            tryCount = tryCount.deduct();
        }
    }

    private RacingCarResponse createRacingCarResponse(final RacingCars racingCars) {
        final List<RacingCarDto> racingCarDtos = getRacingCarDtos(racingCars);

        return new RacingCarResponse(racingCars.getWinnerNames(), racingCarDtos);
    }

    private List<RacingCarDto> getRacingCarDtos(final RacingCars racingCars) {
        return racingCars.getRacingCars()
                .stream()
                .map(racingCar -> new RacingCarDto(racingCar.getName(), racingCar.getPosition()))
                .collect(Collectors.toUnmodifiableList());
    }

    public List<RacingCarResponse> findAllGameLog() {
        final List<RacingCars> racingCars = racingCarPlayerDao.findAll();

        return makeRacingCarHistory(racingCars);
    }

    private List<RacingCarResponse> makeRacingCarHistory(final List<RacingCars> racingCars) {
        return racingCars.stream()
                .map(it -> new RacingCarResponse(it.getWinnerNames(), this.getRacingCarDtos(it)))
                .collect(Collectors.toUnmodifiableList());
    }
}
