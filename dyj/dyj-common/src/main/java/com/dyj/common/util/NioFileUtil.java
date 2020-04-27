package com.dyj.common.util;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Slf4j
public class NioFileUtil {

    /**
     * 根据系统创建临时目录
     * @return
     * @throws IOException
     */
    public static Path createTempDirectory() throws IOException {
        //创建临时目录，不指定根，具体位置取决于操作系统  可以通过 System.getProperty("java.io.tmpdir") 查看
        Path tempDirectory = Files.createTempDirectory("compliance_lib");
        //使用"钩子"的方式删除临时目录（在程序退出后）,一般可以不手动清理，有的系统在重启后会自动清理临时目录
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                Files.delete(tempDirectory);
            } catch (IOException e) {
                log.error("delete tmp file error!", e);
            }
        }));
        return tempDirectory;
    }
}
