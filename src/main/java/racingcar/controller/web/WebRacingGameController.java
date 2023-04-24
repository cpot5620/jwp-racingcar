package racingcar.controller.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import racingcar.dto.request.GameRequest;
import racingcar.dto.response.GameResponse;
import racingcar.service.RacingGameService;

import javax.validation.Valid;
import java.util.List;

@RestController
public class WebRacingGameController {
    private final RacingGameService racingGameService;

    public WebRacingGameController(RacingGameService racingGameService) {
        this.racingGameService = racingGameService;
    }

    @PostMapping("/plays")
    public ResponseEntity<GameResponse> playGame(@RequestBody @Valid GameRequest gameRequest) {
        GameResponse gameResult = racingGameService.playGame(gameRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(gameResult);
    }

    @GetMapping("/plays")
    public ResponseEntity<List<GameResponse>> findAllCarGame() {
        List<GameResponse> gameResults = racingGameService.findAllCarGame();
        return ResponseEntity.ok(gameResults);
    }
}