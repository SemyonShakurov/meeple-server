package com.mscorp.meepleserver;

import com.mscorp.meepleserver.models.EmailServiceImpl;
import com.mscorp.meepleserver.models.User;
import com.mscorp.meepleserver.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.regex.Pattern;

@RestController
@RequestMapping(path = "/register")
public class RegistrationController {

    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-+]+(.[_A-Za-z0-9-]+)*@" +
            "[A-Za-z0-9-]+(.[A-Za-z0-9]+)*(.[A-Za-z]{2,})$";

    @Autowired
    private UserRepository userRepository;

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
        user.setPhotoUrl("https://image.flaticon.com/icons/png/512/168/168726.png");
        checkUser(user);
        confirmRegistration(user);
        userRepository.save(user);
        return user;
    }
    
    private void confirmRegistration(User user) {
        Integer code = (int) (1000 + 9000 * Math.random());
        user.setCode(code);

        String subject = "Подтверждение почты";
        String text = "Добро пожаловать в Meeple! Для подтверждения почты введите код:\n" +
                code + "\nЕсли вы не регистрировались в приложении, проигнорируйте данное сообщение.";

        EmailServiceImpl emailSender = new EmailServiceImpl();
        emailSender.sendSimpleMessage(user.getEmail(), subject, text);
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
