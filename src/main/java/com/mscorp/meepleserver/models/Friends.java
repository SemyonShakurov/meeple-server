package com.mscorp.meepleserver.models;

import java.util.List;

public class Friends {

    private List<User> friends;

    private List<User> sent;

    private List<User> received;

    private List<User> declined;

    public void setFriends(List<User> friends) {
        this.friends = friends;
    }

    public void setReceived(List<User> received) {
        this.received = received;
    }

    public void setSent(List<User> sent) {
        this.sent = sent;
    }

    public List<User> getFriends() {
        return friends;
    }

    public List<User> getReceived() {
        return received;
    }

    public List<User> getSent() {
        return sent;
    }

    public void setDeclined(List<User> declined) {
        this.declined = declined;
    }

    public List<User> getDeclined() {
        return declined;
    }
}
