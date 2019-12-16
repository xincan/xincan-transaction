package com.xincan.transaction.system.server.system.utils;

import org.springframework.util.StringUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 文件处理工具类
 */
public class FileUtils {

    /**
     * 获取指定文件夹中的所有文件
     * @param path
     * @return
     */
    public static Map<String, File> getFileByDirectory(String path) {
        Map<String, File> res = new HashMap<>();
        File file = new File(path);
        if (StringUtils.isEmpty(path) || !file.exists() || !file.isDirectory()) {
            return res;
        }
        File[] files = file.listFiles();
        Arrays.stream(files).forEach(t->{
            if (t.isFile()) {
                res.put(t.getName(), t);
            }
        });
        return res;
    }

    /**
     * 读取文件内容
     * @param file
     * @return
     */
    public static String readFile(File file) throws IOException {
        StringBuilder res = new StringBuilder();
        if (file==null || !file.exists() || !file.isFile()) {
            return res.toString();
        }

        try (InputStreamReader read = new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8);
                BufferedReader bufferedReader = new BufferedReader(read);){
            String lineTxt = null;
            while ((lineTxt = bufferedReader.readLine()) != null) {
                res.append("\n").append(lineTxt);
            }
        } catch (Exception e) {
            throw e;
        }
        return res.toString();
    }

}
