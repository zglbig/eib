package org.zgl.service.regist;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/8/13
 * @文件描述：
 */
public class ServiceModel {
    private Class<?> serviceImp;
    private Map<String,Method> serviceMethod;

    public ServiceModel() {
    }

    public ServiceModel(Class<?> serviceImp, Map<String, Method> serviceMethod) {
        this.serviceImp = serviceImp;
        this.serviceMethod = serviceMethod;
    }

    public Class<?> getServiceImp() {
        return serviceImp;
    }

    public void setServiceImp(Class<?> serviceImp) {
        this.serviceImp = serviceImp;
    }

    public Map<String, Method> getServiceMethod() {
        return serviceMethod;
    }

    public void setServiceMethod(Map<String, Method> serviceMethod) {
        this.serviceMethod = serviceMethod;
    }
    public Method getMethodByName(String methodName){
        return serviceMethod.getOrDefault(methodName,null);
    }
    public Class<?> getClazz(){
        return serviceImp;
    }
}
