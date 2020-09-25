package com.hackerearth.connectfourgameapi.restcontroller;

import com.hackerearth.connectfourgameapi.models.GameRequest;
import com.hackerearth.connectfourgameapi.models.Response;
import com.hackerearth.connectfourgameapi.services.GameDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api-v1/connect-4-game")
public class ApiRestController {

    @Autowired
    GameDataService gameDataService;

    /*
    * This methods returns all the moves played yet by the player
    * */
    @GetMapping("/get-all-moves/{playerId}")
    public ResponseEntity getAllMoves(@PathVariable String playerId){
        if(playerId!=null || playerId.equals(""))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Invalid Player Id");
        else{
            gameDataService.getAllMovesByPlayerId(playerId);
            return ResponseEntity.ok(null);
        }
//            return new ResponseEntity("No such user with id: "+user,HttpStatus.NOT_FOUND);
    }

    @PostMapping("/play-game")
    public ResponseEntity<Response> playGame(@RequestBody GameRequest gameRequest){
        if(gameRequest==null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Invalid game request");
        else{
            Response response;
            if(gameRequest.getData().equalsIgnoreCase("START")){
                response = gameDataService.startANewGame();
                return ResponseEntity.ok(response);
            }
            //valid column value validation
            else if(gameRequest.getData().matches("^[0-6]{1}$")) {
                response = gameDataService.playTheGame(gameRequest.getData(), gameRequest.getPlayerId());
                return ResponseEntity.ok(response);
            }
            else
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "INVALID");
        }
    }
}
