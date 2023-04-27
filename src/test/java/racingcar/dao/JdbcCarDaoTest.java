package racingcar.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import racingcar.domain.entity.CarResultEntity;

@JdbcTest
class JdbcCarDaoTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private JdbcRacingGameDao jdbcRacingGameDao;

    private JdbcCarDao jdbcCarDao;

    @BeforeEach
    void setUp() {
        jdbcRacingGameDao = new JdbcRacingGameDao(jdbcTemplate);
        jdbcCarDao = new JdbcCarDao(jdbcTemplate);
    }

    @Test
    @DisplayName("주어진 자동차를 모두 저장한다.")
    void save_all() {
        //given
        List<CarResultEntity> carEntities = List.of(new CarResultEntity("현서", 10, true),
                new CarResultEntity("참치", 2, false));
        int gameId = jdbcRacingGameDao.save(10);
        //when
        jdbcCarDao.saveAll(gameId, carEntities);
        Integer actual = jdbcTemplate.queryForObject("select count(*) from car", Integer.class);
        //then
        assertThat(actual).isEqualTo(2);
    }

    @Test
    @DisplayName("find_all")
    void find_all() {
        //given
        List<CarResultEntity> carEntities = List.of(new CarResultEntity("현서", 10, true),
                new CarResultEntity("참치", 2, false));
        int gameId = jdbcRacingGameDao.save(10);
        jdbcCarDao.saveAll(gameId, carEntities);
        //when
        List<CarResultEntity> actual = jdbcCarDao.findAll();
        //then
        assertThat(actual.size()).isEqualTo(2);
    }
}
