package org.zgl.service.regist;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @作者： big
 * @创建时间： 2018/6/30
 * @文件描述：单个服务器所有的接口
 */
public class RegisterModel {
    /**所有接口码对应的所有接口*/
    private String httpUrl;
    private Map<String,ServiceInterfaceModel> codeMap;

    public Map<String, ServiceInterfaceModel> getCodeMap() {
        return codeMap;
    }
    public void setCodeMap(Map<String, ServiceInterfaceModel> codeMap) {
        this.codeMap = codeMap;
    }
    public ServiceInterfaceModel getServiceInterfaceModel(String interfaceName){
        ServiceInterfaceModel s = codeMap.get(interfaceName);
        if(s == null) {
            throw new NullPointerException("没有:" + interfaceName + "-->这个接口吗对应的服务类");
        }
        return s;
    }
    public void putInterface(String interfaceName,ServiceInterfaceModel model){
        if(codeMap == null){
            codeMap = new ConcurrentHashMap<>();
        }
        if(!codeMap.containsKey(interfaceName)){
            codeMap.put(interfaceName,model);
        }
    }

    public String getHttpUrl() {
        return httpUrl;
    }

    public void setHttpUrl(String httpUrl) {
        this.httpUrl = httpUrl;
    }
}
