package com.hackzurich.kidssafety.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Device {
    @Id
    private String id;

    private String name;

    private String type;
    private boolean state;
    private boolean childSecurityEnabled;

    public Device(String id, String name, String type, boolean state, boolean childSecurityEnabled, boolean elderlySecurityEnabled) {
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

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public boolean isChildSecurityEnabled() {
        return childSecurityEnabled;
    }

    public void setChildSecurityEnabled(boolean childSecurityEnabled) {
        this.childSecurityEnabled = childSecurityEnabled;
    }
}
