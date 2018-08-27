package org.zgl.gate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.zgl.build.excel.read.ExcelUtils;
import org.zgl.gate.tcp.TcpApplication;
@EnableScheduling
@SpringBootApplication
public class GateApplication {

    public static void main(String[] args) {
        //静态数据加载
        ExcelUtils.init("excel");
        SpringApplication.run(GateApplication.class, args);
        System.err.println("http网关启动成功");
        TcpApplication.run();
    }
}
