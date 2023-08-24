package com.hei.project2p1.controller;

import java.io.File;
import java.io.IOException;
    import java.nio.file.Files;
    import java.util.Base64;

public class ImageToBase64Converter {
  public static String gateLogo() {
    String imagePath = ""; // Replace with the actual path to your image file

    try {
      // Read the image file
      byte[] imageBytes = Files.readAllBytes(new File(imagePath).toPath());

      // Convert image byte array to Base64 string
      String base64Image = Base64.getEncoder().encodeToString(imageBytes);
      System.out.println(base64Image);
      return "data:image/jpeg;base64," + base64Image;
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }
}