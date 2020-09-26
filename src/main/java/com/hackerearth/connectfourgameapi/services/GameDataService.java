package com.hackerearth.connectfourgameapi.services;

import com.hackerearth.connectfourgameapi.core.GameEngine;
import com.hackerearth.connectfourgameapi.models.GameStatus;
import com.hackerearth.connectfourgameapi.models.PlayResult;
import com.hackerearth.connectfourgameapi.models.Response;
import com.hackerearth.connectfourgameapi.models.entity.Board;
import com.hackerearth.connectfourgameapi.models.entity.GameData;
import com.hackerearth.connectfourgameapi.repository.GameDataRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Base64;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class GameDataService {

    @Autowired
    private GameDataRepository gameDataRepository;

    public Response getAllMovesByPlayerId(String playerId) {
        Optional<GameData> gameDataOptional = gameDataRepository.findById(playerId);
        if(gameDataOptional.isPresent()){
            return new Response(playerId,gameDataOptional.get().getMovesPlayed());
        }
        else
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"No such player exists with player-id: "+playerId);
    }

    public Response startANewGame() {
        String playerId = Base64.getEncoder().encodeToString(UUID.randomUUID().toString().getBytes());
        gameDataRepository.saveAndFlush(new GameData(playerId, GameStatus.READY, "",  new Board()));
        return new Response(playerId, GameStatus.READY.name());
    }

    public Response playTheGame(int columnNumber, String playerId) {
        Optional<GameData> gameDataOptional = gameDataRepository.findById(playerId);
        Response response;
        if(gameDataOptional.isPresent()){
            //game has already finished
            if(gameDataOptional.get().getGameStatus()!=GameStatus.INPROGRESS &&
                    gameDataOptional.get().getGameStatus()!=GameStatus.READY) {
                log.info("Game has already finished for player-id: " + playerId);
                return new Response(playerId, "INVALID");
            }
            PlayResult playResult = GameEngine.playGame(gameDataOptional.get().getBoard(), columnNumber);
            gameDataRepository.save(new GameData(playerId,playResult.getGameStatus(),
                    gameDataOptional.get().getMovesPlayed().concat(String.valueOf(columnNumber)),
                    playResult.getBoard()));
            response = new Response(playerId,playResult.getGameStatus().equals(GameStatus.INPROGRESS)?
                    "VALID":playResult.getGameStatus().name());
        }
        else{
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"No such player exists with player-id: "+playerId);
        }

        return response;
    }
}
