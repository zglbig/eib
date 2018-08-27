package org.zgl.service.regist;
import java.util.Map;

/**
 * @author ： big
 * @创建时间： 2018/6/30
 * @文件描述：注册的接口名 对应有哪些方法
 */
public class ServiceInterfaceModel {
    private String interfaceName;
    /**接口协议码对应的方法*/
    private Map<String,MethodModel> methods;
    public MethodModel getMethodModel(short methodCode){
        if(!methods.containsKey(methodCode)){
            throw new RuntimeException("没有->"+methodCode+"<-这个方法码对应的方法");
        }
        return methods.get(methodCode);
    }
    public MethodModel getMethodModel(String methodName){
        if(!methods.containsKey(methodName)){
            throw new RuntimeException("没有->"+methodName+"<-这个方法名对应的方法");
        }
        return methods.get(methodName);
    }
    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public Map<String, MethodModel> getMethods() {
        return methods;
    }

    public void setMethods(Map<String, MethodModel> methods) {
        this.methods = methods;
    }

//    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
//        Class<?> clazz = Thread.currentThread().getContextClassLoader().loadClass(TestMethod.class.getName());
//        Method[] m = clazz.getDeclaredMethods();
//        Class<?>[] c = m[0].getParameterTypes();
//        c[0].getDeclaredConstructor().newInstance();
//        System.out.println(c[0].equals(Integer.class));
//    }
}
