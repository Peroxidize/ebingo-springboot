package com.rest.ebingospring.repositories;

import com.rest.ebingospring.entities.BingoCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BingoCardRepository extends JpaRepository<BingoCard, Long> {
    BingoCard findByToken(String token);
}
