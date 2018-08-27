package org.zgl.build.excel.read;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zgl.build.common.CheckType;
import org.zgl.common.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;

/**
 *
 */
public class ExcelUtils {
    private static final Logger logger = LoggerFactory.getLogger(ExcelUtils.class);
    private static Map<String,String> getFileName(String path)
    {
        Map<String,String> fileMap = new HashMap<>();
        File file = new File(path);
        String[] beanNames = file.list();
        File[] files = file.listFiles();
        if(files == null) {
            return new HashMap<>(0);
        }
        for (int i = 0;i<files.length;i++){
            String beanName = StringUtils.substringBeforeLast(beanNames[i],".");
            beanName = StringUtils.substringAfter(beanName,"_");
            String s = files[i].toString();
            String fileName = StringUtils.replace(s,"\\","/");
            fileMap.put(beanName,fileName);
        }
        return fileMap;
    }
    public static void init(String path) {
        Map<String,String> fileMap = getFileName(path);
        for (Map.Entry<String,String> entry:fileMap.entrySet()){
            ClassPathExcelContext(entry.getValue(),entry.getKey());
        }
    }

    public static void main(String[] args) {
        init("excel");
    }
    /**
     * 解析ecxel表
     */
    private static void ClassPathExcelContext(String fileName,String beanName) {
        //用于后面转对象使用
        List<List<String>> objs;
        objs = new ArrayList<>();
        Workbook book = null;
        FileInputStream fis = null;
        try {
            //取到文件
            fis = new FileInputStream(fileName);
            book = new XSSFWorkbook(fis);
            //及时关闭流
            fis.close();
            //取到工作谱（每一页是一个工作谱）
            Sheet sheet = book.getSheetAt(0);
            Iterator<Row> rows = sheet.iterator();
            List<String> obj = null;
            int i = 0;
            while (rows.hasNext()) {
                if(i == 0 || i == 3) {
                    ++i;
                    continue;
                }
                Row row = rows.next();
                Iterator<Cell> cells = row.iterator();
                obj = new ArrayList<>();
                while (cells.hasNext()) {
                    Cell cell = cells.next();
                    //根据cell中的类型来输出数据
                    switch (cell.getCellTypeEnum()) {
                        case NUMERIC:
                            //全部转换成string类型
                            cell.setCellType(CellType.STRING);
                            break;
                        default:
                            break;
                    }
                    obj.add(cell.getStringCellValue());
                }
                objs.add(obj);
                ++i;
            }
            serializerFile(objs, beanName);
        } catch (Exception e) {
            logger.error("excel表在读取"+fileName+"文件时出现异常-->",e);
        } finally {
            try {
                book.close();
            } catch (Exception e) {
                logger.error("excel表在关闭流-->",e);
            }
        }
    }
    private static void serializerFile(List<List<String>> file, String beanName) {
        List<Map<String, String>> objMap = new ArrayList<>();
        Map<String, String> objs;
        for (int i = 3; i < file.size(); i++) {
            objs = new HashMap<>();
            for (int j = 0; j < file.get(i).size(); j++) {
                objs.put(file.get(1).get(j), file.get(i).get(j));
            }
            objMap.add(objs);
        }
        serializerObj(objMap, beanName);
    }
    private static void serializerObj(List<Map<String, String>> objs, String clazzName) {
        Map<Serializable, Object> beanMap = new HashMap<>();
        Class clazz = null;
        Object beanObj = null;
        try {
            clazz = Class.forName(clazzName);
        } catch (ClassNotFoundException e) {
            logger.error("excel表在反射"+clazzName+"文件时出现异常，不存在这个类路径-->",e);
        }

        for (int i = 0; i < objs.size(); i++) {
            try {
                beanObj = clazz.getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                logger.error("excel表在反射初始化"+clazz+"对象时出现异常-->",e);
            }
            for (Map.Entry<String,String> e:objs.get(i).entrySet()){
                try {
                    Field field = beanObj.getClass().getDeclaredField(e.getKey());
                    field.setAccessible(true);
                    Field modifiersField = Field.class.getDeclaredField("modifiers");
                    modifiersField.setAccessible(true);
                    modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
                    Object valueObj = null;
                    //如果是基础数据类型或者string类型
                    if(field.getType().isPrimitive() || field.getType() == String.class){
                        if(field.getName().equals("id")){
                            float f = Float.valueOf(e.getValue());
                            int temp = (int) f;
                            valueObj = temp;
                        }else {
                            valueObj = CheckType.getType(field.getType(), e.getValue());
                        }
                    }else {
                        //如果是数组或者集合或者其他类型
                        valueObj = arrs(clazz,beanObj,e.getKey(),e.getValue());
                    }
                    if(valueObj == null) {
                        logger.error("excel表在反射初始化" + clazz + "类时出现异常,没有" + field + "的" + valueObj + "这个类型");
                    }
                    field.set(beanObj,valueObj);
                }catch (Exception ex){
                    logger.error("excel表在反射初始化"+clazz+"类时出现异常-->",ex);
                }
            }
            if(beanObj instanceof DataTableMessage) {
                beanMap.put(((DataTableMessage) beanObj).id(), beanObj);
                ((DataTableMessage) beanObj).afterInit();
            }
        }
        //添加到所有导表缓存类中
        StaticConfigMessage staticConfigMessage = StaticConfigMessage.getInstance();
        if(staticConfigMessage == null) {
            logger.error("获取静态初始化类" + staticConfigMessage + "时出现异常取不到这个类-->");
        }
        staticConfigMessage.put(beanObj.getClass(),beanMap);
    }
    private static Object arrs(Class<?> clazz,Object bean,String fieldName,String value){
        Object obj = null;
        try {
            Method method = clazz.getDeclaredMethod(fieldName+"4Init",new Class[]{String.class});
            method.setAccessible(true);
            obj = method.invoke(bean,value);
        } catch (Exception e) {
            logger.error("excel表在反射初始化"+clazz+"类时出现异常，不存在"+fieldName+"4Init方法-->",e);
        }
        return obj;
    }

}  