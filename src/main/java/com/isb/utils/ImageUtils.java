package com.isb.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ImageUtils {
    private static final String IMG_DIR = "./images";
    private ImageUtils(){

    }

    public static void saveImage(MultipartFile imageFile) throws Exception{
        createDirectoryIfNotExist();
        byte[] bytes = imageFile.getBytes();
        Path path = Paths.get(IMG_DIR + File.separator + imageFile.getOriginalFilename());
        Files.write(path, bytes);
    }

    private static void createDirectoryIfNotExist(){
        File dir = new File(IMG_DIR);

        if(!dir.exists()){
            dir.mkdir();
        }
    }
}
