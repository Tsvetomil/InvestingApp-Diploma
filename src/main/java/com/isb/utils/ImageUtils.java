package com.isb.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

public class ImageUtils {
    private static final String IMG_DIR = "./src/frontend/public/images";
    private static final String UNDERSCORE = "_";
    private ImageUtils(){

    }

    public static void saveImage(MultipartFile imageFile, String imgName) throws Exception{
        createDirectoryIfNotExist();
        byte[] bytes = imageFile.getBytes();
        Path path = Paths.get(IMG_DIR + File.separator + imgName);
        Files.write(path, bytes);
    }

    private static void createDirectoryIfNotExist(){
        File dir = new File(IMG_DIR);

        if(!dir.exists()){
            dir.mkdir();
        }
    }

    public static String getUniqueImgName(String imgName){
        Date dNow = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("yyMMddhhmmssMs");
        String datetime = ft.format(dNow);

        return datetime + UNDERSCORE + imgName;
    }
}
