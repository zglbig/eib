package org.zgl.dto.clinet.game2;

import org.zgl.build.desc.Protostuff;
import org.zgl.dto.SerializeMessage;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/8/16
 * @文件描述：
 */
@Protostuff("game2")
public class Game2BetUpdateWeathDto implements SerializeMessage {
    private long reduceGold;
    private long betGold;
    private int betPosition;

    public Game2BetUpdateWeathDto() {
    }

    public Game2BetUpdateWeathDto(long reduceGold, long betGold, int betPosition) {
        this.reduceGold = reduceGold;
        this.betGold = betGold;
        this.betPosition = betPosition;
    }

    public long getReduceGold() {
        return reduceGold;
    }

    public void setReduceGold(long reduceGold) {
        this.reduceGold = reduceGold;
    }

    public long getBetGold() {
        return betGold;
    }

    public void setBetGold(long betGold) {
        this.betGold = betGold;
    }

    public int getBetPosition() {
        return betPosition;
    }

    public void setBetPosition(int betPosition) {
        this.betPosition = betPosition;
    }
}
