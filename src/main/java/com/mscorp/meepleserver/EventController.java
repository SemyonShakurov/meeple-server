package com.mscorp.meepleserver;

import com.mscorp.meepleserver.models.Event;
import com.mscorp.meepleserver.models.EventObject;
import com.mscorp.meepleserver.models.User;
import com.mscorp.meepleserver.repositories.EventRepository;
import com.mscorp.meepleserver.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/events")
public class EventController {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping(path = "/addEvent")
    public @ResponseBody
    EventObject addEvent(@RequestParam String title,
                   @RequestParam String count,
                   @RequestParam List<Integer> games,
                   @RequestParam Integer playersLevel,
                   @RequestParam String info,
                   @RequestParam Long date,
                   @RequestParam List<Integer> members,
                   @RequestParam Double lat,
                   @RequestParam Double lng,
                   @RequestParam Integer creatorId) {
        Event event = new Event();
        event.setTitle(title);
        event.setCount(count);
        event.setGames(games);
        event.setPlayersLevel(playersLevel);
        event.setInfo(info);
        event.setDate(date);
        event.setMembers(members);
        event.setLat(lat);
        event.setLng(lng);
        event.setCreatorId(creatorId);
        eventRepository.save(event);
        User creator = userRepository.findById(creatorId).get();
        creator.getEvents().add(event.getId());
        userRepository.save(creator);
        for (Integer userId : members) {
            User user = userRepository.findById(userId).get();
            user.getEvents().add(event.getId());
            userRepository.save(user);
        }
        return parseToJsonObj(event);
    }

    @PutMapping(path = "/subscribeToEvent")
    public @ResponseBody
    EventObject signToEvent(@RequestParam Integer eventId,
                            @RequestParam Integer userId) {
        Event event = eventRepository.findById(eventId).get();
        event.getMembers().add(userId);
        eventRepository.save(event);
        User user = userRepository.findById(userId).get();
        user.getEvents().add(eventId);
        userRepository.save(user);
        return parseToJsonObj(event);
    }

    @PutMapping(path = "/unsubscribeFromEvent")
    public @ResponseBody
    EventObject unsubscribeFromEvent(@RequestParam Integer eventId,
                                     @RequestParam Integer userId) {
        Event event = eventRepository.findById(eventId).get();
        event.getMembers().remove(userId);
        eventRepository.save(event);
        User user = userRepository.findById(userId).get();
        user.getEvents().remove(eventId);
        userRepository.save(user);
        return parseToJsonObj(event);
    }

    @GetMapping(path = "/getAll")
    public @ResponseBody
    Iterable<EventObject> getEvents() {
        Iterable<Event> events = eventRepository.findAll();
        List<EventObject> eventObjects = new ArrayList<>();
        for (Event event : events) {
            eventObjects.add(parseToJsonObj(event));
        }
        return eventObjects;
    }

    private EventObject parseToJsonObj(Event event) {
        EventObject eventObject = new EventObject();
        eventObject.setId(event.getId());
        eventObject.setTitle(event.getTitle());
        eventObject.setCount(event.getCount());
        eventObject.setGames(event.getGames());
        eventObject.setPlayersLevel(event.getPlayersLevel());
        eventObject.setInfo(event.getInfo());
        eventObject.setDate(event.getDate());
        eventObject.setLat(event.getLat());
        eventObject.setLng(event.getLng());
        eventObject.setCreatorId(event.getCreatorId());
        List<User> members = new ArrayList<>();
        for (Integer id : event.getMembers()) {
            User user = userRepository.findById(id).get();
            members.add(user);
        }
        eventObject.setMembers(members);
        return eventObject;
    }
}
