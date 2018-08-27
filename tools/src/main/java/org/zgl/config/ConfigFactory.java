package org.zgl.config;

import java.util.Properties;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/8/15
 * @文件描述：
 */
public class ConfigFactory {
    private static ConfigFactory instance;
    private SocketUrlCfg socketUrlCfg;
    private GameVersion gameVersion;
    public static ConfigFactory getInstance() {
        if (instance == null) {
            instance = new ConfigFactory();
        }
        return instance;
    }

    private ConfigFactory() {
        initSocketCfg();
    }
    private void initGameVersion(){
        Properties pros = new Properties();
        socketUrlCfg = new SocketUrlCfg();
        try {
            pros.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("socketUrl.properties"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        gameVersion.setGameV(pros.getProperty("gameV"));
        gameVersion.setGameUrl(pros.getProperty("gameUrl"));
        gameVersion.setAbV(pros.getProperty("abV"));
        gameVersion.setAbUrl(pros.getProperty("abUrl"));
        gameVersion.setDataV(pros.getProperty("dataV"));
        gameVersion.setDataUrl(pros.getProperty("dataUrl"));
        gameVersion.setSecretKey(pros.getProperty("secretKey"));
        gameVersion.setSize(Long.valueOf(pros.getProperty("size")));
    }
    private void initSocketCfg() {
        Properties pros = new Properties();
        socketUrlCfg = new SocketUrlCfg();
        try {
            pros.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("socketUrl.properties"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        socketUrlCfg.setGameCount(Integer.valueOf(pros.getProperty("gameCount")));

        socketUrlCfg.setGateHttpHanler(pros.getProperty("gateHttpHanler"));
        socketUrlCfg.setGateHttpRegister(pros.getProperty("gateHttpRegister"));
        socketUrlCfg.setGateTcpIp(pros.getProperty("gateTcpIp"));
        socketUrlCfg.setGateId(Short.parseShort(pros.getProperty("gateId")));
        socketUrlCfg.setGateTcpPort(Integer.parseInt(pros.getProperty("gateTcpPort")));

        socketUrlCfg.setGame1Id(Short.parseShort(pros.getProperty("game1Id")));
        socketUrlCfg.setGame1Http(pros.getProperty("game1Http"));
        socketUrlCfg.setGame1TcpIp(pros.getProperty("game1Ip"));
        socketUrlCfg.setGame1TcpPort(Integer.parseInt(pros.getProperty("game1Port")));

        socketUrlCfg.setDbHttp(pros.getProperty("dbHttp"));
        socketUrlCfg.setDbId(Short.parseShort(pros.getProperty("dbId")));

        socketUrlCfg.setGame2Id(Short.parseShort(pros.getProperty("game2Id")));
        socketUrlCfg.setGame2Http(pros.getProperty("game2Http"));
        socketUrlCfg.setGame2TcpIp(pros.getProperty("game2Ip"));
        socketUrlCfg.setGame2TcpPort(Integer.parseInt(pros.getProperty("game2Port")));

        socketUrlCfg.setHallId(Short.parseShort(pros.getProperty("hallId")));
        socketUrlCfg.setHallHttp(pros.getProperty("hallHttp"));
        socketUrlCfg.setHallTcpIp(pros.getProperty("hallIp"));
        socketUrlCfg.setHallTcpPort(Integer.parseInt(pros.getProperty("hallPort")));

        socketUrlCfg.setGameLotteryId(Short.parseShort(pros.getProperty("gameLotteryId")));
        socketUrlCfg.setGameLotteryHttp(pros.getProperty("gameLotteryHttp"));
        socketUrlCfg.setGameLotteryTcpIp(pros.getProperty("gameLotteryIp"));
        socketUrlCfg.setGameLotteryTcpPort(Integer.parseInt(pros.getProperty("gameLotteryPort")));

        socketUrlCfg.setGameDiceId(Short.parseShort(pros.getProperty("gameDiceId")));
        socketUrlCfg.setGameDiceHttp(pros.getProperty("gameDiceHttp"));
        socketUrlCfg.setGameDiceTcpIp(pros.getProperty("gameDiceIp"));
        socketUrlCfg.setGameDiceTcpPort(Integer.parseInt(pros.getProperty("gameDicePort")));
    }

    public SocketUrlCfg getSocketUrlCfg() {
        return socketUrlCfg;
    }

    public GameVersion getGameVersion() {
        return gameVersion;
    }
}
