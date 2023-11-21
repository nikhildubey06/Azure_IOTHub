package com.azure.iothub;

import com.microsoft.azure.sdk.iot.device.*;

public class SendMessageToIotHub {
	
    private static final String iotHubConnectionString = "HostName=iot-hub-for-demo.azure-devices.net;DeviceId=demodevice;SharedAccessKey=m15ivicisusgmF25BU5eUh0ntBMIH33NIAIoTA5+6cI=";

    private static final String deviceId = "demodevice";

    public static void main(String[] args) throws Exception {
        DeviceClient deviceClient = new DeviceClient(iotHubConnectionString, IotHubClientProtocol.MQTT);

        try {
            deviceClient.open();

            // Create a message
            String messageStr = "Hey! My name is Nikhil and I have sent this message to you...";
            Message message = new Message(messageStr);

            // Send the message
            deviceClient.sendEventAsync(message, new EventCallback(), null);

            System.out.println("Message sent: " + messageStr);

            // Wait for the message to be sent
            Thread.sleep(2000);
        } finally {
            // Close the device client
            deviceClient.closeNow();
        }
    }

    private static class EventCallback implements IotHubEventCallback {
        @Override
        public void execute(IotHubStatusCode status, Object context) {
            System.out.println("IoT Hub responded to message with status: " + status.name());
        }
    }
}

