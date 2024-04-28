package com.rest.ebingospring.entities;

import jakarta.persistence.*;

import java.util.List;
import java.util.Map;

@Entity
@Table(name = "bingo_card")
public class BingoCard {
    @Id
    private String token;

    @ManyToOne
    private BingoGame bingoGame;

    @ElementCollection
    private Map<String, List<Integer>> assignedNumbers;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public BingoGame getBingoGame() {
        return bingoGame;
    }

    public void setBingoGame(BingoGame bingoGame) {
        this.bingoGame = bingoGame;
    }

    public Map<String, List<Integer>> getAssignedNumbers() {
        return assignedNumbers;
    }

    public void setAssignedNumbers(Map<String, List<Integer>> assignedNumbers) {
        this.assignedNumbers = assignedNumbers;
    }
}
