package com.ntn.ecommerce.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

@Configuration
public class CloudinaryConfig {

    @Value("${cloudinary.cloud-name:#{null}}")
    private String cloudName;

    @Value("${cloudinary.api-key:#{null}}")
    private String apiKey;

    @Value("${cloudinary.api-secret:#{null}}")
    private String apiSecret;

    @Bean
    public Cloudinary cloudinary() {
        // Fallback to environment variables if properties are not set
        String name = cloudName != null ? cloudName : System.getenv("CLOUDINARY_CLOUD_NAME");
        String key = apiKey != null ? apiKey : System.getenv("CLOUDINARY_API_KEY");
        String secret = apiSecret != null ? apiSecret : System.getenv("CLOUDINARY_API_SECRET");

        if (name == null || key == null || secret == null) {
            throw new IllegalStateException("Cloudinary configuration is missing. Please set CLOUDINARY_CLOUD_NAME, "
                    + "CLOUDINARY_API_KEY, and CLOUDINARY_API_SECRET environment variables.");
        }

        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", name,
                "api_key", key,
                "api_secret", secret,
                "secure", true));
    }
}
