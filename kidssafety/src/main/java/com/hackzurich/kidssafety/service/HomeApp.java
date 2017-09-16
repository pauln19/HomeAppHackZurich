package com.hackzurich.kidssafety.service;

import com.hopding.jrpicam.RPiCamera;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class HomeApp {

    public HomeApp() {}


    public String hello_world() {return "Helsdfsdfsdlo World";}

    public BufferedImage get_image() {
        try {
            RPiCamera piCamera = new RPiCamera("/home/pi/Pictures");
            BufferedImage image = ImageIO.read(piCamera.takeStill("pic.jpg"));
            return image;
        } catch (Exception e) {
            System.out.println("An error has occurred while taking a picture.");
        }
        return null;
    }
}
