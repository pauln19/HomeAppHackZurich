package com.hackzurich.kidssafety.controller;

import com.hackzurich.kidssafety.repository.DeviceRepository;
import com.hackzurich.kidssafety.service.HomeApp;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.awt.image.BufferedImage;
import java.util.Hashtable;


@RestController
@CrossOrigin
public class Controller {
    final String HOST = "172.31.0.225";
    final int PORT = 4223;

    private DeviceRepository deviceRepository;
    private HomeApp homeApp = new HomeApp(deviceRepository, HOST, PORT);

    public Controller(HomeApp homeApp, DeviceRepository deviceRepository) {
        this.homeApp = homeApp;
        this.deviceRepository = deviceRepository;
    }

    // create new object
    @RequestMapping("/create")
    public void createObject
    (@RequestParam String identifier, @RequestParam String type, @RequestParam String name) {
        homeApp.createObject(identifier, type, name);
    }

    @RequestMapping("/edit")
    public void editObject
            (@RequestParam String identifier, @RequestParam Hashtable<String, Boolean> state) {
        homeApp.editObject(identifier, state);
    }

    // take an image of the front door
    @RequestMapping("/camera")
    public BufferedImage show_image() {
        BufferedImage image = homeApp.getImage();
        return image;
    }



}