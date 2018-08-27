package org.zgl.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class WriteFile {
    private static final Logger logger = LoggerFactory.getLogger(WriteFile.class);
    /**
     * 生成cs文件
     * @param beanName
     * @param content
     */
    public static void writeText(String beanName,String content,String path){
        BufferedWriter ow = null;
        try {
            //文件保存路径
            String Divpath = path;
            File dirFile = new File(path);
            //文件路径不存在时，自动创建目录
            if(!dirFile.exists()){
                dirFile.mkdir();
            }
            //文件名字
            String clazzName = Divpath+"//"+beanName;
            File file = new File(clazzName);
            //创建一个使用指定大小输出缓冲区的新缓冲字符输出流
            ow = new BufferedWriter(new FileWriter(file));
            ow.write(content);
            ow.newLine();
        }catch (Exception e){
            logger.error("数据写入异常",e);
        }finally {
            try {
                if(ow != null) {
                    ow.close();
                }
            }catch (Exception e){
                logger.error("关闭流异常",e);
            }
        }
    }
}