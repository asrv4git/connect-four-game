package com.hackerearth.connectfourgameapi.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GameRequest {
    String data;
    String playerId;
}
