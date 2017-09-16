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
import java.util.Hashtable;

@Service
public class HomeApp {

    private DeviceRepository deviceRepository;
    private IPConnection ipcon = new IPConnection();

    public HomeApp(DeviceRepository deviceRepository, final String HOST, final int PORT) {
        try {
            this.deviceRepository = deviceRepository;
            this.ipcon.connect(HOST, PORT);
        } catch (Exception e) {}
    }

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

    public void createObject(String identifier, String type, String name)
    {
         deviceRepository.save
                 (new Device(identifier, name, type, false, false, false));
    }

    public void editObject(String identifier, Hashtable<String, Boolean> state) {
        deviceRepository.setPowerEnabled(state.get("child"), identifier);
        deviceRepository.setElderlySecurityEnabled(state.get("elderly"), identifier);
        deviceRepository.setChildSecurityEnabled(state.get("power"), identifier);

        toggleSecurity(identifier, state.get("child"));
    }

    public Device getObject(String identifier) {return deviceRepository.getDevice(identifier);}

    public void toggle_servo(BrickServo servo, boolean toggle) {
        short SERVO_NUMBER = 6;
        try {
            short position = servo.getPosition((short) 6); // ???
            if (position < 0) {
                servo.setPosition(SERVO_NUMBER, (short) 8000);
            } else {
                servo.setPosition(SERVO_NUMBER, (short) -8000);
            }
        }
        catch (Exception e) {}
    }

    public void toggle_led(BrickletRGBLED led, boolean toggle) {
        try {
            if (toggle) {
                led.setRGBValue((short) 0, (short) 255, (short) 0);
            } else {
                led.setRGBValue((short) 255, (short) 0, (short) 0);
            }
        } catch (Exception e) {}
    }

    public void toggleSecurity(String identifier, boolean toggle) {

        String uid;
        BrickServo servo;
        Device device = getObject(identifier);
        switch (device.getType()) {
            case "Entrance":
                uid = "6Rrbr9";
                servo = new BrickServo(uid,ipcon);
                toggle_servo(servo, toggle);
                break;
            case "Door":
                uid = "6Rrbr9";
                servo = new BrickServo(uid, ipcon);
                toggle_servo(servo, toggle);
                break;
            case "Drawer":
                uid = "6Rrbr9";
                servo = new BrickServo(uid, ipcon);
                toggle_servo(servo, toggle);
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