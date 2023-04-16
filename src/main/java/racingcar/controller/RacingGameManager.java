package racingcar.controller;

import racingcar.domain.Name;
import racingcar.domain.RacingGame;
import racingcar.domain.TryCount;
import racingcar.view.InputView;
import racingcar.view.OutputView;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class RacingGameManager {
    private RacingGame racingGame;

    public void run() {
        racingGame = retryOnInvalidUserInput(() -> new RacingGame(readCarNames()));
        TryCount tryCount = retryOnInvalidUserInput(this::readTryCount);

        startRace(tryCount);

        OutputView.printAllCars(racingGame.getCars());
        OutputView.printWinners(racingGame.decideWinners());
    }

    private <T> T retryOnInvalidUserInput(Supplier<T> request) {
        try {
            return request.get();
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return retryOnInvalidUserInput(request);
        }
    }

    private List<Name> readCarNames() {
        return InputView.readCarNames()
                .stream()
                .map(Name::new)
                .collect(Collectors.toList());
    }

    private TryCount readTryCount() {
        return new TryCount(InputView.readCount());
    }

    private void startRace(TryCount tryCount) {
        OutputView.printResultMessage();

        for (int i = 0; i < tryCount.getCount(); i++) {
            racingGame.moveCars(new TryCount(1));
            OutputView.printAllCars(racingGame.getCars());
        }
    }
}