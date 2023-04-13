package racingcar.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import racingcar.entity.PlayResultEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class PlayResultMapperTest {

    private final PlayResultMapper mapper;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    PlayResultMapperTest(PlayResultMapper mapper, JdbcTemplate jdbcTemplate) {
        this.mapper = mapper;
        this.jdbcTemplate = jdbcTemplate;
    }

    @BeforeEach
    void setUp() {
        jdbcTemplate.execute("DROP TABLE PLAY_RESULT IF EXISTS");
        jdbcTemplate.execute("CREATE TABLE PLAY_RESULT" +
                "(" +
                "    id         INT         NOT NULL AUTO_INCREMENT," +
                "    trial_count INT         NOT NULL," +
                "    winners    VARCHAR(50) NOT NULL," +
                "    created_at DATETIME    NOT NULL default current_timestamp," +
                "    PRIMARY KEY (id)" +
                ");");
    }

    @Test
    void 게임_기록_저장_조회_테스트() {
        PlayResultEntity entity = PlayResultEntity.of(0, 10, "aa", null);
        long id = mapper.save(entity);
        PlayResultEntity result = mapper.findById(id);
        System.out.println(result);
        assertThat(result).isNotNull();
    }
}
