package com.springboot.azure.config;

import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.blob.CloudBlobClient;
import com.microsoft.azure.storage.blob.CloudBlobContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class AzureBlobConnection {

    public static final Logger logger = LoggerFactory.getLogger(AzureBlobConnection.class);

    public CloudBlobContainer containerInfo(String connectionNameKey,String containerName) {
        CloudBlobContainer container = null;
        try {
            CloudStorageAccount account = CloudStorageAccount.parse(connectionNameKey);
            CloudBlobClient serviceClient = account.createCloudBlobClient();
            container = serviceClient.getContainerReference(containerName);
        } catch (Exception exception) {
            logger.info("AzureBlobConnection containerInfo() " + exception.getMessage());
        }
        return container;
    }
}
