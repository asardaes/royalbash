package de.schramm.royalbash.controller;

import de.schramm.royalbash.controller.requestmodel.DrawRequest;
import de.schramm.royalbash.controller.responsemodel.StateResponse;
import de.schramm.royalbash.gameengine.exception.GameEngineException;
import de.schramm.royalbash.gameengine.handler.DrawResourcesCardHandler;
import de.schramm.royalbash.model.Game;
import de.schramm.royalbash.persistence.GameManager;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequestMapping("gameloop/drawResourcesCard")
public class DrawResourcesCardController {

    private final GameManager gameManager;
    private final DrawResourcesCardHandler drawResourcesCardHandler;

    @Autowired
    public DrawResourcesCardController(
            GameManager gameManager,
            DrawResourcesCardHandler drawResourcesCardHandler
    ) {
        this.gameManager = gameManager;
        this.drawResourcesCardHandler = drawResourcesCardHandler;
    }

    @PostMapping(
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    public ResponseEntity<StateResponse> draw(
            @RequestBody DrawRequest requestParams
    ) {

        try {

            Game game = gameManager.findGame(requestParams.getGameId());
            game = drawResourcesCardHandler.draw(
                    game,
                    game.findPlayer(requestParams.getPlayerId())
            );
            gameManager.saveGame(game);
            return ResponseEntity.ok(StateResponse.fromGame(game));
        } catch (GameEngineException e) {

            log.warn("Failed to draw Resources Card due to: " + e.getMessage());

            return ResponseEntity.badRequest().body(
                    StateResponse.builder()
                            .reason(e.getMessage())
                            .build()
            );
        }
    }
}