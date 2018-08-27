package org.zgl.dto.clinet.game2;

import org.zgl.build.desc.Protostuff;
import org.zgl.dto.SerializeMessage;

/**
 * 历史记录
 */
@Protostuff("game2")
public class Game2HistoryDto implements SerializeMessage {
    /**当前第几局*/
    private long count;
    private boolean one;
    private boolean two;
    private boolean three;
    private boolean four;

    public Game2HistoryDto() {
    }

    public Game2HistoryDto(long count, boolean one, boolean two, boolean three, boolean four) {
        this.count = count;
        this.one = one;
        this.two = two;
        this.three = three;
        this.four = four;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public boolean isOne() {
        return one;
    }

    public void setOne(boolean one) {
        this.one = one;
    }

    public boolean isTwo() {
        return two;
    }

    public void setTwo(boolean two) {
        this.two = two;
    }

    public boolean isThree() {
        return three;
    }

    public void setThree(boolean three) {
        this.three = three;
    }

    public boolean isFour() {
        return four;
    }

    public void setFour(boolean four) {
        this.four = four;
    }
}
