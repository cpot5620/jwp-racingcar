package racingcar.services;

import java.util.List;

import racingcar.dao.entity.Winner;
import racingcar.dao.winner.WinnerDao;

public class TestWinnerDao implements WinnerDao {
    @Override
    public void insertWinner(List<Winner> winners) {

    }

    @Override
    public List<Winner> findAllWinner() {
        return List.of(new Winner(1L, "이리내"));
    }
}