package com.mscorp.meepleserver;

import com.mscorp.meepleserver.models.User;
import com.mscorp.meepleserver.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Optional;
import java.util.regex.Pattern;

@RestController
@RequestMapping(path = "/register")
public class RegistrationController {

    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-+]+(.[_A-Za-z0-9-]+)*@" +
            "[A-Za-z0-9-]+(.[A-Za-z0-9]+)*(.[A-Za-z]{2,})$";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JavaMailSender emailSender;

    @PostMapping(path = "/add")
    public @ResponseBody
    User addNewUser(@RequestParam String nickname,
                    @RequestParam String name,
                    @RequestParam String email,
                    @RequestParam String password) throws ResponseStatusException {
        if (!Pattern.compile(EMAIL_PATTERN).matcher(email).matches())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "incorrect email");
        User user = new User();
        user.setNickname(nickname);
        user.setPassword(password);
        user.setEmail(email);
        user.setFriends(new ArrayList<>());
        user.setName(name);
        user.setRequestsFromOthers(new ArrayList<>());
        user.setRequestsToOthers(new ArrayList<>());
        user.setGames(new ArrayList<>());
        user.setEvents(new ArrayList<>());
        user.setPhotoUrl("https://image.flaticon.com/icons/png/512/168/168726.png");
        checkUser(user);
        confirmRegistration(user);
        userRepository.save(user);
        return user;
    }

    @PutMapping(path = "/confirmEmail")
    public @ResponseBody
    User confirmEmail(@RequestParam String email,
                      @RequestParam Integer code) {
        Iterable<User> users = userRepository.findAll();
        User user = null;
        for (User userData : users) {
            if (userData.getEmail().equals(email)) {
                user = userData;
                break;
            }
        }

        if (user == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "user is not exists");

        if (!user.getCode().equals(code))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "the code does not match");

        user.setEnabled(true);
        user.setCode(0);
        userRepository.save(user);
        return user;
    }

    @PutMapping(path = "/sendCode")
    public @ResponseBody
    User sendCode(@RequestParam String nickname) {
        Iterable<User> users = userRepository.findAll();
        User user = null;
        for (User userData : users) {
            if (userData.getNickname().equals(nickname)) {
                user = userData;
                break;
            }
        }

        if (user == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "nickname is not exists");
        if (!user.getEnabled())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "user did not confirm mail");

        user.setEnabled(false);
        confirmRegistration(user);
        userRepository.save(user);
        return user;
    }

    @PutMapping(path = "/resetPassword")
    public @ResponseBody
    User resetPassword(@RequestParam Integer id,
                       @RequestParam String password) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "user is not exists");
        User user = userOptional.get();

        user.setPassword(password);
        userRepository.save(user);
        return user;
    }

    private void confirmRegistration(User user) {
        Integer code = (int) (1000 + 9000 * Math.random());
        user.setCode(code);

        String subject = "?????????????????????????? ??????????";
        String text = "?????????? ???????????????????? ?? Meeple! ?????? ?????????????????????????? ?????????? ?????????????? ??????:\n" +
                code + "\n???????? ???? ???? ???????????????????????????????? ?? ????????????????????, ???????????????????????????? ???????????? ??????????????????.";

        sendSimpleMessage(user.getEmail(), subject, text);
    }

    public void sendSimpleMessage(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("noreply@meeple.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
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
