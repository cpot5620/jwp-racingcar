package racingcar.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor
public class PlayRequest {

    @NotBlank(message = "자동차 이름을 입력해주세요.")
    private final String names;

    @Min(value = 1, message = "시도 횟수는 1 이상이어야 합니다")
    private final int count;
}