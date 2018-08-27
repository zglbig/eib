package org.zgl.game;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.zgl.ProtostuffUtils;
import org.zgl.build.excel.read.ExcelUtils;
import org.zgl.common.HttpClientPostImpl;
import org.zgl.config.ConfigFactory;
import org.zgl.config.SocketUrlCfg;
import org.zgl.game.socket.tcp.GameServer;
import org.zgl.service.regist.RegisterModel;

@SpringBootApplication
public class GameApplication {

    public static void main(String[] args) throws Exception {
        ExcelUtils.init("excel");
        SpringApplication.run(GameApplication.class, args);
        SocketUrlCfg cfg = ConfigFactory.getInstance().getSocketUrlCfg();
        RegisterModel list = ProxyScan.getInstance().getRegisterModels();
        list.setHttpUrl(cfg.getGame1Http());
        //服务类注册到网关
        //启动之后将proxy的接口和方法发送到网关
        HttpClientPostImpl.getInstance().sendHttp(cfg.getGateHttpRegister(),cfg.getGame1Id(),ProtostuffUtils.serializer(list),false);
        ProxyScan.getInstance().clearRegisterModel();
        GameServer.getInstance().start();
    }
}
