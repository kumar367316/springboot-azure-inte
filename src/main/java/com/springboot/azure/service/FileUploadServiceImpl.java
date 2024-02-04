package com.springboot.azure.service;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.azure.storage.blob.models.BlobItem;
import com.springboot.azure.config.AzureBlobConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class FileUploadServiceImpl implements FileUploadService{

    public static final Logger logger = LoggerFactory.getLogger(FileUploadServiceImpl.class);

    @Value("${blob-account-name-key}")
    private String connectionNameKey;

    @Value("${blob-container-name}")
    private String containerName;

    @Autowired
    private AzureBlobConnection azureBlobConnection;

    @Override
    public String fileUploadprocessing() {
        String sourceDirectory = "output/"+"transit";
        String printDirectory = "output/"+"process";
        moveFilesToPrint(sourceDirectory, printDirectory);
        return null;
    }

    @Scheduled(cron = "${cron-job-print-interval}")
    public void batchProcessing(){
        String sourceDirectory = "output/"+"transit";
        String printDirectory = "output/"+"process";
        moveFilesToPrint(sourceDirectory, printDirectory);
    }

    private void moveFilesToPrint(String sourceDirectory, String targetDirectory) {
        logger.info("moveFilesToPrint method ");
        BlobContainerClient blobContainerClient = getBlobContainerClient(connectionNameKey, containerName);
        Iterable<BlobItem> listBlobs = blobContainerClient.listBlobsByHierarchy(sourceDirectory);
        for (BlobItem blobItem : listBlobs) {
            String fileName = getFileName(blobItem.getName());
            BlobClient dstBlobClient = blobContainerClient.getBlobClient(targetDirectory + fileName);
            BlobClient srcBlobClient = blobContainerClient.getBlobClient(blobItem.getName());
            String updateSrcUrl = srcBlobClient.getBlobUrl();
            if (srcBlobClient.getBlobUrl().contains("%2F")) {
                updateSrcUrl = srcBlobClient.getBlobUrl().replace("%2F", "/");
            }
            dstBlobClient.beginCopy(updateSrcUrl, null);
            srcBlobClient.delete();
        }
    }

    public String getFileName(String blobName) {
        return blobName.replace("output/" + "transit", "");
    }


    public BlobContainerClient getBlobContainerClient(String connectionNameKey, String containerName) {
        BlobServiceClient blobServiceClient = new BlobServiceClientBuilder().connectionString(connectionNameKey)
                .buildClient();
        return blobServiceClient.getBlobContainerClient(containerName);
    }
}
