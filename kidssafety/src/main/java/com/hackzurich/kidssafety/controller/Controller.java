package com.hackzurich.kidssafety.controller;

import com.hackzurich.kidssafety.model.Device;
import com.hackzurich.kidssafety.repository.DeviceRepository;
import com.hackzurich.kidssafety.service.HomeApp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.awt.image.BufferedImage;

import javax.xml.bind.annotation.XmlRegistry;

@RestController
@CrossOrigin
public class Controller {

    private HomeApp homeApp;
    private DeviceRepository deviceRepository;

    public Controller(HomeApp homeApp, DeviceRepository deviceRepository) {
        this.homeApp = homeApp;
        this.deviceRepository = deviceRepository;
    }

    @RequestMapping("/home/device")
    public int updateDevice(@RequestParam identifier) {

        // 2 = child present
        // 1 = no child present
        // 0 = no connection
        return homeApp.getObjectState(identifier);
    }

    @RequestMapping("/home")
    public Iterable<Device> home() {
        homeApp.createObject("lksfs", "fridge", "Daniel");

        return deviceRepository.findAll();
    }

    // create new object
    @RequestMapping("/create")
    public boolean createObject
    (@RequestParam String identifier, @RequestParam String type, @RequestParam String name) {
        homeApp.createObject(identifier, type, name);
        return true;
    }

    @RequestMapping("/edit")
    public boolean editObject
            (@RequestParam String identifier, @RequestParam boolean child_security) {
        homeApp.editObject(identifier, child_security);
    }

    // take an image of the front door
    @RequestMapping("/camera")
    public BufferedImage show_image() {
        BufferedImage image = homeApp.getImage();
        return image;
    }



}