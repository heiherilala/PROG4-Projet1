package com.hei.project2p1.controller.mapper.utils;

import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.util.Base64;

public class LoadFiles {
    public static String getImageAsBase64(String pathResource) {
        try {
            // Load image from resources/static directory
            ClassPathResource imageResource = new ClassPathResource(pathResource);
            // Read image bytes
            byte[] imageBytes = StreamUtils.copyToByteArray(imageResource.getInputStream());
            // Convert image bytes to Base64 encoded string
            return Base64.getEncoder().encodeToString(imageBytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
