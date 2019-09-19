package com.gsww.cascade.utils;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


/**
 * @author
 * @version V3.0
 * @see
 * @since Jdk 1.6
 */

public final class ReadProperties {

    private static Map<String, Properties> propertiesData = new HashMap<String, Properties>();

    public static Properties getInstance(String fileName) {
        if (propertiesData.get(fileName) == null) {
            Properties properties = new Properties();
            InputStream stream =
                    ReadProperties.class.getResourceAsStream(fileName);
            try {
                properties.load(stream);
            } catch (Exception e) {
            }
            properties.putAll(System.getProperties());
            propertiesData.put(fileName, properties);
        }
        return propertiesData.get(fileName);
    }

    public static String getPropertyByStr(String fileName, String propertyName) {
        return String.valueOf(getInstance(fileName).get(propertyName));
    }

    public static int getPropertyByInt(String fileName, String propertyName) {
        return Integer.parseInt(getPropertyByStr(fileName, propertyName));
    }
}

