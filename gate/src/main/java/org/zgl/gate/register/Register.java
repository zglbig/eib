package org.zgl.gate.register;

import org.zgl.config.ConfigFactory;
import org.zgl.service.regist.RegisterModel;
import org.zgl.service.regist.ServiceInterfaceModel;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @作者： big
 * @创建时间： 2018/6/30
 * @文件描述：注册中心
 */
public class Register {
    //线程安全的懒汉式单利模式
    private static class SingletonHolder {
        public final static Register instance = new Register();
    }
    public static Register getInstance() {
        return SingletonHolder.instance;
    }
    /**所有服务器对应的接口*/
    private final Map<Short,RegisterModel> registerMap;
    private final ReentrantReadWriteLock lock;
    private final Set<Short> gameIds;
    private final int gameCount;
    private Register() {
        this.gameCount = ConfigFactory.getInstance().getSocketUrlCfg().getGameCount();
        this.gameIds = new HashSet<>(gameCount);
        this.lock = new ReentrantReadWriteLock();
        this.registerMap = new ConcurrentHashMap<>();
    }

    public void putAll(Short gameId, RegisterModel model){
        lock.writeLock().lock();
        try {
            registerMap.put(gameId,model);
            gameIds.add(gameId);
        }finally {
            lock.writeLock().unlock();
        }
    }
    public boolean removeRegist(short gameId){
        lock.writeLock().lock();
        try {
            registerMap.remove(gameId);
            return true;
        }finally {
            lock.writeLock().unlock();
        }
    }
    public boolean isInitSuccess(){
        return gameIds.size() == gameCount;
    }
    public Map<Short,RegisterModel> getMap(){
        return registerMap;
    }
    public RegisterModel getRegisterModel(short gameId){
        RegisterModel r = registerMap.get(gameId);
        if(r == null){
            throw new RuntimeException("没有："+gameId+"：这个服务器码对应的服务器");
        }
        return r;
    }
    public String getUrl(short gameId){
        if(registerMap.containsKey(gameId)){
            RegisterModel model = registerMap.get(gameId);
            return model.getHttpUrl();
        }else {
            throw new RuntimeException("没有"+gameId+"对应的服务器");
        }
    }
    /**
     * 获取某个服务器某个接口
     * @param gameId 服务器id
     * @param interfaceName 接口码
     * @return
     */
    public ServiceInterfaceModel getServiceInterfaceModel(short gameId, String interfaceName){
        return getRegisterModel(gameId).getServiceInterfaceModel(interfaceName);
    }

    public List<String> allServerUrl(){
        List<String> url = new ArrayList<>(registerMap.size());
        for(Map.Entry<Short,RegisterModel> e: registerMap.entrySet()){
            if(e.getKey() == ConfigFactory.getInstance().getSocketUrlCfg().getDbId()){
                continue;
            }
            url.add(e.getValue().getHttpUrl());
        }
        return url;
    }
}
