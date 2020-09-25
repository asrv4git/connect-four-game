package com.hackerearth.connectfourgameapi.services;

import com.hackerearth.connectfourgameapi.core.GameEngine;
import com.hackerearth.connectfourgameapi.models.GameStatus;
import com.hackerearth.connectfourgameapi.models.PlayResult;
import com.hackerearth.connectfourgameapi.models.Response;
import com.hackerearth.connectfourgameapi.models.entity.Board;
import com.hackerearth.connectfourgameapi.models.entity.GameData;
import com.hackerearth.connectfourgameapi.repository.GameDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Base64;
import java.util.Optional;
import java.util.UUID;

@Service
public class GameDataService {

    @Autowired
    private GameDataRepository gameDataRepository;

    public void getAllMovesByPlayerId(String playerId) {
        Optional<GameData> gameDataOptional = gameDataRepository.findById(playerId);
        if(gameDataOptional.isPresent()){

        }
        else
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"No such player exists with player-id: "+playerId);
    }

    public Response startANewGame() {
        String playerId = Base64.getEncoder().encodeToString(UUID.randomUUID().toString().getBytes());
        gameDataRepository.save(new GameData(playerId, GameStatus.READY, new Board()));
        return new Response(playerId, GameStatus.READY.name());
    }

    public Response playTheGame(String columnNumber, String playerId) {
        Optional<GameData> gameDataOptional = gameDataRepository.findById(playerId);
        Response response;
        if(gameDataOptional.isPresent()){
            PlayResult playResult = GameEngine.playGame(gameDataOptional.get().getBoard(),
                    Integer.parseInt(columnNumber));
            gameDataRepository.save(new GameData(playerId,playResult.getGameStatus(), playResult.getBoard()));
            response = new Response(playerId,playResult.getGameStatus().equals(GameStatus.INPROGRESS)?
                    "Valid":playResult.getGameStatus().name());
        }
        else{
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"No such player exists with player-id: "+playerId);
        }

        return response;
    }
}
