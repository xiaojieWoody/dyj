package com.dyj.common.util;

import org.apache.commons.lang3.StringUtils;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class ResourcesUtil {

    public static InputStream getResourceFileInputStream(String resFilePath) {
        return ResourcesUtil.class.getResourceAsStream(resFilePath);
    }

    /**
     * 将Resources下文件复制到目标路径下
     * @param resFilePath
     * @param destnationFilePath
     * @return
     * @throws Exception
     * copyResourceFileToPath("/test/test.txt", "/home/data/1.txt");
     */
    public static boolean copyResourceFileToPath(String resFilePath, String destnationFilePath) throws Exception {

        if(StringUtils.isBlank(resFilePath) || StringUtils.isBlank(destnationFilePath)) {
            throw new Exception("file path is blank!");
        }
        InputStream resourceFileInputStream = getResourceFileInputStream(resFilePath);
        if(resourceFileInputStream == null) {
            throw new Exception("resource file is not exists!");
        }
        Path path = Paths.get(destnationFilePath);
        boolean parentExists = Files.exists(path.getParent());
        if(!parentExists) {
            throw new Exception("destination parent directory is not exists!");
        }
        Files.copy(resourceFileInputStream, path, StandardCopyOption.REPLACE_EXISTING);
        resourceFileInputStream.close();
        return true;
    }
}
