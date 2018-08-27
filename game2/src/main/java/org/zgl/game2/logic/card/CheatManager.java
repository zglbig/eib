package org.zgl.game2.logic.card;

import org.zgl.ArrayUtils;
import org.zgl.game2.logic.room.HandCard;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CheatManager {
    private final static List<Integer> l;
    static {
        l = new ArrayList<>();
        for(int i = 1;i<5;i++){
            l.add(i);
        }
    }
    public List<Integer> getL(){
        return new ArrayList<>(l);
    }
    private static HandCard[] cheat(Map<Integer,HandCard> handCardMap){
        HandCard[] handCards = ArrayUtils.arrForList(new ArrayList<>(handCardMap.values()),HandCard.class);
        //对所有手牌进行排序
        int length = handCards.length;
        //冒泡大到小排序
//        for(int i = 0;i<length;i++){
//            for(int j = 1;j<length - i;j++){
//                HandCard h1 = handCards[j-1];
//                HandCard h2 = handCards[j];
//                CardManager.getInstance().compareCard(h1,h2);
//                if(h1.isCompareResult()){
//                    HandCard temp = h1;
//                    handCards[j-1] = h2;
//                    handCards[j] = temp;
//                }
//            }
//        }
        //优化后的冒泡大到小排序
//        int j,k;
//        int flag = length;
//        while (flag > 0){
//            k = flag;
//            flag = 0;
//            for(j = 1;j<k;j++){
//                HandCard h1 = handCards[j-1];
//                HandCard h2 = handCards[j];
//                CardManager.getInstance().compareCard(h1,h2);
//                if(h1.isCompareResult()){
//                    HandCard temp = h1;
//                    handCards[j-1] = h2;
//                    handCards[j] = temp;
//
//                    flag = j;
//                }
//            }
//        }
        return handCards;
    }

    /**
     * index之前全设置为赢的，之后为输的
     * @param handCards
     * @param index
     */
    public void getHandCard(HandCard[] handCards, int index){
        for(int i = 0;i<index;i++){
            handCards[i].setCompareResult(true);
        }
        for(int i = index;i<handCards.length;i++){
            handCards[i].setCompareResult(false);
        }
    }

}
