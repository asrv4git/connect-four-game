package com.hackerearth.connectfourgameapi.models;

import com.hackerearth.connectfourgameapi.models.entity.Board;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PlayResult {
    GameStatus gameStatus;
    Board board;
}
