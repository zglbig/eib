package org.zgl.service.regist;


/**
 * @作者： big
 * @创建时间： 2018/7/9
 * @文件描述：方法的模型
 */
public class MethodModel {
    private String methodName;
    private short methodCode;
    /**方法的所有参数类型*/
    private Class<?>[] paramentTypes;
    public Class<?>[] getParamentTypes() {
        return paramentTypes;
    }

    public short getMethodCode() {
        return methodCode;
    }

    public void setMethodCode(short methodCode) {
        this.methodCode = methodCode;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public void setParamentTypes(Class<?>[] paramentTypes) {
        this.paramentTypes = paramentTypes;
    }
}
