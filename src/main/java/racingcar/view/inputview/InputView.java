package racingcar.view.inputview;

import java.util.Scanner;

public class InputView {
    private static final String GET_CAR_NAME_MESSAGE = "경주할 자동차 이름을 입력하세요(이름은 쉼표(,)를 기준으로 구분).";
    private static final String GET_TRY_TIME_MESSAGE = "시도할 횟수는 몇회인가요?";

    private static Scanner scanner = new Scanner(System.in);

    private InputView() {}

    public static String inputCarNames() {
        System.out.println(GET_CAR_NAME_MESSAGE);
        return nextLine();
    }

    private static String nextLine() {
        return scanner.nextLine();
    }

    public static String inputTrialTimes() {
        System.out.println(GET_TRY_TIME_MESSAGE);
        return nextLine();
    }
}
