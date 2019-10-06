package com.xstudio.tool.poi;

import com.xstudio.tool.utils.DateUtil;
import com.xstudio.tool.utils.JavaBeansUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Date;
import java.util.List;

/**
 * CSV文件工具类
 *
 * @author xiaobiao
 * @date 2018/11/13
 */
public class CsvUtil {
    private static Logger logger = LogManager.getLogger(CsvUtil.class);

    private CsvUtil() {
    }

    /**
     * 生成CSV文件
     *
     * @param head       CSV文件表头
     * @param headFields CSV文件内容各字段名称
     * @param list       数据集
     * @param file       文件
     */
    public static void write(List<String> head, List<String> headFields, List<?> list, File file) {
        // GB2312使正确读取分隔符","
        try (BufferedWriter csvWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "GB2312"), 1024)) {
            // 写入文件头部
            StringBuilder sb;
            for (Object data : head) {
                sb = new StringBuilder();
                String rowStr = sb.append("\"").append(data).append("\",").toString();
                csvWriter.write(rowStr);
            }
            csvWriter.newLine();
            for (Object o : list) {
                for (String headField : headFields) {
                    sb = new StringBuilder();
                    Object getter = JavaBeansUtil.getter(o, headField);
                    if (getter instanceof Date) {
                        sb.append("\"").append(DateUtil.format((Date) getter)).append("\",");
                    } else {
                        sb.append("\"").append(getter).append("\",");
                    }
                    csvWriter.write(sb.toString());
                }
                csvWriter.newLine();
            }
            csvWriter.flush();
        } catch (IOException e) {
            logger.error("写CSV文件异常", e);
        }
    }

    public static void download(List<String> head, List<String> headFields, List<?> list, File file, HttpServletResponse response) {
        write(head, headFields, list, file);
        // 取得文件名。
        String filename = file.getName();

        // 以流的形式下载文件。
        byte[] buffer = new byte[0];
        int read = 0;
        try (InputStream fis = new BufferedInputStream(new FileInputStream(file))) {
            buffer = new byte[fis.available()];
            read = fis.read(buffer);

            // 清空response
            response.reset();
            // 设置response的Header
            response.addHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes("gb2312"), "ISO8859-1"));
            response.addHeader("Content-Length", "" + file.length());
        } catch (IOException e) {
            logger.error("文件下载异常", e);
        }
        if (read > 0) {
            try (OutputStream toClient = new BufferedOutputStream(response.getOutputStream());) {
                response.setContentType("application/octet-stream");
                toClient.write(buffer);
                toClient.flush();
            } catch (Exception e1) {
                logger.error("文件下载异常", e1);
            }
        } else {
            response.setStatus(500);
        }
    }
}
