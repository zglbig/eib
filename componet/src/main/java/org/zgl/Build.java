package org.zgl;

import org.zgl.build.excel.create.BuilderExcelInversion;
import org.zgl.build.excel.create.ExcelCreateByCs;
import org.zgl.build.pbcreate.BuilderProtostuff;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/8/13
 * @文件描述：
 */
public class Build {
    public static void main(String[] args) throws ClassNotFoundException {
        BuilderExcelInversion.builder("org.zgl.error");
        BuilderProtostuff.builder("org.zgl");
        ExcelCreateByCs.builder("org.zgl");
//        ExcelUtils.init("excel");
    }
}
