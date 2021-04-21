package com.mscorp.meepleserver;

import com.mscorp.meepleserver.models.User;
import com.mscorp.meepleserver.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;

@RestController
@RequestMapping(path = "/register")
public class RegistrationController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping(path = "/add")
    public @ResponseBody
    User addNewUser(@RequestParam String nickname,
                    @RequestParam String name,
                    @RequestParam String email,
                    @RequestParam String password) throws ResponseStatusException {
        User user = new User();
        user.setNickname(nickname);
        user.setPassword(password);
        user.setEmail(email);
        user.setFriends(new ArrayList<>());
        user.setName(name);
        user.setRequestsFromOthers(new ArrayList<>());
        user.setRequestsToOthers(new ArrayList<>());
        user.setPhotoUrl("https://image.flaticon.com/icons/png/512/168/168726.png");
        checkUser(user);
        userRepository.save(user);
        return user;
    }

    private void checkUser(User newUser) {
        Iterable<User> users = userRepository.findAll();
        for (User user : users) {
            if (newUser.getNickname().equals(user.getNickname()))
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This nickname exists");
            if (newUser.getEmail().equals(user.getEmail()))
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This email exists");
        }
    }
}
