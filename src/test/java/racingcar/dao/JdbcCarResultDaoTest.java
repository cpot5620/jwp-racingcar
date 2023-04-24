package racingcar.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;
import racingcar.domain.CarResult;
import racingcar.domain.GameResult;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class JdbcCarResultDaoTest {
    private final JdbcTemplate jdbcTemplate;
    private JdbcGameResultDao webPlayResultDao;

    private JdbcCarResultDao jdbcCarResultDao;

    @Autowired
    public JdbcCarResultDaoTest(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @BeforeEach
    void setUp() {
        webPlayResultDao = new JdbcGameResultDao(jdbcTemplate);
        jdbcCarResultDao = new JdbcCarResultDao(jdbcTemplate);
    }

    @Test
    void saveAndFindById() {
        GameResult gameResult = new GameResult(10, Timestamp.valueOf(LocalDateTime.now()));
        long playResultId = webPlayResultDao.save(gameResult);
        CarResult carResult = new CarResult(playResultId, "juno", 3);
        long carId = jdbcCarResultDao.save(carResult);
        CarResult result = jdbcCarResultDao.findById(carId);
        assertThat(result).isNotNull();
    }

    @Test
    void findAllByPlayResultId() {
        GameResult gameResult = new GameResult(10, Timestamp.valueOf(LocalDateTime.now()));
        long playResultId = webPlayResultDao.save(gameResult);
        CarResult carResult1 = new CarResult(playResultId, "juno", 3);
        CarResult carResult2 = new CarResult(playResultId, "hongo", 3);
        long carResult1Id = jdbcCarResultDao.save(carResult1);
        long carResult2Id = jdbcCarResultDao.save(carResult2);

        List<CarResult> expectedCarResults = new ArrayList<>();
        expectedCarResults.add(new CarResult(carResult1Id, playResultId, "juno", 3));
        expectedCarResults.add(new CarResult(carResult2Id, playResultId, "hongo", 3));
        List<CarResult> carResults = jdbcCarResultDao.findAllByPlayResultId(playResultId);
        assertThat(carResults).isEqualTo(expectedCarResults);
    }
}