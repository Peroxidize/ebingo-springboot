package com.rest.ebingospring.services;

import com.rest.ebingospring.entities.BingoCard;
import com.rest.ebingospring.entities.BingoGame;
import com.rest.ebingospring.repositories.BingoCardRepository;
import com.rest.ebingospring.repositories.BingoGameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Service
public class BingoGameService {
    @Autowired
    private BingoGameRepository bingoGameRepository;
    @Autowired
    private BingoCardRepository bingoCardRepository;

    public String createGame() {
        String gameCode = generateCode();
        if (getGameByCode(gameCode) == null) {
            BingoGame bingoGame = new BingoGame();
            bingoGame.setGameCode(gameCode);
            bingoGameRepository.save(bingoGame);
            return gameCode;
        }
        return createGame();
    }

    public ResponseEntity<?> getGame(String gameCode) {
        BingoGame bingoGame = bingoGameRepository.findByGameCode(gameCode);
        if (bingoGame == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Game not found!");
        }
        return ResponseEntity.ok(bingoGame);
    }

    public ResponseEntity<?> rollNumber(String gameCode) {
        BingoGame bingoGame = bingoGameRepository.findByGameCode(gameCode);
        if (bingoGame == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Game Not Found");
        }

        List<Integer> numbersRolled = new ArrayList<>(bingoGame.getNumbersRolled());
        List<Integer> numbersNotFound = new ArrayList<>();
        Random random = new Random();

        for (int i = 1; i <= 75; i++) {
            if (!numbersRolled.contains(i)) {
                numbersNotFound.add(i);
            }
        }

        if (numbersNotFound.isEmpty()) {
            return ResponseEntity.badRequest().body("All numbers have been rolled.");
        }

        int newNumberIndex =  random.nextInt(numbersNotFound.size());
        int newNumber = numbersNotFound.get(newNumberIndex);
        numbersRolled.add(newNumber);
        bingoGame.setNumbersRolled(numbersRolled);
        bingoGameRepository.save(bingoGame);
        return ResponseEntity.ok(newNumber);
    }

    public ResponseEntity<?> checkCard(String token) {
        BingoCard bingoCard = bingoCardRepository.findByToken(token);

        if (bingoCard == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Bingo card not found!");
        }

        BingoGame bingoGame = bingoCard.getBingoGame();
        List<Integer> rolledNumbers = bingoGame.getNumbersRolled();
        Map<String, List<Integer>> numbersAssigned = bingoCard.getAssignedNumbers();
        List<Integer> numbersToCheck = new ArrayList<>();

        for (String key : numbersAssigned.keySet()) {
            List<Integer> array = numbersAssigned.get(key);
            numbersToCheck.addAll(array);
        }

        for (int i : numbersToCheck) {
            if (!rolledNumbers.contains(i)) {
                return ResponseEntity.badRequest().body("No bingo!");
            }
        }
        return ResponseEntity.ok("Bingo!");
    }

    private BingoGame getGameByCode(String gameCode) {
        return bingoGameRepository.findByGameCode(gameCode);
    }

    private String generateCode() {
        final String combinations = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        final int length = 5;
        Random random = new Random();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < length; i++) {
           final int index = random.nextInt(combinations.length());
           final char randomChar = combinations.charAt(index);
           sb.append(randomChar);
        }

        return sb.toString();
    }
}
