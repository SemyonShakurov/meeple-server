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

    private String photoUrl;

    @JsonIgnore
    @ElementCollection
    private List<Integer> friends;

    @JsonIgnore
    @ElementCollection
    private List<Integer> requestsFromOthers;

    @JsonIgnore
    @ElementCollection
    private List<Integer> requestsToOthers;

    @JsonIgnore
    @ElementCollection
    private List<Integer> declined;

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
}
