package com.mscorp.meepleserver.repositories;

import com.mscorp.meepleserver.models.BoardGame;
import org.springframework.data.repository.CrudRepository;

public interface BoardGameRepository extends CrudRepository<BoardGame, Integer> {
}
