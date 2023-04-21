package racingcar.view;

import racingcar.dto.GameResponseDto;
import racingcar.dto.PlayerDto;

import java.util.List;
import java.util.stream.Collectors;

import static java.text.MessageFormat.format;

public class OutputView {

    private static final String RESULT_MESSAGE = "\n실행 결과";
    private static final String NEW_LINE = System.lineSeparator();
    private static final String POSITION_MESSAGE_FORMAT = "{0} : {1}";
    private static final String POSITION_MESSAGE_DELIMITER = "\n";
    private static final String WINNERS_MESSAGE_FORMAT = "{0}가 최종 우승했습니다.";
    private static final String WINNERS_MESSAGE_DELIMITER = ", ";
    private static final String ERROR_MESSAGE = "[ERROR] ";

    public void printResult(final GameResponseDto gameResponse) {
        System.out.println(RESULT_MESSAGE);
        printWinnersMessage(gameResponse.getWinners());

        System.out.println(generatePositionMessages(gameResponse.getRacingCars()) + POSITION_MESSAGE_DELIMITER);
    }

    public void printWinnersMessage(final List<String> winners) {
        String winnersMessage = String.join(WINNERS_MESSAGE_DELIMITER, winners);
        System.out.println(NEW_LINE + format(WINNERS_MESSAGE_FORMAT, winnersMessage));
    }

    private String generatePositionMessages(final List<PlayerDto> cars) {
        return cars.stream()
                .map(this::generatePositionMessage)
                .collect(Collectors.joining(POSITION_MESSAGE_DELIMITER));
    }

    private String generatePositionMessage(final PlayerDto playerDto) {
        return format(
                POSITION_MESSAGE_FORMAT,
                playerDto.getName(),
                playerDto.getPosition()
        );
    }

    public void printErrorMessage(final String message) {
        System.out.println(ERROR_MESSAGE + message);
    }
}
