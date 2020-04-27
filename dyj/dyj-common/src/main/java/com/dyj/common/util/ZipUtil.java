package com.dyj.common.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

@Slf4j
public class ZipUtil {

    /**
     * 解压 cppLib.zip
     * @param srcZipPath        zip源文件路径
     * @param destDirPath     解压后的目标文件夹
     * @throws RuntimeException 解压失败会抛出运行时异常
     */
    private static void unZip(String srcZipPath, String destDirPath) throws Exception {

        File zip = new File(srcZipPath);
        if(!zip.exists() || !zip.isFile()) {
            throw new Exception("src zip is not exists!");
        }
        if(StringUtils.isBlank(destDirPath)) {
            throw new Exception("destDirPath is blank!");
        }
        File dstDir = new File(destDirPath);
        if(!dstDir.getParentFile().isDirectory()) {
            throw new Exception("unzip directory parent directory is not exists!");
        }

        try(ZipFile zipFile =  new ZipFile(zip);) {
            Enumeration<? extends ZipEntry> entries = zipFile.entries();
            while (entries.hasMoreElements()) {
                ZipEntry zipEntry = entries.nextElement();
                if(zipEntry.isDirectory()) {
                    String dirPath = destDirPath + "/" + zipEntry.getName();
                    File dir = new File(dirPath);
                    dir.mkdirs();
                } else {
                    String zipFilename = zipEntry.getName();
                    // 解压到外层
                    Path targetPath = Paths.get(destDirPath + "/" + zipFilename);
                    if(!Files.exists(targetPath.getParent())) {
                        Files.createDirectories(targetPath.getParent());
                    }
                    try (InputStream is = zipFile.getInputStream(zipEntry);) {
                        Files.copy(is, targetPath, StandardCopyOption.REPLACE_EXISTING);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        String srcDirPath = "/Users/dingyuanjie/work/engine/doc/compliance_client_lib/tmp";
        String destFilePath = "/Users/dingyuanjie/work/engine/doc/compliance_client_lib/1.zip";
//        zipFiles(srcDirPath, destFilePath);

        String unzipDir = "/Users/dingyuanjie/work/engine/doc/compliance_client_lib/tmp2";
        unZip(destFilePath, unzipDir);
    }


    /**
     * 压缩文件
     * @param srcFile
     * @param destZipPath
     * @return
     */
    public static File zipFiles(String srcFile, String destZipPath) throws Exception {
        File sourceFile = new File(srcFile);
        if(!sourceFile.exists()) {
            throw new Exception("source file is not exists");
        }
        File targetFile = new File(destZipPath);
        if(!targetFile.getParentFile().isDirectory()) {
            throw new Exception("descination file parent directory is not exists!");
        }

        try(ZipOutputStream out = new ZipOutputStream(new FileOutputStream(targetFile));) {
            if(sourceFile.isFile()) {
                zipSingleFile(sourceFile, out, "");
            } else {
                File[] list = sourceFile.listFiles();
                for (File file : list) {
                    compress(file, out, "");
                }
            }
            log.info("压缩完毕");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return targetFile;
    }

    /**
     * 压缩文件夹里文件
     * @param file
     * @param out
     * @param baseDir
     */
    private static void compress(File file, ZipOutputStream out, String baseDir) {
        if(file.isDirectory()) {
            zipDirectory(file, out, baseDir);
        } else {
            zipSingleFile(file, out, baseDir);
        }
    }

    /**
     * 压缩目录
     * @param dir
     * @param out
     * @param baseDir
     */
    private static void zipDirectory(File dir, ZipOutputStream out, String baseDir) {
        if(!dir.exists()) {
            return;
        }
        File[] files = dir.listFiles();
        for (File file : files) {
            compress(file, out, baseDir + dir.getName() +  "/");
        }
    }

    /**
     * 压缩单个文件
     * @param srcFile
     * @param out
     * @param baseDir
     */
    private static void zipSingleFile(File srcFile, ZipOutputStream out, String baseDir) {
        if(!srcFile.exists()) {
            return;
        }
        byte[] buf = new byte[1024];
        int len;
        try(FileInputStream in = new FileInputStream(srcFile);) {
            out.putNextEntry(new ZipEntry(baseDir + srcFile.getName()));
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
