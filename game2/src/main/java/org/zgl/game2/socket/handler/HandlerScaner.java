package org.zgl.game2.socket.handler;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import org.zgl.desc.ClinetProxy;
import org.zgl.game2.ProxyScan;
import org.zgl.service.regist.MethodModel;
import org.zgl.service.regist.ServiceInterfaceModel;
import org.zgl.service.regist.ServiceModel;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 猪哥亮
 */
@Component
public class HandlerScaner implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class<? extends Object> clazz = bean.getClass();
        Annotation proxy = clazz.getAnnotation(ClinetProxy.class);
        if (proxy instanceof ClinetProxy) {
            Class i = clazz.getInterfaces()[0];
            regist(clazz,i);
            ProxyScan.getInstance().getRegisterModels().putInterface(i.getName(),getRegistModel(i));
        }
        return bean;
    }

    private void regist(Class<? extends Object> clazz,Class<?> i) {
        Method[] methods = i.getDeclaredMethods();
        Map<String, Method> methodMap = new HashMap<>(methods.length);
        for (Method m : methods) {
            methodMap.put(m.getName(), m);
        }
        ProxyScan.getInstance().putModel(i.getName(), new ServiceModel(clazz, methodMap));
    }
    private ServiceInterfaceModel getRegistModel(Class<?> clazz) {
        Method[] m = clazz.getDeclaredMethods();
        try {
            ServiceInterfaceModel serviceInterfaceModel = new ServiceInterfaceModel();
            serviceInterfaceModel.setInterfaceName(clazz.getName());
            Map<String, MethodModel> map = new HashMap<>();
            for (Method mm : m) {
                MethodModel methodModel = new MethodModel();
                methodModel.setMethodName(mm.getName());
                methodModel.setParamentTypes(mm.getParameterTypes());
                map.put(mm.getName(), methodModel);
            }
            serviceInterfaceModel.setMethods(map);
            return serviceInterfaceModel;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}