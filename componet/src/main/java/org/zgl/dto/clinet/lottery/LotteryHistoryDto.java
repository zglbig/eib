package org.zgl.dto.clinet.lottery;

import org.zgl.build.desc.Protostuff;
import org.zgl.dto.SerializeMessage;

import java.util.List;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/8/17
 * @文件描述：
 */
@Protostuff("lottery")
public class LotteryHistoryDto implements SerializeMessage {
    /**第几场*/
    private long num;
    /**出牌结果*/
    private int result;
    /**本局单双 8双 9单*/
    private int oddEnven;
    /**上期奖励*/
    private long lastTimeGrantAward;
    /**牌的id*/
    private List<Integer> cardIds;

    public LotteryHistoryDto() {
    }

    public LotteryHistoryDto(long num, int result, int oddEnven, List<Integer> cardIds) {
        this.num = num;
        this.result = result;
        this.oddEnven = oddEnven;
        this.cardIds = cardIds;
    }

    public long getNum() {
        return num;
    }

    public void setNum(long num) {
        this.num = num;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public int getOddEnven() {
        return oddEnven;
    }

    public void setOddEnven(int oddEnven) {
        this.oddEnven = oddEnven;
    }

    public List<Integer> getCardIds() {
        return cardIds;
    }

    public long getLastTimeGrantAward() {
        return lastTimeGrantAward;
    }

    public void setLastTimeGrantAward(long lastTimeGrantAward) {
        this.lastTimeGrantAward = lastTimeGrantAward;
    }

    public void setCardIds(List<Integer> cardIds) {
        this.cardIds = cardIds;
    }
}
