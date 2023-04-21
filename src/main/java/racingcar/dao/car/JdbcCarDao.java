package racingcar.dao.car;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import racingcar.dao.entity.Car;

@Repository
public class JdbcCarDao implements CarDao {
    private static final RowMapper<Car> CAR_ROW_MAPPER = (resultSet, index) -> new Car(
            resultSet.getLong("g_id"),
            resultSet.getString("name"),
            resultSet.getInt("position")
    );
    private final JdbcTemplate jdbcTemplate;

    public JdbcCarDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void insertCar(List<Car> cars) {
        String sql = "INSERT INTO car(g_id, name, position) VALUES (?,?,?)";
        List<Object[]> carsInfo = getCarsInfo(cars);
        jdbcTemplate.batchUpdate(sql, carsInfo);
    }

    private List<Object[]> getCarsInfo(List<Car> cars) {
        return cars.stream()
                .map(car -> {
                    Object[] objects = new Object[3];
                    objects[0] = car.getGameId();
                    objects[1] = car.getName();
                    objects[2] = car.getPosition();
                    return objects;
                })
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public List<Car> findAllCars() {
        String sql = "SELECT g_id, name, position FROM car";
        return jdbcTemplate.query(sql, CAR_ROW_MAPPER);
    }
}