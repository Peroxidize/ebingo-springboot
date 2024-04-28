package com.rest.ebingospring.controllers;

import com.rest.ebingospring.services.BingoGameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/game")
public class BingoGameController {
    @Autowired
    private BingoGameService bingoGameService;

    @PostMapping("/create")
    public ResponseEntity<?> createGame() {
        return ResponseEntity.ok(bingoGameService.createGame());
    }

    @GetMapping("/getGame")
    public ResponseEntity<?> getGame(@RequestParam String gameCode) {
        return bingoGameService.getGame(gameCode);
    }

    @GetMapping("/roll")
    public ResponseEntity<?> roll(@RequestParam String gameCode) {
        return bingoGameService.rollNumber(gameCode);
    }

    @GetMapping("/checkCard")
    public ResponseEntity<?> checkCard(@RequestParam String token) {
        return bingoGameService.checkCard(token);
    }
}
