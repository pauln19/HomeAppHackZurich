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

    public boolean createSmartAppliance(String id, String type, String name) {
        try {
            deviceRepository.save
                    (new Device(id, name, type, false, false, false));
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public boolean editSmartAppliance(HashMap<String, Object> params) {
        try {
            Device device = deviceRepository.getDevice((String) params.get("id"));
            device.setChildSecurityEnabled(Boolean.parseBoolean((String) params.get("childSecurityEnabled")));
            device.setElderlySecurityEnabled((Boolean.parseBoolean((String) params.get("elderlySecurityEnabled"))));
            device.setPowerEnabled(Boolean.parseBoolean((String) params.get("powerEnabled")));
            toggleChildSecurity(device, Boolean.parseBoolean((String) params.get("childSecurityEnabled")));
            deviceRepository.save(device);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteSmartAppliance(String id) {
        try {
            deviceRepository.delete(id);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;

    }


    public Device getSmartAppliance(String id) {
        return deviceRepository.getDevice(id);
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

    public void toggleChildSecurity(Device device, boolean toggle) {

        String uid;
        BrickServo servo;
        try {
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
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}