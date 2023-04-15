package racingcar.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import racingcar.dto.PlayerDto;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class PlayerDaoTest {

    private final PlayerDao playerDao;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PlayerDaoTest(final PlayerDao playerDao, final JdbcTemplate jdbcTemplate) {
        this.playerDao = playerDao;
        this.jdbcTemplate = jdbcTemplate;
    }

    @BeforeEach
    void setUp() {
        jdbcTemplate.execute("DROP TABLE PARTICIPATES IF EXISTS");
        jdbcTemplate.execute("DROP TABLE GAME IF EXISTS");
        jdbcTemplate.execute("DROP TABLE PLAYER IF EXISTS" );

        jdbcTemplate.execute("CREATE TABLE GAME ( " +
                "id          BIGINT   NOT NULL AUTO_INCREMENT, " +
                "trial_count INT      NOT NULL, " +
                "created_at  DATETIME NOT NULL default current_timestamp, " +
                "PRIMARY KEY (id))");

        jdbcTemplate.execute("CREATE TABLE PLAYER ( " +
                "id   BIGINT      NOT NULL AUTO_INCREMENT, " +
                "name varchar(10) NOT NULL, " +
                "PRIMARY KEY (id)) ");

        jdbcTemplate.execute("CREATE TABLE PARTICIPATES ( " +
                "game_id   BIGINT  NOT NULL, " +
                "player_id BIGINT  NOT NULL, " +
                "position  INT     NOT NULL, " +
                "is_winner BOOLEAN NOT NULL default false, " +
                "PRIMARY KEY (game_id, player_id), " +
                "FOREIGN KEY (game_id) references GAME (id), " +
                "FOREIGN KEY (player_id) references PLAYER (id)) ");
    }

    @DisplayName("이름을 입력받아 저장한다.")
    @ParameterizedTest(name = "name: {0}")
    @ValueSource(strings = {"루카", "망고", "소니", "현구막"})
    void save(final String name) {
        //when
        Long id = playerDao.save(name);
        //then
        String sql = "SELECT name FROM PLAYER WHERE id = ?";
        assertThat(jdbcTemplate.queryForObject(sql, String.class, id)).isEqualTo(name);
    }

    @DisplayName("이름을 입력받아 조회한다.")
    @Test
    void findByName() {
        //given
        String name = "포비";
        playerDao.save(name);
        //when
        PlayerDto playerDto = playerDao.findByName(name).orElseThrow();
        //then
        assertThat(playerDto.getId()).isNotNull();
        assertThat(playerDto.getName()).isEqualTo(name);
    }


    @DisplayName("이름을 입력받아 조회한 결과가 없을 때, empty를 반환한다.")
    @Test
    void findByNameWhenEmpty() {
        //given
        String name = "네오";
        //when
        Optional<PlayerDto> playerDto = playerDao.findByName(name);
        //then
        assertThat(playerDto).isEmpty();
    }
}
