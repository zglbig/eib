package org.zgl.build.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zgl.build.cmdcreate.BuilderProtocol;
import org.zgl.build.excel.create.BuilderExcelInversion;
import org.zgl.build.excel.create.ExcelCreateByCs;
import org.zgl.build.pbcreate.BuilderProtostuff;

import java.util.Properties;

public class BuilderAll {
    private static final Logger logger = LoggerFactory.getLogger(BuilderAll.class);
    public static final PathCnf PATH_CNF;
    static {
        Properties pros = new Properties();
        PATH_CNF = new PathCnf();
        try {
            pros.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("code_builder_path.properties"));
        } catch (Exception e) {
            logger.error("加载code_builder_path.properties时出现异常",e);
        }
        PATH_CNF.setScanPath(pros.getProperty("scanPath"));
        PATH_CNF.setDtoCsPath(pros.getProperty("dtoCsPath"));
        PATH_CNF.setCommandCodePath(pros.getProperty("commandCodePath"));
        PATH_CNF.setOperateCommandRecivePath(pros.getProperty("operateCommandRecivePath"));
        PATH_CNF.setErrorCodeExcelPath(pros.getProperty("errorCodeExcelPath"));
        PATH_CNF.setOperateCommandRecivePackage(pros.getProperty("operateCommandRecivePackage"));
        PATH_CNF.setOperateCommandAbstractPackage(pros.getProperty("operateCommandAbstractPackage"));
        PATH_CNF.setCommandCodePackage(pros.getProperty("commandCodePackage"));
        PATH_CNF.setDatableCsBuildPath(pros.getProperty("datableCsBuildPath"));
    }

    public static void main(String[] args) {
        BuilderProtocol.builder(PATH_CNF.getScanPath());
        logger.info("--------------通讯协议号生成完成-------------------");
        BuilderProtostuff.builder(PATH_CNF.getScanPath());
        logger.info("--------------通讯协议类生成完成-------------------");
        BuilderExcelInversion.builder(PATH_CNF.getScanPath());
        logger.info("--------------创建excel表完成----------------------");
        ExcelCreateByCs.builder(PATH_CNF.getScanPath());
        logger.info("--------------excel表对应的类生成完成--------------");
    }
}
