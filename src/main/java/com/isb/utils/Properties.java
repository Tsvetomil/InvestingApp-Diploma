package com.isb.utils;

import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

public class Properties {
    private static final java.util.Properties prop = new java.util.Properties();

    static{
        URL pathURL = Properties.class.getClassLoader().getResource("constants.txt");
        assert pathURL != null;
        try(BufferedReader utf8 = new BufferedReader(new InputStreamReader(new FileInputStream(pathURL.getPath()), StandardCharsets.UTF_8))) {
            prop.load(utf8);
        }catch (IOException e){
            Logger.getLogger(Properties.class).error(e);
        }

    }

    private Properties(){

    }

    public static String getString(String property){
        return prop.getProperty(property);
    }
}
