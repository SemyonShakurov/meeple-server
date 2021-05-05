package com.mscorp.meepleserver;

import com.mscorp.meepleserver.models.BoardGame;
import com.mscorp.meepleserver.models.User;
import com.mscorp.meepleserver.repositories.BoardGameRepository;
import com.mscorp.meepleserver.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping(path = "/games")
public class GamesController {

    @Autowired
    private BoardGameRepository boardGameRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping(path = "/add")
    public @ResponseBody
    BoardGame addBoardGame(@RequestParam String name,
                           @RequestParam String type,
                           @RequestParam String countPlayer,
                           @RequestParam Integer time,
                           @RequestParam String description,
                           @RequestParam String pic) throws ResponseStatusException {
        BoardGame game = new BoardGame();
        game.setDescription(description);
        game.setTime(time);
        game.setCountPlayer(countPlayer);
        game.setType(type);
        game.setName(name);
        game.setPic(pic);
        checkBoardGame(game);
        boardGameRepository.save(game);
        return game;
    }

    @PutMapping(path = "/addGame")
    public void addGame(@RequestParam Integer userId,
                        @RequestParam Integer gameId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "user is not exists");
        User user = userOptional.get();

        Optional<BoardGame> gameOptional = boardGameRepository.findById(gameId);
        if (gameOptional.isEmpty())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "game is not exists");

        user.getGames().add(gameId);
        userRepository.save(user);
    }

    @PutMapping(path = "/removeGame")
    public void removeGame(@RequestParam Integer userId,
                           @RequestParam Integer gameId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "user is not exists");
        User user = userOptional.get();

        Optional<BoardGame> gameOptional = boardGameRepository.findById(gameId);
        if (gameOptional.isEmpty())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "game is not exists");

        user.getGames().remove(gameId);
        userRepository.save(user);
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
