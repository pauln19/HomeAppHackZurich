package com.hackzurich.kidssafety.service;

import com.hackzurich.kidssafety.model.Device;
import com.hackzurich.kidssafety.repository.DeviceRepository;
import com.hackzurich.kidssafety.tinkerforge.BrickServo;
import com.hackzurich.kidssafety.tinkerforge.BrickletRGBLED;
import com.hackzurich.kidssafety.tinkerforge.IPConnection;
import com.hopding.jrpicam.RPiCamera;
import com.sun.org.apache.xpath.internal.operations.Bool;
import javafx.beans.binding.ObjectExpression;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Hashtable;

@Service
public class HomeApp {

    private DeviceRepository deviceRepository;
    private IPConnection ipcon;
    private final static String HOST = "172.31.0.191";
    private final static int PORT = 4223;

    public HomeApp() {
    }

    public HomeApp(DeviceRepository deviceRepository) {

        try {
            this.deviceRepository = deviceRepository;
            this.ipcon = new IPConnection();
            this.ipcon.connect(HOST, PORT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public BufferedImage getImage() {
        try {
            RPiCamera piCamera = new RPiCamera("/home/pi/Pictures");
            return ImageIO.read(piCamera.takeStill("pic.jpg"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void createObject(String identifier, String type, String name) {
        deviceRepository.save
                (new Device(identifier, name, type, false, false, false));
    }

    public void editObject(HashMap<String, Object> params) {
        try {
            deviceRepository.setPowerEnabled(Boolean.parseBoolean((String) params.get("powerEnabled")), (String) params.get("identifier"));
            deviceRepository.setElderlySecurityEnabled(Boolean.parseBoolean((String) params.get("elderlySecurityEnabled")), (String) params.get("identifier"));
            deviceRepository.setChildSecurityEnabled(Boolean.parseBoolean((String) params.get("childSecurityEnabled")), (String) params.get("identifier"));

            toggleSecurity((String) params.get("identifier"), false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Device getObject(String identifier) {
        return deviceRepository.getDevice(identifier);
    }

    public void toggle_servo(BrickServo servo, boolean toggle) {
        short SERVO_NUMBER = 6;
        try {
            if (toggle) {
            short position = servo.getPosition((short) 6); // ???
                servo.setPosition(SERVO_NUMBER, (short) 8000);
            } else {
                servo.setPosition(SERVO_NUMBER, (short) -8000);
            }
        } catch (Exception e) {
        }
    }

    public void toggle_led(BrickletRGBLED led, boolean toggle) {
        try {
            if (toggle) {
                led.setRGBValue((short) 0, (short) 255, (short) 0);
            } else {
                led.setRGBValue((short) 255, (short) 0, (short) 0);
            }
        } catch (Exception e) {
        }
    }

    public void toggleSecurity(String identifier, boolean toggle) {

        String uid;
        BrickServo servo;
        Device device = getObject(identifier);
        switch (device.getType()) {
            case "Entrance":
                uid = "6Rrbr9";
                servo = new BrickServo(uid, ipcon);
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
                uid = "ASc";
                BrickletRGBLED rgbLedBricklet = new BrickletRGBLED(uid, ipcon);
                toggle_led(rgbLedBricklet, toggle);
                break;
            default:
                break;
        }

    }
}