package racingcar.service;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import racingcar.dao.JdbcPlayerInfoDAO;
import racingcar.dao.JdbcPlayResultDAO;
import racingcar.dao.PlayResultDAO;
import racingcar.dao.PlayerInfoDAO;
import racingcar.domain.CarFactory;
import racingcar.domain.Cars;
import racingcar.domain.TryCount;
import racingcar.dto.CarDto;
import racingcar.dto.MoveRequestDto;
import racingcar.dto.MoveResponseDto;
import racingcar.dto.PlayResponseDto;
import racingcar.entity.PlayResultEntity;
import racingcar.genertor.NumberGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RacingCarService {

    private final PlayResultDAO playResultDAO;
    private final PlayerInfoDAO playerInfoDAO;
    private final NumberGenerator numberGenerator;

    public RacingCarService(PlayResultDAO playResultDAO, PlayerInfoDAO playerInfoDAO, NumberGenerator numberGenerator) {
        this.playResultDAO = playResultDAO;
        this.playerInfoDAO = playerInfoDAO;
        this.numberGenerator = numberGenerator;
    }

    @Transactional
    public MoveResponseDto moveCar(MoveRequestDto moveRequestDto) {
        Cars cars = new Cars(CarFactory.buildCars(moveRequestDto.getNames()));
        TryCount tryCount = new TryCount(moveRequestDto.getCount());
        play(cars, tryCount, numberGenerator);
        saveCarResult(cars, moveRequestDto.getCount());
        return new MoveResponseDto(cars.findWinners(), cars.getCars());
    }

    private void play(Cars cars, TryCount tryCount, NumberGenerator numberGenerator) {
        int count = tryCount.getCount();
        while (count-- > 0) {
            cars.moveCars(numberGenerator);
        }
    }

    private void saveCarResult(Cars cars, int trialCount) {
        int tableId = playResultDAO.returnTableIdAfterInsert(trialCount, cars.findWinners());
        playerInfoDAO.insert(tableId, cars.getCars());
    }

    @Transactional
    public List<PlayResponseDto> findAllGameHistory() {
        final ArrayList<PlayResponseDto> playResponseDtos = new ArrayList<>();
        for (PlayResultEntity playResultEntity : playResultDAO.findAll()) {
            playResponseDtos.add(new PlayResponseDto(playResultEntity.getWinners(), makeCarDto(playResultEntity)));
        }
        return playResponseDtos;
    }

    private List<CarDto> makeCarDto(final PlayResultEntity playResultEntity) {
        return playerInfoDAO.findPlayerByPlayResultId(playResultEntity.getId()).stream()
                .map((playerInfo) -> new CarDto(playerInfo.getName(), playerInfo.getPosition()))
                .collect(Collectors.toList());
    }
}
