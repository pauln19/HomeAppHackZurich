package com.hackzurich.kidssafety.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Device {
    public Device(String id, String name, String type, boolean state, boolean childSecurityEnabled, boolean elderlySecurityEnabled) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.state = state;
        this.childSecurityEnabled = childSecurityEnabled;
        this.elderlySecurityEnabled = elderlySecurityEnabled;
    }

    @Id
    private String id;
    private String name;
    private String type;
    private boolean state;

    private boolean childSecurityEnabled;
    public String getType() {
        return type;
    }
    private boolean elderlySecurityEnabled;
}