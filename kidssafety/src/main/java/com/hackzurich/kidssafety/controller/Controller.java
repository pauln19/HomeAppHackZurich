package com.hackzurich.kidssafety.controller;

import com.hackzurich.kidssafety.model.Device;
import com.hackzurich.kidssafety.repository.DeviceRepository;
import com.hackzurich.kidssafety.service.HomeApp;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Hashtable;


@RestController
@CrossOrigin
public class Controller {

    private DeviceRepository deviceRepository;
    private HomeApp homeApp;

    public Controller(HomeApp homeApp, DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
        this.homeApp = new HomeApp(deviceRepository);
    }

    // create new object
    @RequestMapping("/create")
    public Iterable<Device> createObject
    (@RequestParam String identifier, @RequestParam String type, @RequestParam String name) {
        homeApp.createObject(identifier, type, name);
        return deviceRepository.findAll();
    }

    @RequestMapping("/edit")
    public Iterable<Device> editObject
            (@RequestParam HashMap<String, Object> params) {
        homeApp.editObject(params);
        return deviceRepository.findAll();
    }

    // take an image of the front door
    @RequestMapping("/camera")
    public BufferedImage showImage() {
        return homeApp.getImage();
    }


}