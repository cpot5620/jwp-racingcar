package racingcar.dto;

public class RacingCarGameRequest {

    private String names;
    private String count;

    public String getNames() {
        return names;
    }

    public int getCount() {
        try {
            return Integer.parseInt(count);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("숫자 형식이 아닌 count입니다");
        }
    }
}
