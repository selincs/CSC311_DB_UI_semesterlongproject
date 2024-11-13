package dao;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobClientBuilder;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobContainerClientBuilder;

public class StorageUploader {

        private BlobContainerClient containerClient;

        public StorageUploader( ) {
            this.containerClient = new BlobContainerClientBuilder()
                    .connectionString("DefaultEndpointsProtocol=https;AccountName=saracoglucsc311storage;AccountKey=nl6GibhpjZ14tszjgQ3nbo3c5XZi2lSqGEiJ86rh9OXWlDqTyparYwEvB+rj3NgW8hBeovc0wg9y+AStErW9WQ==;EndpointSuffix=core.windows.net")
                    .containerName("media-files")
                    .buildClient();
        }

        public void uploadFile(String filePath, String blobName) {
            BlobClient blobClient = containerClient.getBlobClient(blobName);
            blobClient.uploadFromFile(filePath);
        }
        public BlobContainerClient getContainerClient(){
            return containerClient;
        }

}
