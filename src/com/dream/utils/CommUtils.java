package com.dream.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * 常用工具类
 * @author anan
 *
 */
public class CommUtils {

    /**
     * @param fileName 文件地址
     * @return 属性文件
     */
    public static Properties getProperties(String fileName) {
        Properties prop = new Properties();
        FileInputStream input = null;
        try {
            input = new FileInputStream(fileName);
            prop.load(input);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return prop;
    }
}
