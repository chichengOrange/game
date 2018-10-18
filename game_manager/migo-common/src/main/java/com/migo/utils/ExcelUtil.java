package com.migo.utils;

import com.github.pagehelper.util.StringUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 操作Excel表格的功能类
 *
 * @author：
 */
public class ExcelUtil {
    private static Logger logger = LoggerFactory.getLogger(ExcelUtil.class);
    private POIFSFileSystem fs;
    private XSSFWorkbook wb;
    private Sheet sheet;
    private Row row;


    /**
     * 获取单元格数据内容为字符串类型的数据
     *
     * @param cell Excel单元格
     * @return String 单元格数据内容，若为字符串的要加单引号
     */
    @SuppressWarnings("unused")
    public String getStringCellValue(HSSFCell cell) {
        String strCell = "";
        switch (cell.getCellType()) {
            case HSSFCell.CELL_TYPE_STRING:
                strCell = "'" + cell.getStringCellValue() + "'";
                break;
            case HSSFCell.CELL_TYPE_NUMERIC:
                strCell = String.valueOf(cell.getNumericCellValue());
                break;
            case HSSFCell.CELL_TYPE_BOOLEAN:
                strCell = String.valueOf(cell.getBooleanCellValue());
                break;
            case HSSFCell.CELL_TYPE_BLANK:
                strCell = "''";
                break;
            default:
                strCell = "''";
                break;
        }
        if (strCell.equals("''") || strCell == null) {
            return "";
        }
        if (cell == null) {
            return "";
        }
        return strCell;
    }

    /**
     * 根据HSSFCell类型设置数据
     *
     * @param cell
     * @return
     */
    public static String getCellFormatValue(Cell cell) {
        DecimalFormat format = new DecimalFormat("#");
        String cellvalue = "";
        if (cell != null) {
            // 判断当前Cell的Type
            switch (cell.getCellType()) {
                // 如果当前Cell的Type为NUMERIC
                case HSSFCell.CELL_TYPE_NUMERIC:
                case HSSFCell.CELL_TYPE_FORMULA: {
                    // 判断当前的cell是否为Date
                    if (HSSFDateUtil.isCellDateFormatted(cell)) {
                        Date date = cell.getDateCellValue();
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        cellvalue = sdf.format(date);
                    }
                    // 如果是纯数字
                    else {
                        // 取得当前Cell的数值
                        cellvalue = format.format(cell.getNumericCellValue());
                    }
                    break;
                }
                // 如果当前Cell的Type为STRIN
                case HSSFCell.CELL_TYPE_STRING:
                    // 取得当前的Cell字符串
                    cellvalue = cell.getRichStringCellValue().getString();
                    break;
                // 默认的Cell值
                default:
                    cellvalue = " ";
            }
        } else {
            cellvalue = "";
        }
        return cellvalue;

    }

    /**
     * 读取Excel数据内容
     *
     * @param filePath
     * @return Map 包含单元格数据内容的Map对象
     */
    public List<Map<String, String>> readExcelContent(String filePath) throws FileNotFoundException {

        return readExcelContent(new FileInputStream(filePath));
    }


    /**
     * 读取Excel数据内容
     *
     * @param inputStream
     * @return Map 包含单元格数据内容的Map对象
     */
    public List<Map<String, String>> readExcelContent(InputStream inputStream) {
        Map<Integer, String> content = new HashMap<>();
        Workbook wb = null;
        String str = "";
        //try {
        try {
            wb = WorkbookFactory.create(inputStream);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (wb != null) {
                    wb.close();
                }
            } catch (Exception e) {
                logger.error("readExcelContent error:" + e.getMessage(), e);
            }
        }
        //---------------获取标题
        sheet = wb.getSheetAt(0);
        row = sheet.getRow(0);
        // 标题总列数
        int colNums = row.getPhysicalNumberOfCells();
        String[] title = new String[colNums];
        for (int i = 0; i < colNums; i++) {
            title[i] = getTitleValue(row.getCell(i));
        }
        //------------------------

