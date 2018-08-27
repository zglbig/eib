package org.zgl.dto.clinet.game1;

import org.zgl.build.desc.Protostuff;
import org.zgl.dto.SerializeMessage;

import java.util.List;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/8/15
 * @文件描述：
 */
@Protostuff("game1")
public class Game1SattleEndDto implements SerializeMessage {
    private long winPlayerUid;
    private long winGold;
    private int winCardType;
    private long winPlayerHoldGold;
    private List<Integer> winCardId;
    private List<Game1SattleLoseInfoDto> loseInfo;

    public long getWinPlayerUid() {
        return winPlayerUid;
    }

    public void setWinPlayerUid(long winPlayerUid) {
        this.winPlayerUid = winPlayerUid;
    }

    public long getWinGold() {
        return winGold;
    }

    public void setWinGold(long winGold) {
        this.winGold = winGold;
    }

    public int getWinCardType() {
        return winCardType;
    }

    public void setWinCardType(int winCardType) {
        this.winCardType = winCardType;
    }

    public List<Integer> getWinCardId() {
        return winCardId;
    }

    public void setWinCardId(List<Integer> winCardId) {
        this.winCardId = winCardId;
    }

    public List<Game1SattleLoseInfoDto> getLoseInfo() {
        return loseInfo;
    }

    public void setLoseInfo(List<Game1SattleLoseInfoDto> loseInfo) {
        this.loseInfo = loseInfo;
    }

    public long getWinPlayerHoldGold() {
        return winPlayerHoldGold;
    }

    public void setWinPlayerHoldGold(long winPlayerHoldGold) {
        this.winPlayerHoldGold = winPlayerHoldGold;
    }
}
