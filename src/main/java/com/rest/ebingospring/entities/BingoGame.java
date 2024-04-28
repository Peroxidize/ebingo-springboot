package com.rest.ebingospring.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "bingo_game")
public class BingoGame {
    @Id
    @Column(name = "game_code")
    private String gameCode;

    @ElementCollection
    @Column(name = "numbers_rolled")
    private List<Integer> numbersRolled;

    @OneToMany
    private List<BingoCard> bingoCards;

    public String getGameCode() {
        return gameCode;
    }

    public void setGameCode(String gameCode) {
        this.gameCode = gameCode;
    }

    public List<Integer> getNumbersRolled() {
        return numbersRolled;
    }

    public void setNumbersRolled(List<Integer> numbersRolled) {
        this.numbersRolled = numbersRolled;
    }

    public List<BingoCard> getBingoCards() {
        return bingoCards;
    }

    public void setBingoCards(List<BingoCard> bingoCards) {
        this.bingoCards = bingoCards;
    }
}
