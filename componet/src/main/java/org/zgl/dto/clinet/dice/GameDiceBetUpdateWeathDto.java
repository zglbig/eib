package org.zgl.dto.clinet.dice;

import org.zgl.build.desc.Protostuff;
import org.zgl.dto.SerializeMessage;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/8/18
 * @文件描述：
 */
@Protostuff("dice")
public class GameDiceBetUpdateWeathDto implements SerializeMessage {
    private long residueGold;
    private long betGold;
    private int betPosition;

    public GameDiceBetUpdateWeathDto() {
    }

    public GameDiceBetUpdateWeathDto(long reduceGold, long betGold, int betPosition) {
        this.residueGold = reduceGold;
        this.betGold = betGold;
        this.betPosition = betPosition;
    }

    public long getResidueGold() {
        return residueGold;
    }

    public void setResidueGold(long residueGold) {
        this.residueGold = residueGold;
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
