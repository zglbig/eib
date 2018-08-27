package org.zgl.build.pbcreate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zgl.build.common.CheckType;
import org.zgl.build.common.CodeModel;
import org.zgl.build.desc.OverlookField;
import org.zgl.build.desc.Protostuff;
import org.zgl.common.GetFileAllInit;
import org.zgl.common.WriteFile;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.*;

public class BuilderProtostuff {
    private static final Logger logger = LoggerFactory.getLogger(BuilderProtostuff.class);

    /**
     * 获取拥有Service注解的所有类
     *
     * @param path 包路径
     * @return
     */
    public static void builder(String path) {
        List<Class> clazzs = GetFileAllInit.getClasssFromPackage(path);
        Set<CodeModel> set = new HashSet<>();
        if (clazzs.size() <= 0) {
            return;
        }
        for (Class c : clazzs) {
            Annotation proco = c.getAnnotation(Protostuff.class);
            if (proco instanceof Protostuff) {
                Protostuff proco1 = (Protostuff) proco;
                set.add(new CodeModel(proco1.value(), "", c));
            }
        }
        reflectBean(set);
    }

    /**
     * 反射对象
     *
     * @param
     */
    private static void reflectBean(Set<CodeModel> set) {
        if (set.size() <= 0) {
            return;
        }
        Iterator<CodeModel> iterator = set.iterator();
        while (iterator.hasNext()) {
            Object o = null;
            try {
                CodeModel model = iterator.next();
                o = model.getClazz().getDeclaredConstructor().newInstance();
                List<String> bean = reflectField(model.getId(), o);
                builder(o.getClass().getSimpleName(), bean, model.getCreatePath());
            }catch (Exception e) {
                logger.error("",e);
            }
        }
    }

    /**
     * 获取对象属性名并生成协议类（java、C#）
     */
    private static List<String> reflectField(String id, Object o) {
        Field[] fields = o.getClass().getDeclaredFields();
        List<String> sb = new ArrayList<>();
        if (fields.length <= 0) {
            return null;
        }
        String beanName = o.getClass().getName();
        String clazz = "public class " + o.getClass().getSimpleName() + " : SerializeMessage {\n";
        sb.add(clazz);
        for (int i = 0; i < fields.length; i++) {
            //忽略生成字段
            if (checkOverlook(fields[i])) {
                continue;
            }
            //属性名
            String field = fields[i].getName();
            String s = "\tpublic " + CheckType.typeStr(fields[i], beanName) + " " + field + ";" + "\n";
            sb.add(s);
        }
        sb.add("}");
        return sb;
    }

    /**
     * 是否可以忽略生成该字段
     *
     * @return
     */
    private static boolean checkOverlook(Field f) {
        Annotation ann = f.getAnnotation(OverlookField.class);
        return (ann instanceof OverlookField);
    }

    private static void builder(String beanName, List<String> s, String createPath) {
        StringBuilder sb = new StringBuilder();
        sb.append("using System;\n");
        sb.append("using System.Collections.Generic;\n");
        sb.append("using ProtoBuf;\n");
        sb.append("[ProtoContract]\n");
        for (int i = 0; i < s.size(); i++) {
            sb.append(s.get(i));
            if (i < s.size() - 2) {
                sb.append("\t[ProtoMember(" + (i + 1) + ")]" + "\n");
            }
        }
        WriteFile.writeText(beanName + ".cs", sb.toString(), "E://GameGroup//framework//cs//dto//" + createPath);
    }

}
