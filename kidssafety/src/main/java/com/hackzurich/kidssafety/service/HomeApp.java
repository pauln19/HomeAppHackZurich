package com.hackzurich.kidssafety.service;

import com.hackzurich.kidssafety.model.Device;
import com.hackzurich.kidssafety.repository.DeviceRepository;
import com.hopding.jrpicam.RPiCamera;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

@Service
public class HomeApp {

    private DeviceRepository deviceRepository;

    public HomeApp(DeviceRepository deviceRepository) {this.deviceRepository = deviceRepository;}

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

    public void createObject(String identifier, String type, String name) {
         deviceRepository.save
                 (new Device(identifier, name, type, false, false, false));
    }

    public void editObject(String identifier, boolean child_security) {
        deviceRepository.setChildSecurityEnabled(child_security, identifier);
    }


}
