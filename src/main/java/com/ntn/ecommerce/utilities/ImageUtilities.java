package com.ntn.ecommerce.utilities;

import java.io.IOException;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class ImageUtilities {

    @Autowired
    private Cloudinary cloudinary;

    public String saveFile(String entityType, String entityId, String fileName, MultipartFile multipartFile)
            throws IOException {
        // Generate a unique file code
        String fileCode = RandomStringUtils.randomAlphanumeric(8);

        // Remove file extension from the original filename
        String fileNameWithoutExtension = fileName.substring(0, fileName.lastIndexOf('.'));
        String uniqueFileNameWithoutExtension = fileCode + "-" + fileNameWithoutExtension;

        // Get file extension
        String extension = fileName.substring(fileName.lastIndexOf('.'));
        String uniqueFileName = uniqueFileNameWithoutExtension + extension;

        // Get the folder path based on entity type and entity ID
        String folder = getCloudinaryFolder(entityType, entityId);

        try {
            // Upload file to Cloudinary - use filename without extension as public_id
            // Cloudinary will automatically append the correct extension
            cloudinary.uploader().upload(multipartFile.getBytes(),
                    ObjectUtils.asMap(
                            "folder", folder,
                            "public_id", uniqueFileNameWithoutExtension,
                            "resource_type", "auto"
                    ));

            return uniqueFileName;
        } catch (IOException e) {
            throw new IOException("Could not save file to Cloudinary: " + fileName, e);
        }
    }

    public void deleteFile(String entityType, String entityId, String fileName) throws IOException {
        try {
            // Remove file extension from the filename to get the public_id
            String fileNameWithoutExtension = fileName.substring(0, fileName.lastIndexOf('.'));

            // Get the folder path based on entity type and entity ID
            String folder = getCloudinaryFolder(entityType, entityId);
            String publicId = folder + "/" + fileNameWithoutExtension;

            // Determine resource type based on file extension or entity type
            String resourceType = getResourceType(fileName, entityType);

            // Delete file from Cloudinary
            cloudinary.uploader().destroy(publicId, ObjectUtils.asMap("resource_type", resourceType));
        } catch (IOException e) {
            throw new IOException("Could not delete file from Cloudinary: " + fileName, e);
        }
    }

    private String getCloudinaryFolder(String entityType, String entityId) {
        String folder;
        if ("product".equalsIgnoreCase(entityType)) {
            folder = "product/" + entityId;
        } else if ("category".equalsIgnoreCase(entityType)) {
            folder = "category/" + entityId;
        } else if ("audio".equalsIgnoreCase(entityType)) {
            folder = "audio/product/" + entityId;
        } else {
            throw new IllegalArgumentException("Unsupported entity type: " + entityType);
        }
        return folder;
    }

    private String getResourceType(String fileName, String entityType) {
        // If entity type is audio, return video (Cloudinary treats audio as video)
        if ("audio".equalsIgnoreCase(entityType)) {
            return "video";
        }

        // Determine resource type based on file extension
        String extension = fileName.substring(fileName.lastIndexOf('.') + 1).toLowerCase();

        // Audio/Video formats
        if (extension.matches("mp3|mp4|wav|ogg|webm|flac|aac|m4a|avi|mov|wmv|flv|mkv")) {
            return "video";
        }

        // Image formats
        if (extension.matches("jpg|jpeg|png|gif|bmp|svg|webp|ico|tiff|tif")) {
            return "image";
        }

        // Default to raw for other file types
        return "raw";
    }
}
