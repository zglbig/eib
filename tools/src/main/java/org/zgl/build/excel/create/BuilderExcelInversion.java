package org.zgl.build.excel.create;

import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.xssf.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zgl.build.common.CodeModel;
import org.zgl.build.desc.ExcelInversionToAnn;
import org.zgl.build.desc.ExcelValue;
import org.zgl.common.GetFileAllInit;

import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.*;

/**
 * @author
 */
public class BuilderExcelInversion {
    private static final Logger logger = LoggerFactory.getLogger(BuilderExcelInversion.class);
    /**
     * 获取拥有Service注解的所有类
     *
     * @param path 包路径
     * @return
     */
    public static void builder(String path) {
        List<Class> clazzs = GetFileAllInit.getClasssFromPackage(path);
        Set<CodeModel> excelSet = new HashSet<>();
        if (clazzs.size() <= 0) {
            return;
        }
        for (Class c : clazzs) {
            Annotation excel = c.getAnnotation(ExcelInversionToAnn.class);
            if (excel instanceof ExcelInversionToAnn) {
                ExcelInversionToAnn proco1 = (ExcelInversionToAnn) excel;
                excelSet.add(new CodeModel("","", c));
            }
        }
        reflectBean(excelSet);
    }

    private static void reflectBean(Set<CodeModel> excelSet) {
        if (excelSet.size() <= 0) {
            return;
        }
        Iterator<CodeModel> iterator = excelSet.iterator();
        while (iterator.hasNext()) {
            Object o = null;
            try {
                CodeModel model = iterator.next();

                o = model.getClazz().getDeclaredConstructor().newInstance();
                reflectField(o);
            } catch (Exception e) {
                logger.error("反射对象是出现异常", e);
            }
        }
    }

    private static void reflectField(Object o) {
        List<ExcelErrorCodeModule> modules = new ArrayList<>();
        List<String> ids = new ArrayList<>();
        Field[] fields = o.getClass().getDeclaredFields();
        if (fields.length <= 0) {
            return;
        }
        for (Field f : fields) {
            Annotation ann = f.getAnnotation(ExcelValue.class);
            String name = f.getName();
            String value = "";
            String id = "";
            try {
                //获取字段的值
                id = f.get(o).toString();
            } catch (IllegalAccessException e) {
                logger.error("获取id时出现异常", e);
            }
            if (ann instanceof ExcelValue) {
                ExcelValue ev = (ExcelValue) ann;
                value = ev.value();
            }
            if (ids.contains(id)) {
                throw new RuntimeException("id为->" + id + "<-重复，请先检查并更改之后在操作");
            }
            modules.add(new ExcelErrorCodeModule(Integer.parseInt(id), name, value));
        }
        Collections.sort(modules);
        inversion(o, modules);
    }

    private static void inversion(Object o, List<ExcelErrorCodeModule> modules) {
        //创建HSSFWorkbook
        XSSFWorkbook wb = new XSSFWorkbook();
        //第二步创建sheet
        XSSFSheet sheet = wb.createSheet(o.getClass().getSimpleName());
        //第三步创建行row:添加表头0行
        XSSFRow row = null;
        for (int i = 0; i < 3; i++) {
            row = sheet.createRow(i);
            XSSFCellStyle style = wb.createCellStyle();

            //居中
            style.setAlignment(HorizontalAlignment.CENTER);
            //第四步创建单元格
            //第一个单元格
            XSSFCell cell = row.createCell(0);
            //设定值
            cell.setCellValue("id");
            //内容居中
            cell.setCellStyle(style);
            //第二个单元格
            cell = row.createCell(1);
            cell.setCellValue("name");
            cell.setCellStyle(style);
            //第三个单元格
            cell = row.createCell(2);
            cell.setCellValue("value");
            cell.setCellStyle(style);
        }
        //第五步插入数据
        List<ExcelErrorCodeModule> list = modules;
        for (int i = 0; i < list.size(); i++) {
            ExcelErrorCodeModule errorCondition = list.get(i);
            //创建行
            row = sheet.createRow(i + 3);
            //创建单元格并且添加数据
            row.createCell(0).setCellValue(errorCondition.getId());
            row.createCell(1).setCellValue(errorCondition.getName());
            row.createCell(2).setCellValue(errorCondition.getValue());
        }

        //第六步将生成excel文件保存到指定路径下
        try {
            //首字母小写
            FileOutputStream fout = new FileOutputStream("E://GameGroup//framework//cs//error//appErrorDatable_org.zgl.error.AppErrorDatable.xlsx");
            wb.write(fout);
            fout = new FileOutputStream("E://GameGroup//framework//excel//appErrorDatable_org.zgl.error.AppErrorDatable.xlsx");
            wb.write(fout);
            fout.close();
        } catch (IOException e) {
            logger.error("将生成excel文件保存到指定路径下时出现异常", e);
        }

    }
}
