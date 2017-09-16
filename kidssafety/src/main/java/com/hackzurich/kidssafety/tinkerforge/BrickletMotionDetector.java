/* ***********************************************************
 * This file was automatically generated on 2017-07-27.      *
 *                                                           *
 * Java Bindings Version 2.1.14                              *
 *                                                           *
 * If you have a bugfix for this file and want to commit it, *
 * please fix the bug in the generator. You can find a link  *
 * to the generators git repository on tinkerforge.com       *
 *************************************************************/

package com.hackzurich.kidssafety.tinkerforge;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Passive infrared (PIR) motion sensor, 7m range
 */
public class BrickletMotionDetector extends Device {
	public final static int DEVICE_IDENTIFIER = 233;
	public final static String DEVICE_DISPLAY_NAME = "Motion Detector Bricklet";

	public final static byte FUNCTION_GET_MOTION_DETECTED = (byte)1;
	public final static byte FUNCTION_SET_STATUS_LED_CONFIG = (byte)4;
	public final static byte FUNCTION_GET_STATUS_LED_CONFIG = (byte)5;
	public final static byte FUNCTION_GET_IDENTITY = (byte)255;
	private final static int CALLBACK_MOTION_DETECTED = 2;
	private final static int CALLBACK_DETECTION_CYCLE_ENDED = 3;

	public final static short MOTION_NOT_DETECTED = (short)0;
	public final static short MOTION_DETECTED = (short)1;
	public final static short STATUS_LED_CONFIG_OFF = (short)0;
	public final static short STATUS_LED_CONFIG_ON = (short)1;
	public final static short STATUS_LED_CONFIG_SHOW_STATUS = (short)2;

	private List<MotionDetectedListener> listenerMotionDetected = new CopyOnWriteArrayList<MotionDetectedListener>();
	private List<DetectionCycleEndedListener> listenerDetectionCycleEnded = new CopyOnWriteArrayList<DetectionCycleEndedListener>();

	/**
	 * This listener is called after a motion was detected.
	 */
	public interface MotionDetectedListener extends DeviceListener {
		public void motionDetected();
	}

	/**
	 * This listener is called when the detection cycle ended. When this
	 * listener is called, a new motion can be detected again after approximately 2
	 * seconds.
	 */
	public interface DetectionCycleEndedListener extends DeviceListener {
		public void detectionCycleEnded();
	}

	/**
	 * Creates an object with the unique device ID \c uid. and adds it to
	 * the IP Connection \c ipcon.
	 */
	public BrickletMotionDetector(String uid, IPConnection ipcon) {
		super(uid, ipcon);

		apiVersion[0] = 2;
		apiVersion[1] = 0;
		apiVersion[2] = 0;
		responseExpected[IPConnection.unsignedByte(FUNCTION_GET_MOTION_DETECTED)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_SET_STATUS_LED_CONFIG)] = RESPONSE_EXPECTED_FLAG_FALSE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_GET_STATUS_LED_CONFIG)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_GET_IDENTITY)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;

		callbacks[CALLBACK_MOTION_DETECTED] = new IPConnection.DeviceCallbackListener() {
			public void callback(byte[] packet) {
				for (MotionDetectedListener listener: listenerMotionDetected) {
					listener.motionDetected();
				}
			}
		};

		callbacks[CALLBACK_DETECTION_CYCLE_ENDED] = new IPConnection.DeviceCallbackListener() {
			public void callback(byte[] packet) {
				for (DetectionCycleEndedListener listener: listenerDetectionCycleEnded) {
					listener.detectionCycleEnded();
				}
			}
		};
	}

	/**
	 * Returns 1 if a motion was detected. How long this returns 1 after a motion
	 * was detected can be adjusted with one of the small potentiometers on the
	 * Motion Detector Bricklet, see :ref:`here
	 * &lt;motion_detector_bricklet_sensitivity_delay_block_time&gt;`.
	 * 
	 * There is also a blue LED on the Bricklet that is on as long as the Bricklet is
	 * in the "motion detected" state.
	 */
	public short getMotionDetected() throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)8, FUNCTION_GET_MOTION_DETECTED, this);


		byte[] response = sendRequest(bb.array());

		bb = ByteBuffer.wrap(response, 8, response.length - 8);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		short motion = IPConnection.unsignedByte(bb.get());

		return motion;
	}

	/**
	 * Sets the status led configuration.
	 * 
	 * By default the status LED turns on if a motion is detected and off is no motion
	 * is detected.
	 * 
	 * You can also turn the LED permanently on/off.
	 * 
	 * .. versionadded:: 2.0.1$nbsp;(Plugin)
	 */
	public void setStatusLEDConfig(short config) throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)9, FUNCTION_SET_STATUS_LED_CONFIG, this);

		bb.put((byte)config);

		sendRequest(bb.array());
	}

	/**
	 * Returns the configuration as set by {@link BrickletMotionDetector#setStatusLEDConfig(short)}.
	 * 
	 * .. versionadded:: 2.0.1$nbsp;(Plugin)
	 */
	public short getStatusLEDConfig() throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)8, FUNCTION_GET_STATUS_LED_CONFIG, this);


		byte[] response = sendRequest(bb.array());

		bb = ByteBuffer.wrap(response, 8, response.length - 8);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		short config = IPConnection.unsignedByte(bb.get());

		return config;
	}

	/**
	 * Returns the UID, the UID where the Bricklet is connected to,
	 * the position, the hardware and firmware version as well as the
	 * device identifier.
	 * 
	 * The position can be 'a', 'b', 'c' or 'd'.
	 * 
	 * The device identifier numbers can be found :ref:`here &lt;device_identifier&gt;`.
	 * |device_identifier_constant|
	 */
	public Identity getIdentity() throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)8, FUNCTION_GET_IDENTITY, this);


		byte[] response = sendRequest(bb.array());

		bb = ByteBuffer.wrap(response, 8, response.length - 8);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		Identity obj = new Identity();
		obj.uid = IPConnection.string(bb, 8);
		obj.connectedUid = IPConnection.string(bb, 8);
		obj.position = (char)(bb.get());
		for (int i = 0; i < 3; i++) {
			obj.hardwareVersion[i] = IPConnection.unsignedByte(bb.get());
		}
		for (int i = 0; i < 3; i++) {
			obj.firmwareVersion[i] = IPConnection.unsignedByte(bb.get());
		}
		obj.deviceIdentifier = IPConnection.unsignedShort(bb.getShort());

		return obj;
	}

	/**
	 * Adds a MotionDetected listener.
	 */
	public void addMotionDetectedListener(MotionDetectedListener listener) {
		listenerMotionDetected.add(listener);
	}

	/**
	 * Removes a MotionDetected listener.
	 */
	public void removeMotionDetectedListener(MotionDetectedListener listener) {
		listenerMotionDetected.remove(listener);
	}

	/**
	 * Adds a DetectionCycleEnded listener.
	 */
	public void addDetectionCycleEndedListener(DetectionCycleEndedListener listener) {
		listenerDetectionCycleEnded.add(listener);
	}

	/**
	 * Removes a DetectionCycleEnded listener.
	 */
	public void removeDetectionCycleEndedListener(DetectionCycleEndedListener listener) {
		listenerDetectionCycleEnded.remove(listener);
	}
}
