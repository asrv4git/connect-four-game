package com.hackerearth.connectfourgameapi.models.entity;

import com.hackerearth.connectfourgameapi.models.GameStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "game_data")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GameData {
    @Id
    @Column(name = "player_id")
    String playerId;

//    @Enumerated(EnumType.STRING)
    @Column(name = "game_status")
    GameStatus gameStatus;

    @Embedded
    @Column(name = "board")
    Board board;
}
