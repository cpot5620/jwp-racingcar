package racingcar;

import racingcar.controller.ConsoleRacingCarController;
import racingcar.repository.ConsoleRacingCarRepository;
import racingcar.service.RacingCarService;
import racingcar.view.InputView;
import racingcar.view.OutputView;

import java.util.Scanner;

public class ConsoleRacingCarApplication {

    public static void main(String[] args) {
        ConsoleRacingCarController controller = new ConsoleRacingCarController(new InputView(new Scanner(System.in)),
                new OutputView(),
                new RacingCarService(new ConsoleRacingCarRepository()));

        controller.run();
    }
}
