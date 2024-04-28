package com.rest.ebingospring.controllers;

import com.rest.ebingospring.services.BingoCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/card")
public class BingoCardController {
    @Autowired
    private BingoCardService bingoCardService;

    @PostMapping("/getCard")
    public ResponseEntity<?> getCard(@RequestParam String gameCode) {
        return bingoCardService.getCard(gameCode);
    }
}
