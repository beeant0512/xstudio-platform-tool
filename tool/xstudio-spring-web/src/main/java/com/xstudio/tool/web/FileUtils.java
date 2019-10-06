package com.xstudio.tool.web;

import com.xstudio.tool.enums.EnError;
import com.xstudio.tool.utils.IdWorker;
import com.xstudio.tool.utils.Msg;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

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
     * 获取文件类型
     *
     * @param file {@link MultipartFile}
     * @return
     */
    public static String getFileType(MultipartFile file) {
        String fileType = "";
        try {
            String fileName = file.getOriginalFilename();
            fileType = fileName.substring(fileName.lastIndexOf('.') + 1);
        } catch (Exception e) {
            logger.error("文件类型获取失败", e);
        }

        return fileType;
    }

    /**
     * 保存文件到服务器本地
     *
     * @param file     {@link MultipartFile}
     * @param filePath 文件存储路径
     * @return
     */
    public static Msg<String> save(MultipartFile file, String filePath) {
        Msg<String> msg = new Msg<>();
        // 获取全文件名
        String fileName = IdWorker.getIdString() + file.getOriginalFilename();
        // 新的文件名
        File f = new File(filePath, fileName);
        String path;
        if (!f.exists()) {
            boolean makeDirsRst = f.mkdirs();
            if (!makeDirsRst) {
                msg.setResult(EnError.SERVICE_INVALID);
                msg.setMsg("文件夹创建失败");
                return msg;
            }
        }
        // 转存文件
        try {
            file.transferTo(f);
            path = filePath.concat(File.separator).concat(fileName);
            msg.setData(path);
        } catch (Exception e) {
            logger.error("文件保存包服务器本地失败！", e);
            msg.setResult(EnError.SERVICE_INVALID);
            msg.setMsg("文件保存包服务器本地失败");
        }
        return msg;
    }
}
