package com.mscorp.meepleserver;

import com.mscorp.meepleserver.models.Event;
import com.mscorp.meepleserver.repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/events")
public class EventController {

    @Autowired
    private EventRepository eventRepository;

    @PostMapping(path = "/addEvent")
    public @ResponseBody
    Event addEvent(@RequestParam String title,
                   @RequestParam Integer count,
                   @RequestParam List<Integer> games,
                   @RequestParam Integer playersLevel,
                   @RequestParam Integer type,
                   @RequestParam String info,
                   @RequestParam Integer date,
                   @RequestParam Integer access,
                   @RequestParam List<Integer> members,
                   @RequestParam Integer creatorId) {
        Event event = new Event();
        event.setCount(count);
        event.setAccess(access);
        event.setCreatorId(creatorId);
        event.setDate(date);
        event.setGames(games);
        event.setTitle(title);
        event.setPlayersLevel(playersLevel);
        event.setType(type);
        event.setInfo(info);
        event.setMembers(members);
        eventRepository.save(event);
        return event;
    }

    @GetMapping(path = "getAll")
    public @ResponseBody
    Iterable<Event> getEvents() {
        return eventRepository.findAll();
    }
}
