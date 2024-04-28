package com.rest.ebingospring.repositories;

import com.rest.ebingospring.entities.BingoGame;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BingoGameRepository extends JpaRepository<BingoGame, Long> {
    BingoGame findByGameCode(String gameCode);
}
