package com.isb.utils;

import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class UtilsService {
    private UtilsService(){

    }

    public static void saveImage(MultipartFile imageFile) throws Exception{
        String folder = "./images/";
        byte[] bytes = imageFile.getBytes();
        Path path = Paths.get(folder + imageFile.getOriginalFilename());
        Files.write(path, bytes);
    }
}
