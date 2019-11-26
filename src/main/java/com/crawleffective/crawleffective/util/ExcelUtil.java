package com.crawleffective.crawleffective.util;

import com.crawleffective.crawleffective.model.Merchant;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: 秦小冷
 * @Date: 2019/1/8 9:22
 */
@SuppressWarnings("all")
public class ExcelUtil {

    /**
     * 写一个excel文件
     *
     * @throws Exception
     * @Author: 秦小冷
     */
    public static void testWrite() throws Exception {
        //1、创建工作簿
        HSSFWorkbook wookbook = new HSSFWorkbook();
        //2、创建工作表，名字是shermin
        HSSFSheet sheet = wookbook.createSheet("shermin");
        //3、创建行；创建第一行，索引从0开始
        HSSFRow row = sheet.createRow(0);
        //4、创建单元格；创建第1行第1列
        HSSFCell cell = row.createCell(0);

        cell.setCellValue("hello world");

        //输出到硬盘，把excel输出到具体的地址
        FileOutputStream fileOutputStream = new FileOutputStream("D:\\qqq\\test.xls");
        wookbook.write(fileOutputStream);
        wookbook.close();
        fileOutputStream.close();
    }


    /**
     * 读取excel文件
     *
     * @throws Exception
     * @Author: 秦小冷
     */
    public static void testToRead() {
        String filePath = "D:\\1028.xlsx";
        //判断是否excel文档
        try {
            if (filePath.matches("^.+\\.(?i)(xls|xlsx)$")) {
                FileInputStream fileInputStream = new FileInputStream(filePath);
                boolean is03Excel = filePath.matches("^.+\\.(?i)(xlsx)$") ? true : false;
                Workbook workbook = is03Excel ? new XSSFWorkbook(fileInputStream) : new HSSFWorkbook(fileInputStream);
                Sheet sheet = workbook.getSheetAt(0);
                //指定第二行
                int r = 1;
                for (int i = 1; i < 40; i++) {
                    Row rows = sheet.createRow(r);
                    Cell cell = rows.getCell(i);
                    System.out.println(cell.getStringCellValue());
                    workbook.close();
                    fileInputStream.close();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @Author: 秦晓渊
     * 向excel中写数据
     */
    public static void writeData(List<Merchant> companyDatas, String title, String search) {
        //创建工作薄
        Workbook workbook = new XSSFWorkbook();
        //创建工作表，并起名称为 "你好"
        Sheet sheet = workbook.createSheet("city");
//        CellStyle cellStyle = workbook.createCellStyle();
        //创建字体
        Font font = workbook.createFont();
        //加粗字体
//        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        font.setBold(true);
        //字体大小
        font.setFontHeightInPoints((short) 16);
        //字体大小
        font.setFontHeightInPoints((short) 16);
        //创建单元格样式
        CellStyle style = workbook.createCellStyle();
        //水平居中
//        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style.setAlignment(HorizontalAlignment.CENTER);
        //c垂直居中
//        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        //将字体加入到样式中。
        style.setFont(font);

        //参数说明：1：开始行 2：结束行  3：开始列 4：结束列
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 6));

        //指定列宽
        sheet.setColumnWidth(0, 20 * 256);
        sheet.setColumnWidth(1, 20 * 256);
        sheet.setColumnWidth(2, 20 * 256);
        sheet.setColumnWidth(3, 20 * 256);
        sheet.setColumnWidth(4, 30 * 256);
        sheet.setColumnWidth(5, 30 * 256);
        sheet.setColumnWidth(6, 30 * 256);

        Row row0 = sheet.createRow(0);
        row0.setHeight((short) 800);
        Cell cell = row0.createCell(0);
        cell.setCellValue(search);
        cell.setCellStyle(style);


        //创建单元格样式
        CellStyle style1 = workbook.createCellStyle();
        style1.setAlignment(HorizontalAlignment.CENTER);
        style1.setVerticalAlignment(VerticalAlignment.CENTER);


        //创建单元格样式
        CellStyle style2 = workbook.createCellStyle();
        style2.setAlignment(HorizontalAlignment.CENTER);
        style2.setVerticalAlignment(VerticalAlignment.CENTER);
        style2.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
//        应用此背景色
        style2.setFillPattern(FillPatternType.SOLID_FOREGROUND);
//        style2.setFillPattern(CellStyle.SOLID_FOREGROUND);

        //指定第二行
        Row row = sheet.createRow(1);
        row.setHeight((short) 500);
        row.createCell(0).setCellValue("所在地");
        row.getCell(0).setCellStyle(style2);
        //指定第二行的每一列
        row.createCell(1).setCellValue("公司名称");
        row.getCell(1).setCellStyle(style2);

        row.createCell(2).setCellValue("诚信年份");
        row.getCell(2).setCellStyle(style2);

        row.createCell(3).setCellValue("卖家");
        row.getCell(3).setCellStyle(style2);

        row.createCell(4).setCellValue("电话");
        row.getCell(4).setCellStyle(style2);

        row.createCell(5).setCellValue("移动电话");
        row.getCell(5).setCellStyle(style2);

        row.createCell(6).setCellValue("地址");
        row.getCell(6).setCellStyle(style2);

//        指定行号
        int r = 2;
        for (int i = 0; i < companyDatas.size(); i++) {
            //获取集合中的对象

            //指定第二行
            Row rows = sheet.createRow(r);
            rows.setHeight((short) 450);
            //指定第二行的每一列
            rows.createCell(0).setCellValue(companyDatas.get(i).getDisc());
            rows.getCell(0).setCellStyle(style1);

            rows.createCell(1).setCellValue(companyDatas.get(i).getTitle());
            rows.getCell(1).setCellStyle(style1);

            rows.createCell(2).setCellValue(companyDatas.get(i).getSpan());
            rows.getCell(2).setCellStyle(style1);

            rows.createCell(3).setCellValue(companyDatas.get(i).getMembername());
            rows.getCell(3).setCellStyle(style1);

            if (companyDatas.get(i).getPhone() != null) {
                rows.createCell(4).setCellValue(companyDatas.get(i).getPhone());
            } else {
                rows.createCell(4).setCellValue("暂无");
            }
            rows.getCell(4).setCellStyle(style1);

            if (companyDatas.get(i).getMobilephone() != null) {
                rows.createCell(5).setCellValue(companyDatas.get(i).getMobilephone());
            } else {
                rows.createCell(5).setCellValue("暂无");
            }
            rows.getCell(5).setCellStyle(style1);

            rows.createCell(6).setCellValue(companyDatas.get(i).getAddress());
            rows.getCell(6).setCellStyle(style1);
//            rows.createCell(3).setCellValue(String.valueOf(companyDatas.get(i).getExt()));
//            rows.getCell(3).setCellStyle(style1);

            r++;
        }
        workbook.setSheetName(0, "信息");
        try {
            File file = new File("D:\\merchant");
            //如果文件夹不存在
            if (!file.exists()) {
                //创建文件夹
                file.mkdir();
            }
            file = new File("D:\\merchant\\" + title + ".xlsx");
            FileOutputStream fileoutputStream = new FileOutputStream(file);
            workbook.write(fileoutputStream);
            fileoutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * 读取excel所有数据
     * @throws Exception
     * @Author: 秦小冷
     */
    public static void ReadDataAll(String filePath) {
//        String filePath = "D:\\1028.xlsx";
        File file = new File(filePath);
        try {
            InputStream in = new FileInputStream(file);
            //得到整个excel对象
            XSSFWorkbook excel = new XSSFWorkbook(in);
            //获取整个excel有多少个sheet
            int sheets = excel.getNumberOfSheets();
            //便利第一个sheet
            Map<String, String> colMap = new HashMap<String, String>();
            for (int i = 0; i < sheets; i++) {
                XSSFSheet sheet = excel.getSheetAt(i);
                if (sheet == null) {
                    continue;
                }
                int mergedRegions = sheet.getNumMergedRegions();
                XSSFRow row2 = sheet.getRow(0);
                Map<Integer, String> category = new HashMap<Integer, String>();
                for (int j = 0; j < mergedRegions; j++) {
                    CellRangeAddress rangeAddress = sheet.getMergedRegion(j);
                    int firstRow = rangeAddress.getFirstColumn();
                    int lastRow = rangeAddress.getLastColumn();
                    category.put(rangeAddress.getFirstColumn(), rangeAddress.getLastColumn() + "-" + row2.getCell(firstRow).toString());
                }
                //便利每一行
                for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
                    System.out.println();
                    XSSFRow row = sheet.getRow(rowNum);
                    if (row == null) {
                        continue;
                    }
                    short lastCellNum = row.getLastCellNum();
                    String cate = "";
                    Integer maxIndex = 0;
                    for (int col = row.getFirstCellNum(); col < lastCellNum; col++) {
                        XSSFCell cell = row.getCell(col);
                        if (cell == null) {
                            continue;
                        }
                        if ("".equals(cell.toString())) {
                            continue;
                        }
                        int columnIndex = cell.getColumnIndex();
                        String string = category.get(columnIndex);
                        if (string != null && !string.equals("")) {
                            String[] split = string.split("-");
                            cate = split[1];
                            maxIndex = Integer.parseInt(split[0]);
                            System.out.println(cate + "<-->" + cell.toString());
                        } else {
                            //如果当前便利的列编号小于等于合并单元格的结束,说明分类还是上面的分类名称
                            if (columnIndex <= maxIndex) {
                                System.out.println(cate + "<-->" + cell.toString());
                            } else {
                                System.out.println("分类未知" + "<-->" + cell.toString());
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取excel指定列数据
     * @throws Exception
     * @Author: 秦小冷
     */
    public static List ReadDataColumn(String filePath,Integer col) {
//        String filePath = "D:\\1028.xlsx";
        File file = new File(filePath);
        List list = new ArrayList();
        try {
            InputStream in = new FileInputStream(file);
            //得到整个excel对象
            XSSFWorkbook excel = new XSSFWorkbook(in);
            //获取整个excel有多少个sheet
            int sheets = excel.getNumberOfSheets();
            //便利第一个sheet
            Map<String, String> colMap = new HashMap<String, String>();
            for (int i = 0; i < sheets; i++) {
                XSSFSheet sheet = excel.getSheetAt(i);
                if (sheet == null) {
                    continue;
                }
                int mergedRegions = sheet.getNumMergedRegions();
                XSSFRow row2 = sheet.getRow(0);
                Map<Integer, String> category = new HashMap<Integer, String>();
                for (int j = 0; j < mergedRegions; j++) {
                    CellRangeAddress rangeAddress = sheet.getMergedRegion(j);
                    int firstRow = rangeAddress.getFirstColumn();
                    int lastRow = rangeAddress.getLastColumn();
                    category.put(rangeAddress.getFirstColumn(), rangeAddress.getLastColumn() + "-" + row2.getCell(firstRow).toString());
                }
                //便利每一行
//                int col = 1;
                for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
                    System.out.println();
                    XSSFRow row = sheet.getRow(rowNum);
                    if (row == null) {
                        continue;
                    }
                    short lastCellNum = row.getLastCellNum();
                    String cate = "";
                    Integer maxIndex = 0;
//                    for (int col = row.getFirstCellNum(); col < lastCellNum; col++) {
                        XSSFCell cell = row.getCell(col);
                        if (cell == null) {
                            continue;
                        }
                        if ("".equals(cell.toString())) {
                            continue;
                        }
                        int columnIndex = cell.getColumnIndex();
                        String string = category.get(columnIndex);
                        if (string != null && !string.equals("")) {
                            String[] split = string.split("-");
                            cate = split[1];
                            maxIndex = Integer.parseInt(split[0]);
                            System.out.println(cate + "<-->" + cell.toString());
                        } else {
                            //如果当前便利的列编号小于等于合并单元格的结束,说明分类还是上面的分类名称
                            if (columnIndex <= maxIndex) {
                                System.out.println(cate + "<-->" + cell.toString());
                            } else {
                                list.add(cell.toString());
                                System.out.println("分类未知" + "<-->" + cell.toString());
                                return list;
                            }
                        }
//                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
