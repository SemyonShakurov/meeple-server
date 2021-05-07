package com.mscorp.meepleserver;

import com.mscorp.meepleserver.models.Friends;
import com.mscorp.meepleserver.models.User;
import com.mscorp.meepleserver.repositories.UserRepository;
import com.mscorp.meepleserver.services.FileStorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping(path = "/get")
    public @ResponseBody
    User getUser(@RequestParam Integer id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "user is not exists");

        return userOptional.get();
    }

    @PutMapping(path = "/sendRequest")
    public @ResponseBody
    User sendRequest(@RequestParam Integer id,
                     @RequestParam Integer friendId) throws ResponseStatusException {
        Optional<User> userOptional = userRepository.findById(id);
        Optional<User> friendOptional = userRepository.findById(friendId);
        if (userOptional.isEmpty())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "user is not exists");
        if (friendOptional.isEmpty())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "friend id is not exists");
        User user = userOptional.get();
        User friend = friendOptional.get();

        for (Integer requestId : user.getRequestsToOthers()) {
            if (requestId.equals(friendId))
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "request is already sent");
        }

        for (Integer idFriend : user.getFriends()) {
            if (idFriend.equals(friendId))
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "already friends");
        }

        user.getRequestsToOthers().add(friendId);
        friend.getRequestsFromOthers().add(id);

        userRepository.save(user);
        userRepository.save(friend);

        return friend;
    }

    @GetMapping(path = "/getFriends")
    public @ResponseBody
    Friends getFriends(@RequestParam Integer id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "user is not exists");
        User user = userOptional.get();

        List<User> requestsFrom = new ArrayList<>();
        for (Integer requestId : user.getRequestsFromOthers())
            requestsFrom.add(userRepository.findById(requestId).get());

        List<User> requestsTo = new ArrayList<>();
        for (Integer requestId : user.getRequestsToOthers())
            requestsTo.add(userRepository.findById(requestId).get());

        List<User> friends = new ArrayList<>();
        for (Integer friend : user.getFriends())
            friends.add(userRepository.findById(friend).get());

        List<User> declined = new ArrayList<>();
        for (Integer userId : user.getDeclined())
            declined.add(userRepository.findById(userId).get());

        Friends friends1 = new Friends();
        friends1.setFriends(friends);
        friends1.setReceived(requestsFrom);
        friends1.setSent(requestsTo);
        friends1.setDeclined(declined);

        return friends1;
    }

    @PutMapping(path = "/acceptRequest")
    public @ResponseBody
    User acceptRequest(@RequestParam Integer id,
                       @RequestParam Integer friendId) {
        User user = userRepository.findById(id).get();
        User friend = userRepository.findById(friendId).get();

        if (user.getRequestsFromOthers().contains(friendId))
            user.getRequestsFromOthers().remove(friendId);
        else
            user.getDeclined().remove(friendId);

        user.getFriends().add(friendId);
        userRepository.save(user);

        friend.getRequestsToOthers().remove(id);
        friend.getFriends().add(id);
        userRepository.save(friend);
        return friend;
    }

    @PutMapping(path = "/rejectRequest")
    public @ResponseBody
    User rejectRequest(@RequestParam Integer id,
                       @RequestParam Integer friendId) {
        User user = userRepository.findById(id).get();
        User friend = userRepository.findById(friendId).get();

        user.getRequestsToOthers().remove(friendId);
        userRepository.save(user);

        if (friend.getRequestsFromOthers().contains(id))
            friend.getRequestsFromOthers().remove(id);
        else
            friend.getDeclined().remove(id);

        userRepository.save(friend);

        return friend;
    }

    @PutMapping(path = "/deleteFriend")
    public @ResponseBody
    User deleteFriend(@RequestParam Integer id,
                      @RequestParam Integer friendId) {
        User user = userRepository.findById(id).get();
        User friend = userRepository.findById(friendId).get();

        user.getFriends().remove(friendId);
        user.getDeclined().add(friendId);
        userRepository.save(user);

        friend.getFriends().remove(id);
        friend.getRequestsToOthers().add(id);
        userRepository.save(friend);

        return friend;
    }

    @PutMapping(path = "/declineRequest")
    public @ResponseBody
    User declineRequest(@RequestParam Integer id,
                        @RequestParam Integer friendId) {
        User user = userRepository.findById(id).get();

        user.getRequestsFromOthers().remove(friendId);
        user.getDeclined().add(friendId);
        userRepository.save(user);

        return userRepository.findById(friendId).get();
    }

    @GetMapping(path = "/getAll")
    public @ResponseBody
    Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    @PostMapping(path = "/uploadAvatar")
    public @ResponseBody
    User uploadAvatar(@RequestParam Integer id,
                      @RequestParam("file") MultipartFile file) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "user is not exists");
        User user = userOptional.get();

        String fileName = fileStorageService.storeFile(file);
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(fileName)
                .toUriString();

        user.setPhotoUrl(fileDownloadUri);
        userRepository.save(user);
        return user;
    }

    @GetMapping("/downloadFile/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        Resource resource = fileStorageService.loadFileAsResource(fileName);

        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            logger.info("Could not determine file type.");
        }

        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}
