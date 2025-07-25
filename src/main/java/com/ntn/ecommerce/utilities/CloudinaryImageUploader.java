package com.ntn.ecommerce.utilities;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.stream.Stream;

public class CloudinaryImageUploader {
    public static void main(String[] args) {

        String cloudName = "dmbpesu2z";
        String apiKey = "343896654985164";
        String apiSecret = "jT7-FdtWEFpJFYnfitdwLVIZnn0";

        Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", cloudName,
                "api_key", apiKey,
                "api_secret", apiSecret,
                "secure", true
        ));

        // Các thư mục gốc cần upload
        String baseImagePath = "E:\\Project\\SoftechAptech-SpringBoot-React-sem4\\backend\\images\\upload";
        String baseAudioPath = "E:\\Project\\SoftechAptech-SpringBoot-React-sem4\\backend\\audio\\product";

        // Upload category images
        uploadFromDirectory(cloudinary, Paths.get(baseImagePath, "category"), "category");

        // Upload product images
        uploadFromDirectory(cloudinary, Paths.get(baseImagePath, "product"), "product");

        // Upload audio files
        uploadFromDirectory(cloudinary, Paths.get(baseAudioPath), "audio/product");
    }

    private static void uploadFromDirectory(Cloudinary cloudinary, Path rootPath, String cloudFolderPrefix) {
        if (!Files.exists(rootPath)) {
            System.err.println("Directory not found: " + rootPath);
            return;
        }

        try (Stream<Path> walk = Files.walk(rootPath)) {
            walk.filter(Files::isRegularFile)
                    .sorted()
                    .forEach(file -> {
                        try {
                            String relativePath = rootPath.relativize(file).toString().replace("\\", "/");
                            String fileNameWithoutExt = relativePath.substring(0, relativePath.lastIndexOf('.'));

                            String publicId = cloudFolderPrefix + "/" + fileNameWithoutExt;

                            Map uploadResult = cloudinary.uploader().upload(file.toFile(), ObjectUtils.asMap(
                                    "public_id", publicId,
                                    "overwrite", true,
                                    "resource_type", isAudio(file) ? "video" : "image"
                            ));

                            String secureUrl = (String) uploadResult.get("secure_url");
                            String uploadedPublicId = (String) uploadResult.get("public_id");

                            System.out.println("[UPLOADED] " + uploadedPublicId + " -> " + secureUrl);

                            // === OPTIONAL: THÊM LOGIC CẬP NHẬT DATABASE Ở ĐÂY ===
                            // Ví dụ:
                            // if (cloudFolderPrefix.startsWith("product")) updateProductImage(productId, secureUrl);
                            // if (cloudFolderPrefix.startsWith("audio")) updateProductAudio(productId, secureUrl);

                        } catch (Exception e) {
                            System.err.println("[ERROR] Failed to upload " + file.getFileName() + ": " + e.getMessage());
                            e.printStackTrace();
                        }
                    });
        } catch (IOException e) {
            System.err.println("Failed to walk directory " + rootPath + ": " + e.getMessage());
        }
    }

    private static boolean isAudio(Path file) {
        String name = file.getFileName().toString().toLowerCase();
        return name.endsWith(".mp3") || name.endsWith(".wav") || name.endsWith(".ogg");
    }
}
