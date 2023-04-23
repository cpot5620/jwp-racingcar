package racingcar.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import racingcar.dao.RacingCarDao;
import racingcar.domain.Names;
import racingcar.domain.RacingCar;
import racingcar.domain.RacingCars;
import racingcar.domain.TryCount;
import racingcar.dto.RacingCarDto;
import racingcar.dto.RacingCarRequestDto;
import racingcar.dto.RacingCarResponseDto;
import racingcar.dto.RacingCarsDto;
import racingcar.dto.TryCountDto;
import racingcar.dto.WinnersDto;

@Service
public class RacingCarService {
    private final RacingCarDao racingCarDao;

    public RacingCarService(RacingCarDao racingCarDao) {
        this.racingCarDao = racingCarDao;
    }

    public RacingCarResponseDto addRace(RacingCarRequestDto racingCarRequestDto) {
        RacingCars racingCars = createRacingCar(racingCarRequestDto);
        TryCount tryCount = new TryCount(racingCarRequestDto.getTryCount());

        playGame(racingCars, tryCount);

        RacingCarsDto racingCarsDto = new RacingCarsDto(racingCars);
        racingCarDao.insertGame(racingCarsDto, new TryCountDto(tryCount));
        return new RacingCarResponseDto(racingCarsDto);
    }

    private RacingCars createRacingCar(RacingCarRequestDto racingCarRequestDto) {
        Names names = new Names(racingCarRequestDto.getNames());
        return new RacingCars(createRacingCar(names));
    }

    private List<RacingCar> createRacingCar(Names names) {
        return names.getNames()
            .stream()
            .map(RacingCar::new)
            .collect(Collectors.toList());
    }

    private void playGame(RacingCars racingCars, TryCount tryCount) {
        for (int i = 0; i < tryCount.getCount(); i++) {
            racingCars.moveAll();
        }
    }

    public List<RacingCarResponseDto> findRace() {
        List<RacingCarResponseDto> racingCarResponseDtos = new ArrayList<>();
        for (WinnersDto winnersDto : racingCarDao.selectWinners()) {
            int gameIds = winnersDto.getGameIds();
            List<RacingCarDto> racingCarDtos = racingCarDao.selectRace(gameIds);
            racingCarResponseDtos.add(new RacingCarResponseDto(winnersDto, racingCarDtos));
        }
        return racingCarResponseDtos;
    }
}
