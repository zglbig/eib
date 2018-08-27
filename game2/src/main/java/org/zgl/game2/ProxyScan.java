package org.zgl.game2;
import org.zgl.config.ConfigFactory;
import org.zgl.proxy.MyProxyBuilder;
import org.zgl.service.regist.RegisterModel;
import org.zgl.service.regist.ServiceModel;

import java.util.HashMap;
import java.util.Map;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/7/26
 * @文件描述：
 */
public class ProxyScan {
    /**
     * 注 当前并不支持重载方法
     */
    private final Map<String, ServiceModel> operationMap = new HashMap<>();
    private static RegisterModel REGISTER_MODELS = new RegisterModel();
    private static ProxyScan instance;

    public static ProxyScan getInstance() {
        if(instance == null){
            instance = new ProxyScan();
        }
        return instance;
    }

    public RegisterModel getRegisterModels() {
        return REGISTER_MODELS;
    }

    public void clearRegisterModel() {
        REGISTER_MODELS = null;
    }



    public ServiceModel getServiceModel(String interfaceName) {
        return operationMap.getOrDefault(interfaceName, null);
    }

    public void putModel(String interfaceName, ServiceModel model) {
        operationMap.put(interfaceName, model);
    }
    public static void main(String[] args) throws ClassNotFoundException {
//        ExcelUtils.init("excel");
        MyProxyBuilder builder = new MyProxyBuilder();
        builder.scanAnnotation("org.zgl",ConfigFactory.getInstance().getSocketUrlCfg().getGame2Id(),"E://GameGroup//framework//cs//proxy//game2");
    }
}
