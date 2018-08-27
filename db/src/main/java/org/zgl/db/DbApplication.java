package org.zgl.db;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.zgl.ProtostuffUtils;
import org.zgl.build.excel.read.ExcelUtils;
import org.zgl.common.HttpClientPostImpl;
import org.zgl.config.ConfigFactory;
import org.zgl.config.SocketUrlCfg;
import org.zgl.service.regist.RegisterModel;
@EnableScheduling
@MapperScan("org.zgl.db.dao.mapper")
@SpringBootApplication
public class DbApplication {

    public static void main(String[] args) throws Exception {
        ExcelUtils.init("excel");
        SpringApplication.run(DbApplication.class, args);
        RegisterModel list = ProxyScan.getInstance().getRegisterModels();
        SocketUrlCfg cfg = ConfigFactory.getInstance().getSocketUrlCfg();
        list.setHttpUrl(cfg.getDbHttp());
        //服务类注册到网关
        //启动之后将proxy的接口和方法发送到网关
        HttpClientPostImpl.getInstance().sendHttp(cfg.getGateHttpRegister(),cfg.getDbId(),ProtostuffUtils.serializer(list),false);
        ProxyScan.getInstance().clearRegisterModel();
    }
}
