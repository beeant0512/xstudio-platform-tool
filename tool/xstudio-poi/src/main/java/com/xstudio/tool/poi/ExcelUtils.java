package com.xstudio.tool.poi;

import com.xstudio.tool.enums.EnError;
import com.xstudio.tool.utils.JavaBeansUtil;
import com.xstudio.tool.utils.Msg;
import com.xstudio.tool.web.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author xiaobiao
 * @version 2017/11/22
 */
public class ExcelUtils {
    private static final String XLS = "xls";
    private static final String XLSX = "xlsx";
    private static Logger logger = LogManager.getLogger(ExcelUtils.class);

    /**
     * 验证EXCEL文件
     *
     * @param filePath 文件路径
     * @return boolean
     */
    public static boolean isExcel(String filePath) {
        boolean isExcel = isExcel2003(filePath) || isExcel2007(filePath);
        if (StringUtils.isEmpty(filePath) || !isExcel) {
            return false;
        }
        return true;
    }

    public static Workbook getWorkBook(MultipartFile file) {
        //获得文件名
        String fileName = file.getOriginalFilename();
        //创建Workbook工作薄对象，表示整个excel
        Workbook workbook = null;
        try {
            //获取excel文件的io流
            InputStream is = file.getInputStream();
            //根据文件后缀名不同(xls和xlsx)获得不同的Workbook实现类对象
            if (fileName.endsWith(XLS)) {
                //2003
                workbook = new HSSFWorkbook(is);
            } else if (fileName.endsWith(XLSX)) {
                //2007
                workbook = new XSSFWorkbook(is);
            }
        } catch (IOException e) {
            logger.info(e.getMessage());
        }
        return workbook;
    }

    public static String getCellValue(Cell cell) {
        String cellValue = "";
        if (cell == null) {
            return cellValue;
        }

        // 判断数据的类型
        switch (cell.getCellType()) {
            //数字
            case Cell.CELL_TYPE_NUMERIC:
                boolean isCellDateFormatted = HSSFDateUtil.isCellDateFormatted(cell);
                if (isCellDateFormatted) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                    Date date = cell.getDateCellValue();
                    return sdf.format(date);
                }
                // 把数字当成String来读，避免出现1读成1.0的情况
                cell.setCellType(Cell.CELL_TYPE_STRING);
                cellValue = String.valueOf(cell.getStringCellValue());
                break;
            //字符串
            case Cell.CELL_TYPE_STRING:
                cellValue = String.valueOf(cell.getStringCellValue());
                break;
            //Boolean
            case Cell.CELL_TYPE_BOOLEAN:
                cellValue = String.valueOf(cell.getBooleanCellValue());
                break;
            //公式
            case Cell.CELL_TYPE_FORMULA:
                cellValue = String.valueOf(cell.getCellFormula());
                break;
            //空值
            case Cell.CELL_TYPE_BLANK:
                cellValue = "";
                break;
            //故障
            case Cell.CELL_TYPE_ERROR:
                cellValue = "非法字符";
                break;
            default:
                cellValue = "未知类型";
                break;
        }
        return cellValue;
    }

    /**
     * 验证EXCEL文件
     *
     * @param file {@link MultipartFile}
     * @return boolean
     */
    public static boolean isExcel(MultipartFile file) {
        boolean isExcel = isExcel2003(FileUtils.getFileType(file)) || isExcel2007(FileUtils.getFileType(file));
        return file != null && !isExcel;
    }

    /**
     * 是否2003格式excel, 文件后缀 .XLSX
     *
     * @param filePath 文件路径
     * @return boolean
     */
    private static boolean isExcel2003(String filePath) {
        return filePath.matches("^.+\\.(?i)(xls)$");
    }

    /**
     * 是否2007格式的excel, 文件后缀 .XLSX
     *
     * @param filePath 文件路径
     * @return boolean
     */
    private static boolean isExcel2007(String filePath) {
        return filePath.matches("^.+\\.(?i)(xlsx)$");
    }

    public void exportExcel(String[] headers, Collection<Object> dataset, String fileName, String[] attribute, HttpServletResponse response) {
        // 声明一个工作薄

        SXSSFWorkbook workbook = new SXSSFWorkbook(100);
        // 生成一个表格

        try {
            // 遍历集合数据，产生数据行 iterator迭代器
            Iterator<Object> it = dataset.iterator();
            SXSSFSheet sheet = null;
            int page = 1;
            sheet = workbook.createSheet("第" + page + "页");
            sheet.setDefaultColumnWidth((short) 20);
            SXSSFRow row = sheet.createRow(0);
            for (short i = 0; i < headers.length; i++) {
                SXSSFCell cell = row.createCell(i);
                XSSFRichTextString text = new XSSFRichTextString(headers[i]);
                cell.setCellValue(text);
            }
            while (it.hasNext()) {

                if (page != 1) {
                    sheet = workbook.createSheet("第" + page + "页");
                    // 设置表格默认列宽度为15个字节
                    sheet.setDefaultColumnWidth((short) 20);
                    // 产生表格标题行
                    row = sheet.createRow(0);
                    for (short i = 0; i < headers.length; i++) {
                        SXSSFCell cell = row.createCell(i);
                        XSSFRichTextString text = new XSSFRichTextString(headers[i]);
                        cell.setCellValue(text);
                    }
                }

                int index;
                //通过迭代器按行写入excel  index=0为标题行，上面已经写入过
                for (index = 1; index < 100000; index++) {
                    if (it.hasNext()) {
                        row = sheet.createRow(index);
                        Object t = it.next();
                        // 利用 反射 （reflect），根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值
                        for (short i = 0; i < headers.length; i++) {
                            SXSSFCell cell = row.createCell(i);
                            String fieldName = attribute[i];
                            Object value = JavaBeansUtil.getter(t, fieldName);
                            // 判断值的类型后进行强制类型转换
                            String textValue = null;
                            // 其它数据类型都当作字符串简单处理
                            if (value != null && value != "") {
                                textValue = value.toString();
                            }
                            if (textValue != null) {
                                XSSFRichTextString richString = new XSSFRichTextString(textValue);
                                cell.setCellValue(richString);
                            }
                        }
                    } else {
                        break;
                    }
                }
                page++;
            }
            getExportedFile(workbook, fileName, response);
        } catch (Exception e) {
            logger.error("EXCEL导出失败", e);
        }

    }

    /**
     * 方法说明: 指定路径下生成EXCEL文件
     *
     * @return
     */
    public void getExportedFile(SXSSFWorkbook workbook, String name, HttpServletResponse response) throws Exception {
        BufferedOutputStream out = null;
        try {
            String fileName = name + ".xlsx";
            response.setContentType("application/x-msdownload");
            response.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("gb2312"), "ISO8859-1"));
            out = new BufferedOutputStream(response.getOutputStream());
            workbook.write(out);
        } catch (Exception e) {
            logger.error("EXCEL导出失败", e);
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }
}
