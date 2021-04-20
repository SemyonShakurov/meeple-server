package com.mscorp.meepleserver;

import com.mscorp.meepleserver.models.User;
import com.mscorp.meepleserver.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping(path = "/login")
public class LoginController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping(path = "/get")
    public @ResponseBody
    User isUserRegistered(@RequestParam String nickname,
                          @RequestParam String password) throws ResponseStatusException {
        Iterable<User> users = userRepository.findAll();
        for (User user : users) {
            if (nickname.equals(user.getNickname()) && password.equals(user.getPassword()))
                return user;
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "user is not registered");
    }
}
