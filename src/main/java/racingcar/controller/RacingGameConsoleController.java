package racingcar.controller;

import racingcar.controller.dto.GameResponse;
import racingcar.dao.PlayerConsoleDao;
import racingcar.dao.RacingGameConsoleDao;
import racingcar.service.RacingGameService;
import racingcar.view.InputView;
import racingcar.view.OutputView;

public class RacingGameConsoleController {

    private final NameParser nameParser;

    public RacingGameConsoleController(final NameParser nameParser) {
        this.nameParser = nameParser;
    }

    public void play() {
        final RacingGameService racingGameService = new RacingGameService(
                new RacingGameConsoleDao(),
                new PlayerConsoleDao()
        );

        final String names = InputView.requestCarName();
        final int tryCount = InputView.requestTryCount();

        final GameResponse response = racingGameService.run(nameParser.slice(names), tryCount);

        OutputView.printResult(response);
    }
}
