package com.mscorp.meepleserver.models;

import java.util.List;

public class EventObject {

    private Integer Id;

    private String title;

    private String count;

    private List<Integer> games;

    private Integer playersLevel;

    private String info;

    private Long date;

    private List<User> members;

    private Double lat;

    private Double lng;

    private Integer creatorId;

    public void setId(Integer id) {
        Id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setGames(List<Integer> games) {
        this.games = games;
    }

    public void setPlayersLevel(Integer playersLevel) {
        this.playersLevel = playersLevel;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public void setMembers(List<User> members) {
        this.members = members;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public Integer getId() {
        return Id;
    }

    public List<Integer> getGames() {
        return games;
    }

    public String getTitle() {
        return title;
    }

    public String getCount() {
        return count;
    }

    public String getInfo() {
        return info;
    }

    public Integer getPlayersLevel() {
        return playersLevel;
    }

    public List<User> getMembers() {
        return members;
    }

    public Integer getCreatorId() {
        return creatorId;
    }

    public Double getLat() {
        return lat;
    }

    public Long getDate() {
        return date;
    }

    public Double getLng() {
        return lng;
    }
}
