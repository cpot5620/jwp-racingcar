package racingcar.dao;

import racingcar.dao.dto.CarInfoDTO;
import racingcar.dao.dto.CarNameDTO;

import java.util.List;

public interface CarDao {

    int insert(final String name, final int position, final Long gameId, final boolean isWin);

    int countRows();

    void deleteAll();

    List<CarNameDTO> findWinners(final Long gameId);

    List<CarInfoDTO> findAllCarNamesAndPositions(final Long gameId);
}
