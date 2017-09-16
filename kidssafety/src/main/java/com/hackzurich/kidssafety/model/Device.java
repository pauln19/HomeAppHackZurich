package com.hackzurich.kidssafety.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Device {
    @Id
    private String id;

    private String name;

    private String type;
    private int state;
    private boolean childSecurityEnabled;

    public Device(String id, String name, String type, int state, boolean childSecurityEnabled, boolean elderlySecurityEnabled) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.state = state;
        this.childSecurityEnabled = childSecurityEnabled;
    }

    public Device() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int isState() {
        return state;
    }

    @Override
    public String toString() {
        return "Device{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", state=" + state +
                ", childSecurityEnabled=" + childSecurityEnabled +
                '}';
    }

    public void setState(int state) {
        this.state = state;
    }

    public boolean isChildSecurityEnabled() {
        return childSecurityEnabled;
    }

    public void setChildSecurityEnabled(boolean childSecurityEnabled) {
        this.childSecurityEnabled = childSecurityEnabled;
    }
}
