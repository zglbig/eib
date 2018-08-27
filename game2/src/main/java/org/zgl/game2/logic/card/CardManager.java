package org.zgl.game2.logic.card;

import org.zgl.ArrayUtils;
import org.zgl.build.excel.read.StaticConfigMessage;
import org.zgl.datable.CardDataTable;
import org.zgl.game2.logic.room.HandCard;

import java.util.*;

public class CardManager {
    /**线程安全的懒汉式单利模式*/
    private static class SingletonHolder{
        public final static CardManager instance = new CardManager();
    }
    public static CardManager getInstance(){
        return SingletonHolder.instance;
    }
    /**牌id对应的牌*/
    private final Map<Integer,CardDataTable> map;
    /**牌面对应的牌*/
    private final Map<Integer,List<CardDataTable>> cardFaceMap;
    private CardManager(){
        map = new HashMap<>();
        cardFaceMap = new HashMap<>();
        List<Object> cards = new ArrayList<>(StaticConfigMessage.getInstance().getMap(CardDataTable.class).values());
        for(Object o : cards){
            CardDataTable ccc = (CardDataTable) o;
            map.putIfAbsent(((CardDataTable) o).getId(),ccc);
            List<CardDataTable> cf = cardFaceMap.getOrDefault(ccc.getFace(),null);
            if(cf == null){
                cf = new ArrayList<>(4);
                cardFaceMap.put(ccc.getFace(),cf);
            }
            cf.add(ccc);
        }
    }

    public HandCard getCardType(HandCard c){
        if(AAA(c)) {
            return c;
        }else if(leopard(c)) {
            return c;
        }else if(straightFlush(c)) {
            return c;
        }else if(sameColor(c)) {
            return c;
        }else if(straight(c)) {
            return c;
        } else if(pair(c)) {
            return c;
        }else if(highCard(c)) {
            return c;
        }
        return c;
    }

    /**
     * 比较大小
     * @param
     * @return
     */
    public void compareCard(HandCard self, HandCard other){
        int selfType = self.getCardType();
        int otherType = other.getCardType();
        boolean compareResult = false;
        if(selfType > otherType){
            compareResult = true;
        }else if(selfType == otherType){
            compareHighCart(self,other);
            return;
        }else {
            compareResult = false;
        }
        self.setCompareResult(compareResult);
        other.setCompareResult(!compareResult);
    }
    private void compareHighCart(HandCard self, HandCard other){
        boolean selfResult = false;
        //最大的相等
        if(self.getMax() == other.getMax()){
            //第二个的相等
            if(self.getCardFaces()[1] .equals(other.getCardFaces()[1])){
                //最小的相等
                if(self.getMin() == other.getMin()){
                    //根据牌面取牌id
                    int selfMaxId = getIdForFace(self.getCardIds(),cardFaceMap.getOrDefault(self.getMax(),null));
                    int otherMaxId = getIdForFace(other.getCardIds(),cardFaceMap.getOrDefault(other.getMax(),null));
                    if(selfMaxId == -1 || otherMaxId == -1) {
                        throw new NullPointerException("没有->" + Arrays.toString(self.getCardIds()) + "<-和->" + Arrays.toString(other.getCardIds()) + "<-对应的牌->selfMaxFace->" + self.getMax() + "<-otherMax->" + other.getMax());
                    }
                    //比黑 红 梅 方
                    selfResult = map.get(selfMaxId).getType() < map.get(otherMaxId).getType();
                }else {
                    selfResult = self.getMin() > other.getMin();
                }
            }else {
                selfResult = self.getCardFaces()[1] > other.getCardFaces()[1];
            }
        }else {
            selfResult = self.getMax() > other.getMax();
        }
        self.setCompareResult(selfResult);
        other.setCompareResult(!selfResult);
    }
    private int getIdForFace(Integer[] ids,List<CardDataTable> dataTables){
        if(ids == null || dataTables == null) {
            return -1;
        }
        for(int i = 0;i<dataTables.size();i++){
            int id = dataTables.get(i).getId();
            if(ArrayUtils.contains(ids,id)){
                return id;
            }
        }
        return -1;
    }
    /**
     * 散牌
     * @param c
     */
    public boolean highCard(HandCard c){
        Integer[] cardFace = c.getCardFaces();
        Arrays.sort(cardFace);
        c.setCardType(CardType.HIGH_CARD.id());
        c.setMax(cardFace[2]);
        c.setMin(cardFace[0]);
        return true;
    }

    /**
     * 对子
     * @return
     */
    public boolean pair(HandCard c){
        Integer[] cardFace = c.getCardFaces();
        Arrays.sort(cardFace);
        for(int i = 0;i<cardFace.length - 1;i++){
            if(cardFace[i] .equals(cardFace[i+1])) {
                c.setCardType(CardType.PAIR.id());
                if(cardFace[0].equals(cardFace[1])){
                    c.setMin(cardFace[2]);
                }else {
                    c.setMin(cardFace[0]);
                }
                c.setMax(cardFace[1]);
                return true;
            }
        }
        return false;
    }
    public static final Integer[] A12 = new Integer[]{14,2,3};
    //顺子
    public boolean straight(HandCard c){
        Integer[] cardFace = c.getCardFaces();
        Arrays.sort(cardFace);
        if(ArrayUtils.contains(cardFace,A12)){
            c.setCardType(CardType.STRAIGHT.id());
            c.setMax(cardFace[2]);
            c.setMin(cardFace[0]);
            return true;
        }
        for(int i = 0;i<cardFace.length - 1;i++){
            if(cardFace[i] - cardFace[i+1] != -1) {
                return false;
            }
        }
        c.setCardType(CardType.STRAIGHT.id());
        c.setMax(cardFace[2]);
        c.setMin(cardFace[0]);
        return true;
    }
    //同花
    public boolean sameColor(HandCard c){
        Integer ids[] = c.getCardIds();
        for(int i=0;i<ids.length-1;i++){
            if(map.get(ids[i]).getType() != map.get(ids[i+1]).getType()) {
                return false;
            }
        }
        Integer[] cardFace = c.getCardFaces();
        Arrays.sort(cardFace);
        c.setCardType(CardType.SAME_COLOR.id());
        c.setMax(cardFace[2]);
        c.setMin(cardFace[0]);
        return true;
    }
    /**
     * 同花顺
     * @return
     */
    public boolean straightFlush(HandCard c){
        if(!sameColor(c) || !straight(c)) {
            return false;
        }
        Integer[] cardFace = c.getCardFaces();
        Arrays.sort(cardFace);
        c.setCardType(CardType.STRAIGHT_FLUSH.id());
        c.setMax(cardFace[2]);
        c.setMin(cardFace[0]);
        return true;
    }

    /**
     * 豹子
     * @return
     */
    public boolean leopard(HandCard c){
        Integer[] cardFace = c.getCardFaces();
        Arrays.sort(cardFace);
        if(cardFace[0] .equals(cardFace[cardFace.length-1])) {
            c.setCardType(CardType.LEOPARD.id());
            c.setMax(cardFace[2]);
            c.setMin(cardFace[0]);
            return true;
        }
        return false;
    }
    /**
     * 三张A
     * @return
     */
    public boolean AAA(HandCard c){
        Integer[] cardFace = c.getCardFaces();
        if(!leopard(c)) {
            return false;
        }
        if(cardFace[0] == 1) {
            c.setCardType(CardType.AAA.id());
            c.setMax(cardFace[2]);
            c.setMin(cardFace[0]);
            return true;
        }
        return false;
    }

}
