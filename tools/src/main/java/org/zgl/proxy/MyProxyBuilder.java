package org.zgl.proxy;

import org.zgl.common.GetFileAllInit;
import org.zgl.common.StringUtils;
import org.zgl.common.WriteFile;
import org.zgl.desc.ClinetProxy;
import org.zgl.proxy.rule.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

public class MyProxyBuilder {
    /**
     * 扫描所有代理接口
     * @param scanPath
     */
    public void scanAnnotation(String scanPath,int gameId,String createPath) throws ClassNotFoundException {
        List<Class> classList = GetFileAllInit.getClasssFromPackage(scanPath);
        if (classList.size()<=0) {
            return;
        }
        for (Class c:classList) {
            Annotation proxy = c.getAnnotation(ClinetProxy.class);
            if(proxy instanceof ClinetProxy){
                Class i = c.getInterfaces()[0];
                Class ic = i.getInterfaces()[0];
                CsModel cmdl = getObjectInfo(i,ic);
                String csClass = "";
                String icName = ic.getName();
                if(icName.equals(IHttpSyncService.class.getName()) ||
                        icName.equals(ITcpSyncService.class.getName()) ||
                        icName.equals(INotify.class.getName())){
                    csClass = syncSpliceCsClass(cmdl,gameId);
                }else if(icName.equals(IHttpAsyncService.class.getName()) ||
                        icName.equals(ITcpAsyncService.class.getName())){
                    csClass = asyncSpliceCsClass(cmdl,gameId);
                }
                if(csClass != null && !csClass.equals("")){
                    WriteFile.writeText(i.getSimpleName()+".cs",csClass,createPath);
                    System.out.println(csClass);
                }
            }
        }
    }
    private CsModel getObjectInfo(Class c2, Class c3) throws ClassNotFoundException {
        Class cs = Class.forName(c2.getName());
        Method[] methods = cs.getDeclaredMethods();
        List<CsMethodModel> methodModels = new ArrayList<>();
        for(Method m : methods){
            CsMethodModel csMethodModel = new CsMethodModel();
            //获取方法注释
            Annotation proxy = m.getAnnotation(MethodDesc.class);
            String desc = "";
            if(proxy instanceof MethodDesc) {
                MethodDesc classDesc = (MethodDesc) proxy;
                //获取接口注释
                desc = classDesc.value();
            }
            csMethodModel.setMethodDesc(desc);
            csMethodModel.setMethodName(m.getName());
            csMethodModel.setReturnType(m.getReturnType().getSimpleName());
            Parameter[] parameters = m.getParameters();
            ParamertModel[] paramertModels = new ParamertModel[parameters.length];
            for(int i = 0;i<paramertModels.length;i++){
                Parameter p = parameters[i];
                String str = p.getParameterizedType().getTypeName();
                String str1 = StringUtils.substringAfterLast(str,".");
                str1 = str1 == null || str1.equals("") ? str : str1;
                paramertModels[i] = new ParamertModel(str1,p.getName());
            }
            csMethodModel.setMethodTypes(paramertModels);
            methodModels.add(csMethodModel);
        }
        CsModel csModel = new CsModel();
        Annotation proxy = c2.getAnnotation(ClassDesc.class);
        String desc = "";
        if(proxy instanceof ClassDesc) {
            ClassDesc classDesc = (ClassDesc) proxy;
            //获取接口注释
            desc = classDesc.value();
        }
        csModel.setClassDesc(desc);
        csModel.setInterfaceName(c2.getName());
        csModel.setImplementsInterfaceName(c3.getSimpleName());
        csModel.setMethodModels(methodModels);
        return csModel;
    }

