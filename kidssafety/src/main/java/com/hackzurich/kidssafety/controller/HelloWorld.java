package com.hackzurich.kidssafety.controller;

import com.hackzurich.kidssafety.service.HomeApp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.bind.annotation.XmlRegistry;

@RestController
@CrossOrigin
public class HelloWorld {

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

    @RequestMapping("/camera")
    public BufferedImage get_image() {
        homeApp.take_image();
        BufferedImage image = homeApp.get_image();
        return image;
    }


}