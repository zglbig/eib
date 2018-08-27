package org.zgl.dto.clinet.dice;


import org.zgl.build.desc.Protostuff;
import org.zgl.dto.SerializeMessage;

@Protostuff("dice")
public class DiceCountDto implements SerializeMessage,Comparable<DiceCountDto> {
    private int one;
    private int two;
    /**场次*/
    private long battleCount;
    public DiceCountDto() {
    }

    public DiceCountDto(int one, int two, long battleCount) {
        this.one = one;
        this.two = two;
        this.battleCount = battleCount;
    }

    public int getOne() {
        return one;
    }

    public void setOne(int one) {
        this.one = one;
    }

    public int getTwo() {
        return two;
    }

    public long getBattleCount() {
        return battleCount;
    }

    public void setBattleCount(long battleCount) {
        this.battleCount = battleCount;
    }

    public void setTwo(int two) {
        this.two = two;
    }

    @Override
    public int compareTo(DiceCountDto o) {
        long temp = o.getBattleCount() - this.battleCount;
        if(temp < 0){
            return -1;
        }else if(temp == 0){
            return 0;
        }else {
            return 1;
        }
    }
}
