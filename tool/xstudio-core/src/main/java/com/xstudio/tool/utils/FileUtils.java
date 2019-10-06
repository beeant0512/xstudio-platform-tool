package com.xstudio.tool.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * 文件工具类
 *
 * @author xiaobiao
 * @version 2017/11/22
 */

public class FileUtils {

    private static Logger logger = LogManager.getLogger(FileUtils.class);

    private FileUtils() {
    }

    /**
     * 获取资源文件
     *
     * @param resourceFileName
     * @return
     */
    public static File getResource(String resourceFileName) {
        URL resource = FileUtils.class.getResource("/" + resourceFileName);
        if (null == resource) {
            return null;
        }
        try {
            return new File(resource.toURI());
        } catch (URISyntaxException e) {
            return null;
        }
    }


    /**
     * 删除本地文件
     *
     * @param file {@link File}
     */
    public static void delete(File file) {
        boolean delete = file.delete();
        if (delete) {
            logger.debug("文件删除成功");
        } else {
            file.deleteOnExit();
        }
    }

    /**
     * 是否是压缩文件
     *
     * @param fileType 文件类型
     * @return boolean
     */
    public static boolean isZipFile(String fileType) {
        String type = fileType.toLowerCase();
        if (type.startsWith(".")) {
            type = type.substring(1);
        }
        return "zip".equalsIgnoreCase(type)
                || "rar".equalsIgnoreCase(type)
                || "7z".equalsIgnoreCase(type)
                || "gzip".equalsIgnoreCase(type)
                || "gz".equalsIgnoreCase(type)
                || "tgz".equalsIgnoreCase(type)
                || "tar".equalsIgnoreCase(type)
                ;
    }

    /**
     * 是否是图片文件
     *
     * @param fileType 文件类型
     * @return boolean
     */
    public static boolean isImageFile(String fileType) {
        String type = fileType.toLowerCase();
        if (type.startsWith(".")) {
            type = type.substring(1);
        }
        return "png".equalsIgnoreCase(type)
                || "svg".equalsIgnoreCase(type)
                || "jpg".equalsIgnoreCase(type)
                || "jpeg".equalsIgnoreCase(type)
                || "jpe".equalsIgnoreCase(type)
                || "gif".equalsIgnoreCase(type)
                || "tiff".equalsIgnoreCase(type)
                || "tif".equalsIgnoreCase(type)
                ;
    }

    /**
     * 是否是Excel文件
     *
     * @param fileType 文件类型
     * @return boolean
     */
    public static boolean isExcelFile(String fileType) {
        String type = fileType.toLowerCase();
        if (type.startsWith(".")) {
            type = type.substring(1);
        }
        return "xls".equalsIgnoreCase(type)
                || "xlsx".equalsIgnoreCase(type)
                ;
    }

    /**
     * File 转 FileOutputStream
     *
     * @param file {@link File}
     * @return
     * @throws IOException
     */
    public static FileOutputStream openOutputStream(final File file) throws IOException {
        return openOutputStream(file, false);
    }

    public static FileOutputStream openOutputStream(final File file, final boolean append) throws IOException {
        if (file.exists()) {
            if (file.isDirectory()) {
                throw new IOException("File '" + file + "' exists but is a directory");
            }
            if (!file.canWrite()) {
                throw new IOException("File '" + file + "' cannot be written to");
            }
        } else {
            final File parent = file.getParentFile();
            if (parent != null && !parent.mkdirs() && !parent.isDirectory()) {
                throw new IOException("Directory '" + parent + "' could not be created");
            }
        }
        return new FileOutputStream(file, append);
    }

}
