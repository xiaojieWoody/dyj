package com.dyj.common.util;

import java.io.*;
import java.util.Properties;

public class PropertiesUitl {
    /**
     * 获取Properties文件
     * @param resPath
     * @return
     * @throws IOException
     */
    public static Properties getPropertiesByResPath(String resPath) throws IOException {

        InputStream resourceAsStream = PropertiesUitl.class.getResourceAsStream(resPath);
        Properties properties = new Properties();
        if(resourceAsStream != null) {
            properties.load(resourceAsStream);
        }
        return properties;
    }

    /**
     * 获取Properties文件
     * @param resPath
     * @return
     * @throws IOException
     */
    public static Properties getPropertyByResPath(String resPath) throws IOException {
        InputStream resourceAsStream = PropertiesUitl.class.getResourceAsStream(resPath);
        Properties properties = new Properties();
        if(resourceAsStream != null) {
            properties.load(new InputStreamReader(resourceAsStream));
        }
        return properties;
    }
}
