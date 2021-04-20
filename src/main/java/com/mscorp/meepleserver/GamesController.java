package com.mscorp.meepleserver;

import com.mscorp.meepleserver.models.BoardGame;
import com.mscorp.meepleserver.repositories.BoardGameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping(path = "/games")
public class GamesController {

    @Autowired
    private BoardGameRepository boardGameRepository;

    @PostMapping(path = "/add")
    public @ResponseBody
    BoardGame addBoardGame(@RequestParam String name,
                           @RequestParam String type,
                           @RequestParam Integer countOfPlayers,
                           @RequestParam Integer duration,
                           @RequestParam String description) throws ResponseStatusException {
        BoardGame game = new BoardGame();
        game.setDescription(description);
        game.setDuration(duration);
        game.setCountOfPlayers(countOfPlayers);
        game.setType(type);
        game.setName(name);
        checkBoardGame(game);
        boardGameRepository.save(game);
        return game;
    }

    private void checkBoardGame(BoardGame newBoardGame) {
        Iterable<BoardGame> games = boardGameRepository.findAll();
        for (BoardGame game : games) {
            if (game.getName().equals(newBoardGame.getName()))
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "this game exists");
        }
    }

    @GetMapping(path = "/getAll")
    public @ResponseBody
    Iterable<BoardGame> getAllGames() {
        return boardGameRepository.findAll();
    }
}