        //sheet = wb.getSheetAt(0);
        // 得到总行数
        int rowNum = sheet.getLastRowNum();
        row = sheet.getRow(0);
        int colNum = row.getPhysicalNumberOfCells();
        // 正文内容应该从第二行开始,第一行为表头的标题
        for (int i = 1; i <= rowNum; i++) {
            row = sheet.getRow(i);
            int j = 0;
            while (j < colNum) {
                String cellValue = "_=";
                if (row.getCell(j) != null) {
                    String cValue = getCellFormatValue(row.getCell(j)).trim();
                    cellValue = cValue + "_=";
                }
                str += cellValue;
                j++;
            }
            content.put(i, str);
            str = "";
        }
        List<Map<String, String>> list = new ArrayList<>();
        Map<String, String> dataMap = null;
        if (null != content && !"".equals(content)) {
            for (int i = 1; i < content.size() + 1; i++) {
                dataMap = new HashMap<>(16);
                String[] aa = content.get(i).split("_=");
                for (int n = 0; n < title.length; n++) {
                    if (aa.length > n) {
                        dataMap.put(title[n], aa[n]);
                        //logger.info("title:" + title[n] + "==content:" + aa[n]);
                    } else {
                        dataMap.put(title[n], "");
                    }
                }
                list.add(dataMap);
            }
        }
        return list;
    }

    /**
     * 获取单元格数据内容为日期类型的数据
     *
     * @param cell Excel单元格
     * @return String 单元格数据内容
     */
    private String getDateCellValue(HSSFCell cell) {
        String result = "";
        try {
            int cellType = cell.getCellType();
            if (cellType == HSSFCell.CELL_TYPE_NUMERIC) {
                Date date = cell.getDateCellValue();
                result = (date.getYear() + 1900) + "-" + (date.getMonth() + 1)
                        + "-" + date.getDate();
            } else if (cellType == HSSFCell.CELL_TYPE_STRING) {
                String date = getStringCellValue(cell);
                result = date.replaceAll("[年月]", "-").replace("日", "").trim();
            } else if (cellType == HSSFCell.CELL_TYPE_BLANK) {
                result = "";
            }
        } catch (Exception e) {
            logger.info("日期格式不正确!");
            logger.error(e.getMessage(), e);
        }
        return result;
    }

    public String getTitleValue(Cell cell) {
        String strCell = cell.getStringCellValue();
        return strCell;
    }


    public static void main(String[] args) {
        ExcelUtil excelUtil = new ExcelUtil();
        List<Map<String, String>> users = null;
        try {
            users = excelUtil.readExcelContent("C:\\Users\\mql\\Desktop\\user.xlsx");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        System.out.println(users);

        if (users == null || users.size() == 0) {
            return;
        }
        Map<String, String> m0 = users.get(0);


        StringBuffer msgBuf = new StringBuffer();

        String br = "\r\n";

        String[] comps = {"name", "mobile", "email"};

        if (!CollectionUtils.isSubCollection(Arrays.asList(comps), m0.keySet())) {
            msgBuf.append("标题必须包含" + Arrays.asList(comps).toString()).append(br);
        }
        String and = " & ";
        List<String> nameList = new LinkedList<>();
        for (int i = 0; i < users.size(); i++) {
            StringBuffer line = new StringBuffer("第 " + i + " 行 ");
            Map<String, String> map = users.get(i);
            String name = map.get("name");
            if (StringUtil.isEmpty(name)) {
                line.append(" name:不能为空").append(and);
            } else {
                if (nameList.contains(map.get("name"))) {
                    line.append("name:"+name + " 重复").append(and);
                }
                nameList.add(name);
            }

            String mobile = map.get("mobile");
            if (StringUtil.isNotEmpty(mobile) && !MatchUtil.checkPhone(mobile)){
                line.append(mobile+" 电话格式不正确").append(and);
            }

            String email = map.get("email");
            if (StringUtil.isNotEmpty(email) && !MatchUtil.checkEmaile(email)){
                line.append(email+" 邮件格式不正确").append(and);
            }

            if (line.toString().contains(and)){
                msgBuf.append(line.substring(0, line.length()-and.length())).append(br);
            }
        }


        if (msgBuf.toString().contains(br)){
           //
        }

        System.out.println("...........");

        System.out.println(msgBuf.toString());

    }
}
