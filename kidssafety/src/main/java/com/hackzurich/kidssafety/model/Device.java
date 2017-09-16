package com.hackzurich.kidssafety.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Device {

    public Device(String id, String name, String type, boolean powerEnabled, boolean childSecurityEnabled, boolean elderlySecurityEnabled) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.powerEnabled = powerEnabled;
        this.childSecurityEnabled = childSecurityEnabled;
        this.elderlySecurityEnabled = elderlySecurityEnabled;
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

    public boolean isPowerEnabled() {
        return powerEnabled;
    }

    public void setPowerEnabled(boolean powerEnabled) {
        this.powerEnabled = powerEnabled;
    }

    public boolean isChildSecurityEnabled() {
        return childSecurityEnabled;
    }

    public void setChildSecurityEnabled(boolean childSecurityEnabled) {
        this.childSecurityEnabled = childSecurityEnabled;
    }

    public boolean isElderlySecurityEnabled() {
        return elderlySecurityEnabled;
    }

    public void setElderlySecurityEnabled(boolean elderlySecurityEnabled) {
        this.elderlySecurityEnabled = elderlySecurityEnabled;
    }

    @Id

    private String id;
    private String name;
    private String type;
    private boolean powerEnabled;
    private boolean childSecurityEnabled;
    private boolean elderlySecurityEnabled;

}