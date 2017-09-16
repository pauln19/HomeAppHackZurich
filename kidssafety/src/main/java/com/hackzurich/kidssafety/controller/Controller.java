package com.hackzurich.kidssafety.controller;

import com.hackzurich.kidssafety.service.HomeApp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.awt.image.BufferedImage;

import javax.xml.bind.annotation.XmlRegistry;

@RestController
@CrossOrigin
public class Controller {

    @Autowired
    HomeApp homeApp;

    @RequestMapping("/")
    public String toggle(@RequestParam boolean A) {
        String a = homeApp.hello_world();
        return a;
    }

    @RequestMapping("/home")
    public String home() {
        return "I am at home!";
    }

    // take an image of the front door
    @RequestMapping("/camera")
    public BufferedImage show_image() {
        BufferedImage image = homeApp.get_image();
        return image;
    }

}