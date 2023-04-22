CREATE TABLE gamestates
(
    id          INT      NOT NULL AUTO_INCREMENT,
    created_at  DATETIME NOT NULL default current_timestamp,
    initial_trial_count INT      NOT NULL,
    remaining_trial_count INT      NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE cars
(
    id        INT         NOT NULL AUTO_INCREMENT,
    game_id   INT         NOT NULL,
    name      VARCHAR(50) NOT NULL,
    position  INT         NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (game_id) REFERENCES gamestates (id)
);