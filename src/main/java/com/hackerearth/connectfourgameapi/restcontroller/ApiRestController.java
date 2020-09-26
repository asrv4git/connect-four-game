package com.hackerearth.connectfourgameapi.restcontroller;

import com.hackerearth.connectfourgameapi.models.GameRequest;
import com.hackerearth.connectfourgameapi.models.Response;
import com.hackerearth.connectfourgameapi.services.GameDataService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api-v1/connect-4-game")
@Api(value = "connect 4 game", description = "Game API v1")
public class ApiRestController {

    @Autowired
    GameDataService gameDataService;

    /*
    * This methods returns all the moves played yet by the player
    * */

    @GetMapping("/get-all-moves/{playerId}")
    @ApiOperation(value = "View all moves played (column number) by a player ", response = Response.class)
    public ResponseEntity<Response> getAllMoves(@PathVariable @ApiParam(value = "player-id to search for") String playerId){
        if(playerId==null || playerId.equals(""))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Invalid Player Id");
        else{
            return ResponseEntity.ok(gameDataService.getAllMovesByPlayerId(playerId));
        }
    }

    @PostMapping("/play-game")
    @ApiOperation(value = "main game play api. Send \"START\" in data to start a new game keeping player-id blank or null." +
            "Subsequent player request should contain a valid column number in data and id of player whose game is to be played.",
            response = Response.class)
    public ResponseEntity<Response> playGame(@RequestBody @ApiParam(value = "valid game request", allowableValues = "START,0,1,2,3,4,5,6") GameRequest gameRequest){
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
                response = gameDataService.playTheGame(Integer.parseInt(gameRequest.getData()), gameRequest.getPlayerId());
                return ResponseEntity.ok(response);
            }
            else
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "INVALID");
        }
    }
}
