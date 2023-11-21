package com.azure.iothub;

import com.microsoft.azure.sdk.iot.device.DeviceClient;
import com.microsoft.azure.sdk.iot.device.IotHubClientProtocol;
import com.microsoft.azure.sdk.iot.device.Message;
import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.StorageException;
import com.microsoft.azure.storage.blob.CloudBlobContainer;
import com.microsoft.azure.storage.blob.CloudBlockBlob;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;

public class SendMessageToStorageAccount {

    private static final String iotHubConnectionString = "HostName=iot-hub-for-demo.azure-devices.net;DeviceId=demodevice;SharedAccessKey=m15ivicisusgmF25BU5eUh0ntBMIH33NIAIoTA5+6cI=";
    private static final String storageConnectionString = "DefaultEndpointsProtocol=https;AccountName=minestorgaeaccount;AccountKey=YKgX5KO9kE524E4budAFC96B5pAGREtrWMaEzcHnxDLAd807G0ws61vM/PcA0jnpFRcuhpSqTcc4+AStLZC9tA==;EndpointSuffix=core.windows.net";
    private static final String storageContainerName = "minecontainer";

    public static void main(String[] args) throws StorageException {
        try {
            // Initialize IoT Hub client
            DeviceClient client = new DeviceClient(iotHubConnectionString, IotHubClientProtocol.MQTT);
            client.open();

            // Create a message
            String messageStr = "Hello message from IoT device!";
            Message message = new Message(messageStr);

            // Send the message to IoT Hub
            client.sendEventAsync(message, null, null);

            // Upload the message to Azure Storage
            CloudStorageAccount storageAccount = CloudStorageAccount.parse(storageConnectionString);
            CloudBlobContainer container = storageAccount.createCloudBlobClient().getContainerReference(storageContainerName);
            CloudBlockBlob blob = container.getBlockBlobReference("message.txt");
            blob.uploadText(messageStr);

            // Close the IoT Hub client
            client.closeNow();
        } catch (IOException | URISyntaxException | InvalidKeyException e) {
            e.printStackTrace();
        }
    }
}

