package com.hackzurich.kidssafety.controller;

import com.hackzurich.kidssafety.model.Device;
import com.hackzurich.kidssafety.repository.DeviceRepository;
import com.hackzurich.kidssafety.service.HomeApp;
import com.hackzurich.kidssafety.tinkerforge.BrickServo;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.awt.image.BufferedImage;
import java.util.HashMap;


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
    @RequestMapping("/testservoblock")
    public void testblock(boolean toggle) {
        BrickServo servo = new BrickServo("6Rrbr9", homeApp.getIpcon());
        homeApp.toggle_servo_block(servo, toggle);
    }

    @RequestMapping("/testservo")
    public void testservo(boolean toggle) {
        BrickServo servo = new BrickServo("6Rrbr9", homeApp.getIpcon());
        homeApp.toggle_servo(servo, toggle);
    }

    // create new object
    @RequestMapping("/create")
    public boolean createObject
    (@RequestParam String id, @RequestParam String type, @RequestParam String name) {
        return homeApp.createSmartAppliance(id, type, name);
    }

    @RequestMapping("/edit")
    public boolean editObject
            (@RequestParam HashMap<String, Object> params) {
        return homeApp.editSmartAppliance(params);
    }

    // take an image of the front door
    @RequestMapping("/camera")
    public BufferedImage showImage() {
        return homeApp.getImage();
    }

    @RequestMapping("/delete")
    public boolean delete(@RequestParam String id) {
        return homeApp.deleteSmartAppliance(id);
    }

    @RequestMapping("/all")
    public Iterable<Device> getAllDevices() {
        return deviceRepository.findAll();
    }

}