    /**
     * 异步 需要回调方法
     * @param csModels
     */
    private String asyncSpliceCsClass(CsModel csModels,int gameId){
        StringBuilder sb = new StringBuilder();
        String desc = classDesc(csModels.getClassDesc());
        if(desc != null) {
            sb.append(desc);
        }
        String s = csModels.getInterfaceName();
        sb.append("namespace "+StringUtils.substringBeforeLast(s,".")+"{\n");
        s = StringUtils.substringAfterLast(s,".");
        if(csModels.getImplementsInterfaceName() != null || !csModels.getImplementsInterfaceName().equals("")) {
            s += " : " + csModels.getImplementsInterfaceName();
        }
        sb.append("\t[Proxy(gameId = "+gameId+")]\n");
        sb.append("\tpublic interface "+ s + " { \n");
        for(CsMethodModel cmd : csModels.getMethodModels()){
            String sss = methodDesc(cmd.getMethodDesc());
            if(sss != null){
                sb.append(sss);
            }
            sb.append("\t\tvoid " + cmd.getMethodName() + "(");
            String s1 = "";
            for(int i = 0;i<cmd.getMethodTypes().length;i++){
                String str = cmd.getMethodTypes()[i].getType();
                if(str.equals("String"))
                    str = "string";
                else if(str.equals("boolean"))
                    str = "bool";
                s1 += str + " " + cmd.getMethodTypes()[i].getName() + ",";
            }
            s1 = s1 == null || s1.equals("") ? "" : StringUtils.substringBeforeLast(s1,",");
            sb.append(s1);
            sb.append(");\n");
            String s2 = "";
            String returnType = cmd.getReturnType();
            if(!returnType.equals("void")){
                if(returnType.equals("String")) {
                    returnType = "string";
                }else if(returnType.equals("boolean")) {
                    returnType = "bool";
                }
                s2 = returnType + " callBackParam";
            }
            if(sss != null){
                sb.append(callBackMethodDesc(cmd.getMethodDesc(),cmd.getMethodName()));
            }
            sb.append("\t\tvoid " + cmd.getMethodName() + "2CallBack" + "(" + s2 + ");\n");
        }
        sb.append("\t}\n");
        sb.append("}");
        return sb.toString();
    }
    /**
     * 拼接cs对象
     * @param csModels
     */
    private String syncSpliceCsClass(CsModel csModels,int gameId){
        StringBuilder sb = new StringBuilder();
        String desc = classDesc(csModels.getClassDesc());
        if(desc != null) {
            sb.append(desc);
        }
        String s = csModels.getInterfaceName();
        sb.append("namespace "+StringUtils.substringBeforeLast(s,".")+"{\n");
        s = StringUtils.substringAfterLast(s,".");
        if(csModels.getImplementsInterfaceName() != null || !csModels.getImplementsInterfaceName().equals("")) {
            s += " : " + csModels.getImplementsInterfaceName();
        }
        sb.append("\t[Proxy(gameId = "+gameId+")]\n");
        sb.append("\tpublic interface "+ s + " { \n");
        for(CsMethodModel cmd : csModels.getMethodModels()){
            String sss = methodDesc(cmd.getMethodDesc());
            if(sss != null){
                sb.append(sss);
            }
            String returnType = cmd.getReturnType();
            if(returnType.equals("String")) {
                returnType = "string";
            }else if(returnType.equals("boolean")) {
                returnType = "bool";
            }
            sb.append("\t\t"+returnType + " " + cmd.getMethodName() + "(");
            String s1 = "";
            for(int i = 0;i<cmd.getMethodTypes().length;i++){
                String str = cmd.getMethodTypes()[i].getType();
                if(str.equals("String"))
                    str = "string";
                else if(str.equals("boolean"))
                    str = "bool";
                s1 += str + " " + cmd.getMethodTypes()[i].getName() + ",";
            }
            s1 = s1 == null || s1.equals("") ? "" : StringUtils.substringBeforeLast(s1,",");
            sb.append(s1);
            sb.append(");\n");
        }
        sb.append("\t}\n");
        sb.append("}");
        return sb.toString();
    }

    private String classDesc(String s){
        if(s == null && s.equals("")) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("/// <summary>\n");
        sb.append("/// " + s + "\n");
        sb.append("/// </summary>\n");
        return sb.toString();
    }
    private String methodDesc(String s){
        if(s == null && s.equals("")) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("\t\t/// <summary>\n");
        sb.append("\t\t/// " + s + "\n");
        sb.append("\t\t/// </summary>\n");
        return sb.toString();
    }
    private String callBackMethodDesc(String s,String methodName){
        if(s == null && s.equals("")) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("\t\t/// <summary>\n");
        sb.append("\t\t/// " + s +" ----> <<"+ methodName+"();回调>>\n");
        sb.append("\t\t/// </summary>\n");
        return sb.toString();
    }

    public static void main(String[] args) throws ClassNotFoundException {
        MyProxyBuilder builder = new MyProxyBuilder();
        builder.scanAnnotation("org.zgl",-8,"E://GameGroup//framework//cs//proxy");
//        System.out.println(getParamterName(TestImpl.class,"test2"));
    }
}