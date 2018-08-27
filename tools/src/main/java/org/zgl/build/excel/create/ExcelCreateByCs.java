package org.zgl.build.excel.create;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zgl.build.common.CheckType;
import org.zgl.build.common.CodeModel;
import org.zgl.build.desc.DataTable;
import org.zgl.build.desc.OverlookField;
import org.zgl.common.GetFileAllInit;
import org.zgl.common.StringUtils;
import org.zgl.common.WriteFile;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.*;

public class ExcelCreateByCs {
    private static final Logger logger = LoggerFactory.getLogger(ExcelCreateByCs.class);
    /**
     * 获取拥有Service注解的所有类
     * @param path 包路径
     * @return
     */
    public static void builder(String path){
        List<Class> clazzs = GetFileAllInit.getClasssFromPackage(path);
        Set<CodeModel> protocolSet = new HashSet<>();
        if (clazzs.size()<=0) {
            return;
        }
        for (Class c:clazzs) {
            Annotation proco = c.getAnnotation(DataTable.class);
            if(proco instanceof DataTable){
                DataTable proco1 = (DataTable) proco;
                protocolSet.add(new CodeModel(proco1.value(),"",c));
            }
        }
        reflectBean(protocolSet);
    }
    /**
     * 反射对象
     * @param beans
     */
    private static void reflectBean(Set<CodeModel> beans){
        if (beans.size()<=0){ return;}
        Iterator<CodeModel> iterator = beans.iterator();
        while (iterator.hasNext()){
            Object o = null;
            try {
                CodeModel model = iterator.next();
                o = model.getClazz().getDeclaredConstructor().newInstance();
                List<String> bean = reflectField(model.getId(), o);
                assemblyBean(o.getClass().getSimpleName(), bean);
            } catch (Exception e) {
                logger.error("",e);
            }
        }
    }
    /**
     * 获取对象属性名并生成协议类（java、C#）
     */
    private static List<String> reflectField(String id,Object o) {
        Field[] fields = o.getClass().getDeclaredFields();
        List<String> sb = new ArrayList<>();
        if (fields.length<=0) {
            return null;
        }
        String beanName = o.getClass().getName();
        sb.add("namespace "+ StringUtils.substringBeforeLast(beanName,".")+"{\n");
        String clazz = "\tpublic class "+o.getClass().getSimpleName()+" : DataTableMessage {\n";
        sb.add(clazz);
        sb.add("\t\tpublic int Id(){\n\t\t\treturn id;\n\t\t}\n");
        sb.add("\t\tpublic static "+o.getClass().getSimpleName()+" get(int id){\n" +
                "\t\t\treturn StaticConfigMessage.Instance.get<"+o.getClass().getSimpleName()+">(typeof("+o.getClass().getSimpleName()+"),id);\n\t\t}\n");
        for (int i = 0;i< fields.length;i++){
            //忽略生成字段
            if(checkOverlook(fields[i])) {
                continue;
            }
            //属性名
            String field = fields[i].getName();
            String s = "\t\tpublic "+CheckType.typeStr(fields[i],beanName)+" "+field+";"+"\n";
            sb.add(s);
        }
        sb.add("\t\tpublic void AfterInit(){}\n");
        sb.add("\t}\n");
        sb.add("}");
        return sb;
    }
    private static boolean checkOverlook(Field f){
        Annotation ann = f.getAnnotation(OverlookField.class);
        return  (ann instanceof OverlookField);
    }
    /**
     * 拼装对象
     */
    private static void assemblyBean(String beanName, List<String> s){
        StringBuilder sb = new StringBuilder();
        sb.append("using System;\n");
        sb.append("using System.Collections.Generic;\n");
        for (String str:s){
            sb.append(str);
        }
        WriteFile.writeText(beanName+".cs",sb.toString(),"E://GameGroup//framework//cs//data//db//");
    }
}
