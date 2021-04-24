package com.isb.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;

public class Properties {
    private static final java.util.Properties prop = new java.util.Properties();

    static{
        URL pathURL = Properties.class.getClassLoader().getResource("constants.txt");
        assert pathURL != null;
        try(FileInputStream file = new FileInputStream(pathURL.getPath())) {
            prop.load(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private Properties(){

    }

    public static String getString(String property){
        return prop.getProperty(property);
    }
}
