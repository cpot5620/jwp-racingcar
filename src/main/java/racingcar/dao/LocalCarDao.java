package racingcar.dao;

import racingcar.dao.dto.CarInfoDTO;
import racingcar.dao.dto.CarNameDTO;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class LocalCarDao implements CarDao {

    private static final int UPDATED_ROWS = 1;

    private final Map<Long, CarEntity> carEntities = new LinkedHashMap<>();
    private Long id = 0L;

    @Override
    public int insert(final String name, final int position, final Long gameId, final boolean isWin) {
        id++;
        carEntities.put(id, new CarEntity(id, name, position, gameId, isWin));
        return UPDATED_ROWS;
    }

    @Override
    public int countRows() {
        return carEntities.size();
    }

    @Override
    public void deleteAll() {
        carEntities.clear();
    }

    @Override
    public List<CarNameDTO> findWinners(final Long gameId) {
        return carEntities.values().stream()
                .filter(carEntity -> gameId.equals(carEntity.getGameId()) && carEntity.isWin())
                .map(carEntity -> new CarNameDTO(carEntity.getName()))
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public List<CarInfoDTO> findAllCarNamesAndPositions(final Long gameId) {
        return carEntities.values().stream()
                .filter(carEntity -> gameId.equals(carEntity.getGameId()))
                .map(carEntity -> new CarInfoDTO(carEntity.getName(), carEntity.getPosition()))
                .collect(Collectors.toUnmodifiableList());
    }

    private static class CarEntity {

        private final Long id;
        private final String name;
        private final int position;
        private final Long gameId;
        private final boolean isWin;

        public CarEntity(final Long id, final String name, final int position, final Long gameId, final boolean isWin) {
            this.id = id;
            this.name = name;
            this.position = position;
            this.gameId = gameId;
            this.isWin = isWin;
        }

        public Long getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public int getPosition() {
            return position;
        }

        public Long getGameId() {
            return gameId;
        }

        public boolean isWin() {
            return isWin;
        }
    }
}
