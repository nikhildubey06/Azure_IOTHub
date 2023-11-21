package com.azure.iothub;

import com.microsoft.azure.sdk.iot.device.*;

public class ReceiveMessageFromIotHub {

    private static final String iotHubConnectionString = "HostName=iot-hub-for-demo.azure-devices.net;DeviceId=demodevice;SharedAccessKey=m15ivicisusgmF25BU5eUh0ntBMIH33NIAIoTA5+6cI=";

    private static final String deviceId = "demodevice";

    public static void main(String[] args) throws Exception {
        DeviceClient deviceClient = new DeviceClient(iotHubConnectionString, IotHubClientProtocol.MQTT);

        try {
            deviceClient.open();

            // Set callback for receiving messages
            deviceClient.setMessageCallback(new MessageCallbacks(), null);

            // Wait for messages
            System.out.println("Waiting for messages...");
            System.in.read();
        } finally {
            // Close the device client
            deviceClient.closeNow();
        }
    }

    private static class MessageCallbacks implements MessageCallback {
        @Override
        public IotHubMessageResult execute(Message msg, Object context) {
            System.out.println("Received message: " + new String(msg.getBytes(), Message.DEFAULT_IOTHUB_MESSAGE_CHARSET));

            // Acknowledge the received message
            return IotHubMessageResult.COMPLETE;
        }
    }
}
