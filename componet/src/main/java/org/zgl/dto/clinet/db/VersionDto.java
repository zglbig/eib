package org.zgl.dto.clinet.db;

import org.zgl.build.desc.Protostuff;
import org.zgl.dto.SerializeMessage;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/8/20
 * @文件描述：
 */
@Protostuff("db")
public class VersionDto implements SerializeMessage {
    /**最新游戏版本号*/
    private String gameV;
    /**游戏下载地址*/
    private String gameUrl;
    /**最新静态数据版本号*/
    private String dataV;
    /**静态数据下载地址*/
    private String dataUrl;
    /**最新ab包版本号*/
    private String abV;
    /**ab下载地址*/
    private String abUrl;
    /**md5码*/
    private String secretKey;
    /**包大小*/
    private long size;

    public String getGameV() {
        return gameV;
    }

    public void setGameV(String gameV) {
        this.gameV = gameV;
    }

    public String getGameUrl() {
        return gameUrl;
    }

    public void setGameUrl(String gameUrl) {
        this.gameUrl = gameUrl;
    }

    public String getDataV() {
        return dataV;
    }

    public void setDataV(String dataV) {
        this.dataV = dataV;
    }

    public String getDataUrl() {
        return dataUrl;
    }

    public void setDataUrl(String dataUrl) {
        this.dataUrl = dataUrl;
    }

    public String getAbV() {
        return abV;
    }

    public void setAbV(String abV) {
        this.abV = abV;
    }

    public String getAbUrl() {
        return abUrl;
    }

    public void setAbUrl(String abUrl) {
        this.abUrl = abUrl;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }
}
