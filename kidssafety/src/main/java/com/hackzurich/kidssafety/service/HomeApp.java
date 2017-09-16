package com.hackzurich.kidssafety.service;

import com.hackzurich.kidssafety.model.Device;
import com.hackzurich.kidssafety.repository.DeviceRepository;
import com.hackzurich.kidssafety.tinkerforge.BrickServo;
import com.hackzurich.kidssafety.tinkerforge.BrickletRGBLED;
import com.hackzurich.kidssafety.tinkerforge.IPConnection;
import com.hopding.jrpicam.RPiCamera;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.HashMap;

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
            deviceRepository.setPowerEnabled(Boolean.parseBoolean((String) params.get("powerEnabled")), (String) params.get("id"));
            deviceRepository.setElderlySecurityEnabled(Boolean.parseBoolean((String) params.get("elderlySecurityEnabled")), (String) params.get("id"));
            deviceRepository.setChildSecurityEnabled(Boolean.parseBoolean((String) params.get("childSecurityEnabled")), (String) params.get("id"));

            toggleSecurity((String) params.get("id"), Boolean.parseBoolean((String) params.get("childSecurityEnabled")));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean deleteObject(String id) {
        try {
            deviceRepository.delete(id);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } return false;

    }


    public Device getObject(String identifier) {
        return deviceRepository.getDevice(identifier);
    }

    public IPConnection getIpcon() {
        return ipcon;
    }

    public void toggle_servo(BrickServo servo, boolean toggle) {
        short SERVO_NUMBER = 6;
        try {
            if (toggle) {
                servo.enable(SERVO_NUMBER);
                servo.setPosition(SERVO_NUMBER, (short) 8000);
            } else {
                servo.setPosition(SERVO_NUMBER, (short) -8000);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void toggle_servo_block(BrickServo servo, boolean toggle) {
        short SERVO_NUMBER = 6;
        try {
            if (toggle) {
                servo.enable(SERVO_NUMBER);
            } else {
                servo.disable(SERVO_NUMBER);
            }
        } catch (Exception e) {
            e.printStackTrace();
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
            e.printStackTrace();
        }
    }

    public void toggleSecurity(String identifier, boolean toggle) {

        String uid;
        BrickServo servo;
        Device device = getObject(identifier);

        switch (device.getType()) {
            case "entrance":
                uid = "6Rrbr9";
                servo = new BrickServo(uid, ipcon);
                toggle_servo_block(servo, toggle);
                break;
            case "door":
                uid = "6Rrbr9";
                servo = new BrickServo(uid, ipcon);
                toggle_servo_block(servo, toggle);
                break;
            case "drawer":
                uid = "6Rrbr9";
                servo = new BrickServo(uid, ipcon);
                toggle_servo(servo, toggle);
                break;
            case "stove":
                uid = "ASc";
                BrickletRGBLED rgbLedBricklet = new BrickletRGBLED(uid, ipcon);

                toggle_led(rgbLedBricklet, toggle);
                break;
            default:
                break;
        }

    }
}