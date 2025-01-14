package racingcar.dao;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.Transactional;
import racingcar.dao.entity.PlayerResultEntity;
import racingcar.dto.CarDto;
import racingcar.dto.GameResultDto;
import racingcar.dto.PlayerResultDto;

import java.util.List;
import java.util.stream.Collectors;

@ComponentScan
@JdbcTest
class PlayerResultDAOInJdbcTest {
    @Autowired
    private GameResultDAOInJdbc gameResultDAOInJdbc;
    @Autowired
    private PlayerResultDAOInJdbc playerResultDAOInJdbc;

    @DisplayName("플레이어들의 게임 결과를 저장할 수 있다.")
    @Transactional
    @Test
    void savePlayerResultTest() {
        //given
        List<CarDto> carDtos = List.of(CarDto.of("zuny", 10), CarDto.of("dochi", 7));
        int savedId = gameResultDAOInJdbc.save(GameResultDto.from(10));

        //when
        playerResultDAOInJdbc.saveAll(PlayerResultDto.of(carDtos, savedId));

        //then
        List<PlayerResultEntity> findResult = playerResultDAOInJdbc.findAll();
        List<String> findNames = getNamesFrom(findResult);
        List<Integer> findPositions = getPositionsFrom(findResult);

        Assertions.assertThat(findNames)
                .containsExactlyInAnyOrder("zuny", "dochi");
        Assertions.assertThat(findPositions)
                .containsExactlyInAnyOrder(10, 7);
    }

    private List<Integer> getPositionsFrom(List<PlayerResultEntity> findResult) {
        return findResult.stream()
                .map(PlayerResultEntity::getPosition)
                .collect(Collectors.toList());
    }

    private List<String> getNamesFrom(List<PlayerResultEntity> findResult) {
        return findResult.stream()
                .map(PlayerResultEntity::getName)
                .collect(Collectors.toList());
    }
}
