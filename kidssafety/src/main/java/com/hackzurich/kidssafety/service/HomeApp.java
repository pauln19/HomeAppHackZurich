package com.hackzurich.kidssafety.service;

import com.hackzurich.kidssafety.model.Device;
import com.hackzurich.kidssafety.repository.DeviceRepository;
import com.hackzurich.kidssafety.tinkerforge.IPConnection;

import com.hackzurich.kidssafety.tinkerforge.BrickServo;
import com.hackzurich.kidssafety.tinkerforge.BrickletRGBLED;
import com.hopding.jrpicam.RPiCamera;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

@Service
public class HomeApp {

    private DeviceRepository deviceRepository;

    public HomeApp(DeviceRepository deviceRepository) {this.deviceRepository = deviceRepository;}

    public BufferedImage getImage() {
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
                 (new Device(identifier, name, type, 1, false, false));
    }

    public void editObject(String identifier, boolean child_security) {
        deviceRepository.setChildSecurityEnabled(child_security, identifier);
        toggleSecurity(identifier, child_security);
    }

    public Device getObject(String identifier) {return deviceRepository.getDevice(identifier);}

    public void toggle_servo(BrickServo servo, boolean toggle) {
        short position = servo.getPosition(6); // ???
        if (position < 0) {
            servo.setPosition((short) 6, (short) 8000);
        } else {
            servo.setPosition((short) 6, (short) -8000);
        }
    }

    public void toggle_led(BrickletRGBLED led, boolean toggle) {
        if (toggle) {
            led.setRGBValue(0, 255, 0);
        } else {
            led.setRGBValue(255, 0, 0);
        }
    }

    public void toggleSecurity(String identifier, boolean toggle) {

        Device device = getObject(identifier);
        final String HOST = "172.31.0.225";
        final int PORT = 4223;
        IPConnection ipcon = new IPConnection();

        String uid;

        ipcon.connect(HOST, PORT);

        switch (device.getType()) {
            case "Entrance":
                uid = "6Rrbr9";
                BrickServo servoBricklet = new BrickServo(uid,ipcon);
                toggle_servo(servoBricklet, toggle);
                break;
            case "Door":
                uid = "6Rrbr9";
                BrickServo servoBricklet = new BrickServo(uid, ipcon);
                toggle_servo(servoBricklet, toggle);
                break;
            case "Drawer":
                uid = "6Rrbr9";
                BrickServo servoBricklet = new BrickServo(uid, ipcon);
                toggle_servo(servoBricklet, toggle);
                break;
            case "Stove":
                uid = "abc";
                BrickletRGBLED rgbLedBricklet = new BrickletRGBLED(uid, ipcon);
                toggle_led(rgbLedBricklet, toggle);
                break;
            default:

                break;
        }

    }
}