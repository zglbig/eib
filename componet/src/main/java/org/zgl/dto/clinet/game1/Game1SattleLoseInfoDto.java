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
public class Game1SattleLoseInfoDto implements SerializeMessage {
    private long loseUid;
    private long loseGold;
    private long loseHoldGold;
    private int loseCardType;
    private List<Integer> loseCardIds;

    public Game1SattleLoseInfoDto() {
    }

    public long getLoseUid() {
        return loseUid;
    }

    public long getLoseHoldGold() {
        return loseHoldGold;
    }

    public void setLoseHoldGold(long loseHoldGold) {
        this.loseHoldGold = loseHoldGold;
    }

    public void setLoseUid(long loseUid) {
        this.loseUid = loseUid;
    }

    public long getLoseGold() {
        return loseGold;
    }

    public void setLoseGold(long loseGold) {
        this.loseGold = loseGold;
    }

    public int getLoseCardType() {
        return loseCardType;
    }

    public void setLoseCardType(int loseCardType) {
        this.loseCardType = loseCardType;
    }

    public List<Integer> getLoseCardIds() {
        return loseCardIds;
    }

    public void setLoseCardIds(List<Integer> loseCardIds) {
        this.loseCardIds = loseCardIds;
    }
}
