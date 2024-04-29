package com.rest.ebingospring.services;

import com.rest.ebingospring.entities.BingoCard;
import com.rest.ebingospring.entities.BingoGame;
import com.rest.ebingospring.repositories.BingoCardRepository;
import com.rest.ebingospring.repositories.BingoGameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BingoCardService {
    @Autowired
    private BingoCardRepository bingoCardRepository;
    @Autowired
    private BingoGameRepository bingoGameRepository;
    private static final Random random = new Random();

    public ResponseEntity<?> getCard(String gameCode) {
        if (bingoGameRepository.findByGameCode(gameCode) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Game not found!");
        }

        return ResponseEntity.ok(generateCard(gameCode));
    }

    private BingoCard generateCard(String gameCode) {
        String token = generateToken();
        Map<String, List<Integer>> numbersAssigned = generateNumbers();
        BingoGame bingoGame = bingoGameRepository.findByGameCode(gameCode);

        if (bingoCardRepository.findByToken(token) != null) {
            return generateCard(gameCode);
        }

        BingoCard bingoCard = new BingoCard();
        bingoCard.setToken(token);
        bingoCard.setAssignedNumbers(numbersAssigned);
        bingoCard.setBingoGame(bingoGame);
        bingoCardRepository.save(bingoCard);
        return bingoCard;
    }

    private Map<String, List<Integer>> generateNumbers() {
        Map<String, List<Integer>> bingoCard = new HashMap<>();
        List<List<Integer>> categories = new ArrayList<>();
        List<Integer> B;
        List<Integer> I;
        List<Integer> N;
        List<Integer> G;
        List<Integer> O;

        for (int i = 0; i < 5; i++) {
            categories.add(new ArrayList<>());
        }
        for (int i = 1; i <= 75; i++) {
            int category = (i - 1) / 15;
            List<Integer> array = categories.get(category);
            array.add(i);
        }

        B = categories.get(0);
        I = categories.get(1);
        N = categories.get(2);
        G = categories.get(3);
        O = categories.get(4);

        for (int i = 1; i <= 50; i++) {
            int category = (i - 1) / 10;
            List<Integer> array = categories.get(category);
            array.remove(random.nextInt(array.size()));
        }

        bingoCard.put("B", B);
        bingoCard.put("I", I);
        bingoCard.put("N", N);
        bingoCard.put("G", G);
        bingoCard.put("O", O);
        return bingoCard;
    }

    private String generateToken() {
        final String combinations = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        final int length = 5;
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < length; i++) {
            final int index = random.nextInt(combinations.length());
            final char randomChar = combinations.charAt(index);
            sb.append(randomChar);
        }

        return sb.toString();
    }
}
