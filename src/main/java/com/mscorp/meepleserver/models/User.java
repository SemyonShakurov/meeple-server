package com.mscorp.meepleserver.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String nickname;

    private String name;

    @JsonIgnore
    private String password;

    private String email;

    private Boolean enabled;

    @JsonIgnore
    private Integer code;

    private String photoUrl;

    @JsonIgnore
    @ElementCollection
    private List<Integer> friends;

    @JsonIgnore
    @ElementCollection
    private List<Integer> requestsFromOthers;

    @ElementCollection
    private List<Integer> games;

    @JsonIgnore
    @ElementCollection
    private List<Integer> requestsToOthers;

    @JsonIgnore
    @ElementCollection
    private List<Integer> declined;

    @ElementCollection
    private List<Integer> events;

    public User() {
        super();
        this.enabled = false;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public List<Integer> getFriends() {
        return friends;
    }

    public void setFriends(List<Integer> friends) {
        this.friends = friends;
    }

    public List<Integer> getRequestsFromOthers() {
        return requestsFromOthers;
    }

    public void setRequestsFromOthers(List<Integer> requestsFromOthers) {
        this.requestsFromOthers = requestsFromOthers;
    }

    public List<Integer> getRequestsToOthers() {
        return requestsToOthers;
    }

    public void setRequestsToOthers(List<Integer> requestsToOthers) {
        this.requestsToOthers = requestsToOthers;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setDeclined(List<Integer> declined) {
        this.declined = declined;
    }

    public List<Integer> getDeclined() {
        return declined;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public List<Integer> getGames() {
        return games;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public void setGames(List<Integer> games) {
        this.games = games;
    }

    public void setEvents(List<Integer> events) {
        this.events = events;
    }

    public List<Integer> getEvents() {
        return events;
    }
}
