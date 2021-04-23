package com.mscorp.meepleserver.models;

import javax.persistence.*;
import java.util.List;

@Entity
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer Id;

    private String title;

    private Integer count;

    @ElementCollection
    private List<Integer> games;

    private Integer playersLevel;

    private Integer type;

    private String info;

    private Integer date;

    private Integer access;

    @ElementCollection
    private List<Integer> members;

    private Integer creatorId;

    public void setId(Integer id) {
        Id = id;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public void setAccess(Integer access) {
        this.access = access;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public void setDate(Integer date) {
        this.date = date;
    }

    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
    }

    public void setGames(List<Integer> games) {
        this.games = games;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public void setMembers(List<Integer> members) {
        this.members = members;
    }

    public void setPlayersLevel(Integer playersLevel) {
        this.playersLevel = playersLevel;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getId() {
        return Id;
    }

    public Integer getCount() {
        return count;
    }

    public Integer getAccess() {
        return access;
    }

    public Integer getCreatorId() {
        return creatorId;
    }

    public Integer getDate() {
        return date;
    }

    public Integer getPlayersLevel() {
        return playersLevel;
    }

    public Integer getType() {
        return type;
    }

    public List<Integer> getGames() {
        return games;
    }

    public List<Integer> getMembers() {
        return members;
    }

    public String getInfo() {
        return info;
    }

    public String getTitle() {
        return title;
    }
}
