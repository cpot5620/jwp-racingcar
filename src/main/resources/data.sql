
CREATE TABLE GAME_RESULT(
    id  INT NOT NULL AUTO_INCREMENT,
    trial_count INT NOT NULL,
    created_at DATETIME NOT NULL default current_timestamp,
    PRIMARY KEY (id)
);

CREATE TABLE PLAYER_RESULT(
    id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    position INT NOT NULL,
    game_id INT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (game_id) REFERENCES GAME_RESULT(id) ON UPDATE CASCADE
);
