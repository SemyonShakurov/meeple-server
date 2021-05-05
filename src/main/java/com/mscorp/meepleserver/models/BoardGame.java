package com.mscorp.meepleserver.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class BoardGame {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String name;

    private String type;

    private Integer countPlayer;

    private Integer time;

    private String description;

    private String pic;

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setCountPlayer(Integer countPlayer) {
        this.countPlayer = countPlayer;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public Integer getCountPlayer() {
        return countPlayer;
    }

    public Integer getTime() {
        return time;
    }

    public String getPic() {
        return pic;
    }
}
