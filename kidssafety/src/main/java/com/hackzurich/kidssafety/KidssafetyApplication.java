package com.hackzurich.kidssafety;

import com.hackzurich.kidssafety.tinkerforge.BrickletRGBLED;
import com.hackzurich.kidssafety.tinkerforge.IPConnection;
import org.apache.tomcat.jni.Time;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class KidssafetyApplication {

    private static final String HOST = "localhost";
    private static final int PORT = 4223;

    private static final String RGB_LED_UID = "ASc";

    private static BrickletRGBLED brickletRGBLED;
    private static int colorNum1 = 0;
    private static int colorNum2 = 0;
    private static int colorNum3 = 0;

    public static void main(String[] args) {

        try {
            IPConnection ipConnection = new IPConnection();
            brickletRGBLED = new BrickletRGBLED(RGB_LED_UID, ipConnection);
            ipConnection.connect(HOST, PORT);
        } catch (Exception e) {
        }

            colorNum1 = (colorNum2 + colorNum3 + 10) % 256;
            colorNum2 = (colorNum1 + colorNum2 + 20) % 256;
            try {

                toggleRgbLedColor(colorNum1, colorNum2, colorNum3);
                Thread.sleep(5000);
                toggleRgbLedColor(colorNum3, colorNum3, 255);


            } catch (Exception e) {
            }

            SpringApplication.run(KidssafetyApplication.class, args);
        }

    public static void toggleRgbLedColor(int colorNum1, int colorNum2, int colorNum3) throws Exception {
        brickletRGBLED.setRGBValue((short) colorNum1, (short) colorNum2, (short) colorNum3);
    }

}
