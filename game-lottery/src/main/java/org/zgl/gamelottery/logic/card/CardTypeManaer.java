package org.zgl.gamelottery.logic.card;

import org.zgl.ArrayUtils;
import org.zgl.RandomUtils;
import org.zgl.build.excel.read.ExcelUtils;
import org.zgl.build.excel.read.StaticConfigMessage;
import org.zgl.datable.CardDataTable;

import java.util.*;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/8/11
 * @文件描述：作弊牌型管理
 */
public class CardTypeManaer {
    private static class SingletonHolder {
        public final static CardTypeManaer instance = new CardTypeManaer();
    }

    public static CardTypeManaer getInstance() {
        return SingletonHolder.instance;
    }
    /**
     * 千万场的牌型 只有7以上的大牌
     */
    private final Map<Integer, CardDataTable> bigCardMap;
    /**
     * 牌id对应牌的map
     */
    private final Map<Integer, CardDataTable> allCardIdMap;


    private CardTypeManaer() {
        List<Object> cards = new ArrayList<>(StaticConfigMessage.getInstance().getMap(CardDataTable.class).values());
        allCardIdMap = new HashMap<>(cards.size());
        bigCardMap = new HashMap<>();
        for (Object o : cards) {
            CardDataTable ccc = (CardDataTable) o;
            //所有牌id对应的牌
            allCardIdMap.put(((CardDataTable) o).getId(), ccc);
            if (ccc.getFace() > 7) {
                //千王场的大牌
                bigCardMap.put(ccc.getId(), ccc);
            }
        }
    }

    public Map<Integer, CardDataTable> getAllCardIdMap() {
        return new HashMap<>(allCardIdMap);
    }

    public List<CardDataTable> allCardList() {
        return new ArrayList<>(allCardIdMap.values());
    }
    public CardDataTable getCard(int id){
        return allCardIdMap.get(id);
    }
    public List<CardDataTable> bigCardList() {
        return new ArrayList<>(bigCardMap.values());
    }

    public List<CardDataTable> getAllCardList() {
        return new ArrayList<>(allCardIdMap.values());
    }

    public List<CardDataTable> shuff(int num, int scenesType) {
        List<CardDataTable> c = null;
        switch (scenesType) {
            case 1:
                c = new ArrayList<>(allCardList());
                break;
            case 2:
                c = new ArrayList<>(bigCardList());
                break;
            default:
                c = new ArrayList<>(allCardList());
                break;
        }
        Collections.shuffle(c);
        return c.subList(0, num);
    }

    public CardDataTable[] getCards(Map<Integer, CardDataTable> map, int cardType) {
        switch (cardType) {
            case 7:
                return getAAA(map);
            case 6:
                return getLeopard(map);
            case 5:
                return getStraightFlush(map);
            case 4:
                return getSameColorCard(map);
            case 3:
                return getStraight(map);
            case 2:
                return getPair(map);
            case 1:
                return getHigh(map);
            default:
                return getHigh(map);
        }
    }

    /**
     * 获取AAA
     *
     * @param map
     * @return
     */
    public CardDataTable[] getAAA(Map<Integer, CardDataTable> map) {
        List<CardDataTable> aaa = new ArrayList<>(4);
        for (Map.Entry<Integer, CardDataTable> entry : map.entrySet()) {
            if (entry.getValue().getFace() == 14) {
                aaa.add(entry.getValue());
            }
        }
        if (aaa.size() < 3) {
            new RuntimeException("牌不够组成AAA");
        }
        aaa = aaa.subList(0, 3);
        for (CardDataTable c : aaa) {
            map.remove(c.getId());
        }
        return ArrayUtils.arrForList(aaa, CardDataTable.class);
    }

    /**
     * 从一组牌中获取豹子 也包括aaa
     *
     * @return
     */
    public CardDataTable[] getLeopard(Map<Integer, CardDataTable> map) {
        Map<Integer, List<CardDataTable>> cardFaceMap = getCardFaceMap(map);
        //可以组成豹子的所有牌的id
        Iterator<Map.Entry<Integer, List<CardDataTable>>> iterator = cardFaceMap.entrySet().iterator();
        while (iterator.hasNext()) {
            //将不够三个的牌移除
            List<CardDataTable> list = new ArrayList<>(iterator.next().getValue());
            if (list.size() < 3) {
                iterator.remove();
//                //优化之后
//                list = list.subList(0, 3);
//                for (CardDataTable c : list) {
//                    map.remove(c.getId());
//                }
//                return ArrayUtils.arrForList(list, CardDataTable.class);
            }
        }
        List<List<CardDataTable>> leopardList = new ArrayList<>(cardFaceMap.values());
        if (leopardList.size() < 1) {
            throw new RuntimeException("牌不够");
        }
        int random = RandomUtils.getRandom(0, leopardList.size() - 1);
        List<CardDataTable> resultLeopard = leopardList.get(random);
        resultLeopard = resultLeopard.subList(0, 3);
        for (CardDataTable c : resultLeopard) {
            map.remove(c.getId());
        }
        return ArrayUtils.arrForList(resultLeopard, CardDataTable.class);
    }

    /**
     * 同花顺子
     *
     * @param map
     * @return
     */
    public CardDataTable[] getStraightFlush(Map<Integer, CardDataTable> map) {
        //1先找同花
        Map<Integer, List<CardDataTable>> cardTypeMap = getSameColor(map);
        //2再从同花里找顺子
        //所有能组成同花顺子的牌组
        List<List<CardDataTable>> allStraightFlushList = new ArrayList<>();
        //找出所有的同花顺
        for (Map.Entry<Integer, List<CardDataTable>> entry : cardTypeMap.entrySet()) {
            if (entry.getValue().size() < 3) {
                continue;
            }
            List<CardDataTable> list = entry.getValue();
            Map<Integer, CardDataTable> map1 = getStraightFlush(list);
            List<Integer> cardIds = new ArrayList<>(map1.keySet());
            Collections.sort(cardIds);
            //排同花
            for (int i = 0; i < cardIds.size() - 3; i++) {
                int[] cardIdsTemp = new int[3];
                for (int j = 0; j < 3; j++) {
                    cardIdsTemp[j] = cardIds.get(i + j);
                }
                if (isStraight(cardIdsTemp)) {
                    //如果时顺子
                    List<CardDataTable> straightFlush = new ArrayList<>(3);
                    for (int j = 0; j < cardIdsTemp.length; j++) {
                        straightFlush.add(map.get(cardIdsTemp[j]));
                    }

                    //优化 只要找到一个立马返回不在往下找
//                    for (CardDataTable c : straightFlush) {
//                        map.remove(c.getId());
//                    }
//                    return ArrayUtils.arrForList(straightFlush, CardDataTable.class);
                    allStraightFlushList.add(straightFlush);
                }
            }
        }
        if (allStraightFlushList.size() < 1) {
            throw new RuntimeException("牌不够");
        }
        //随机拿个取一个
        int random = RandomUtils.getRandom(0, allStraightFlushList.size() - 1);
        List<CardDataTable> resultList = allStraightFlushList.get(random);
        for (CardDataTable c : resultList) {
            map.remove(c.getId());
        }
        return ArrayUtils.arrForList(resultList, CardDataTable.class);
    }

    /**
     * 找同花但不是顺子的牌
     *
     * @param map
     * @return
     */
    public CardDataTable[] getSameColorCard(Map<Integer, CardDataTable> map) {
        //1先找同花

        Map<Integer, List<CardDataTable>> cardTypeMap = getSameColor(map);
        //再找不是顺子的牌
        //所有同花但又不是顺子的牌集合
        List<List<CardDataTable>> allSameColorList = new ArrayList<>();
        for (Map.Entry<Integer, List<CardDataTable>> entry : cardTypeMap.entrySet()) {
            if (entry.getValue().size() < 3) {
                continue;
            }
            List<CardDataTable> list = entry.getValue();
            Map<Integer, CardDataTable> map1 = getStraightFlush(list);
            List<Integer> cardIds = new ArrayList<>(map1.keySet());
            //洗牌
            Collections.shuffle(cardIds);
            //排同花
            for (int i = 0; i < cardIds.size() - 3; i++) {
                int[] cardIdsTemp = new int[3];
                for (int j = 0; j < 3; j++) {
                    cardIdsTemp[j] = cardIds.get(i + j);
                }
                if (!isStraight(cardIdsTemp)) {
                    //如果时顺子
                    List<CardDataTable> sameColor = new ArrayList<>(3);
                    for (int j = 0; j < cardIdsTemp.length; j++) {
                        sameColor.add(map.get(cardIdsTemp[j]));
                    }

                    //优化 只要找到一个立马返回不在往下找
//                    for (CardDataTable c : sameColor) {
//                        map.remove(c.getId());
//                    }
//                    return ArrayUtils.arrForList(sameColor, CardDataTable.class);
                    allSameColorList.add(sameColor);
                }
            }
        }
        if (allSameColorList.size() < 1) {
            throw new RuntimeException("牌不够");
        }
        //随机拿个取一个
        int random = RandomUtils.getRandom(0, allSameColorList.size() - 1);
        List<CardDataTable> resultList = allSameColorList.get(random);
        for (CardDataTable c : resultList) {
            map.remove(c.getId());
        }
        return ArrayUtils.arrForList(resultList, CardDataTable.class);
    }

    /**
     * 找普通顺子
     *
     * @param map
     * @return
     */
    public CardDataTable[] getStraight(Map<Integer, CardDataTable> map) {
        Map<Integer, List<CardDataTable>> cardFaceMap = getCardFaceMap(map);
        //找出所有顺子但不是同花的牌
        List<List<CardDataTable>> allStraightList = new ArrayList<>();
        List<Integer> cardFaceList = new ArrayList<>(cardFaceMap.keySet());
        //洗牌
        Collections.sort(cardFaceList);
        //排同花
        for (int i = 0; i < cardFaceList.size() - 3; i++) {
            int[] cardFacesTemp = new int[3];
            for (int j = 0; j < 3; j++) {
                cardFacesTemp[j] = cardFaceList.get(i + j);
            }
            if (isStraight(cardFacesTemp)) {
                //列出所有的顺子再除去同花顺
                List<List<CardDataTable>> listTemp = new ArrayList<>(3);
                for (int cardFaceVar : cardFacesTemp) {
                    listTemp.add(cardFaceMap.get(cardFaceVar));
                }
                if (listTemp.size() < 3) {
                    continue;
                }
                for (CardDataTable cl : listTemp.get(0)) {
                    for (CardDataTable cl1 : listTemp.get(1)) {
                        for (CardDataTable cl2 : listTemp.get(2)) {
                            List<CardDataTable> straight = new ArrayList<>(3);
                            straight.add(cl);
                            straight.add(cl1);
                            straight.add(cl2);
                            //除去同花顺
                            if (!isStraightFlush(straight)) {
                                //优化 只要找到一个立马返回不在往下找
//                                for (CardDataTable c : straight) {
//                                    map.remove(c.getId());
//                                }
//                                return ArrayUtils.arrForList(straight, CardDataTable.class);
                                allStraightList.add(straight);
                            }
                        }
                    }
                }
            }
        }
        if (allStraightList.size() < 1) {
            throw new RuntimeException("牌不够");
        }
        //随机拿个取一个
        int random = RandomUtils.getRandom(0, allStraightList.size() - 1);
        List<CardDataTable> resultList = allStraightList.get(random);
        for (CardDataTable c : resultList) {
            map.remove(c.getId());
        }
        return ArrayUtils.arrForList(resultList, CardDataTable.class);
    }

    /**
     * 找对子
     *
     * @param map
     * @return
     */
    public CardDataTable[] getPair(Map<Integer, CardDataTable> map) {
        Map<Integer, List<CardDataTable>> cardFace = getCardFaceMap(map);
        List<List<CardDataTable>> allPair = new ArrayList<>();
        Iterator<Map.Entry<Integer, List<CardDataTable>>> iterator = cardFace.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Integer, List<CardDataTable>> entry = iterator.next();
            if (entry.getValue().size() < 2) {
                iterator.remove();
            }
        }
        for (List<CardDataTable> l : cardFace.values()) {
            for (int i = 0; i < l.size() - 1; i++) {
                for (CardDataTable c : map.values()) {
                    if (c.getFace() != l.get(i).getFace() && c.getId() != l.get(i).getId() && c.getId() != l.get(i + 1).getId()) {
                        List<CardDataTable> temp = new ArrayList<>(3);
                        temp.add(l.get(i));
                        temp.add(l.get(i + 1));
                        temp.add(c);

//                        优化 只要找到一个立马返回不在往下找
//                        for (CardDataTable cc : temp) {
//                            map.remove(cc.getId());
//                        }
//                        return ArrayUtils.arrForList(temp, CardDataTable.class);
                        allPair.add(temp);
                    }
                }
            }
        }
        //删除同花 删除顺子
        //随机拿个取一个
        int random = RandomUtils.getRandom(0, allPair.size() - 1);
        List<CardDataTable> resultList = allPair.get(random);
        for (CardDataTable c : resultList) {
            map.remove(c.getId());
        }
        return ArrayUtils.arrForList(resultList, CardDataTable.class);
    }

    /**
     * 获取散牌
     *
     * @param map
     * @return
     */
    public CardDataTable[] getHigh(Map<Integer, CardDataTable> map) {
        List<CardDataTable> allCard = new ArrayList<>(map.values());
        List<List<CardDataTable>> allHigh = new ArrayList<>();
        Collections.shuffle(allCard);
        if (allCard.size() < 3) {
            throw new RuntimeException("牌不够");
        }
        int tag = 0;
        while (true) {
            for (int i = 0; i < allCard.size() - 3; i++) {
                List<CardDataTable> temp = new ArrayList<>(3);
                temp.add(allCard.get(i));
                temp.add(allCard.get(i + 1));
                temp.add(allCard.get(i + 2));
                //不是同花 不是顺子 不是对子
                if (!isStraightFlush(temp) && !isStraight(temp) && !isPair(temp)) {
                    //优化 只要找到一个立马返回不在往下找
//                    for (CardDataTable c : temp) {
//                        map.remove(c.getId());
//                    }
//                    return ArrayUtils.arrForList(temp, CardDataTable.class);
                    allHigh.add(temp);
                }
            }
            if (allHigh.size() > 0 || tag > 3) {
                break;
            }
            tag++;
        }

        if (allHigh.size() < 1) {
            throw new RuntimeException("牌不够");
        }
        //随机拿个取一个
        int random = RandomUtils.getRandom(0, allHigh.size() - 1);
        List<CardDataTable> resultList = allHigh.get(random);
        for (CardDataTable c : resultList) {
            map.remove(c.getId());
        }
        return ArrayUtils.arrForList(resultList, CardDataTable.class);
    }

    /**
     * 获取map中牌面对应的牌
     *
     * @param map
     * @return
     */
    private Map<Integer, List<CardDataTable>> getCardFaceMap(Map<Integer, CardDataTable> map) {
        Map<Integer, List<CardDataTable>> cardFaceMap = new HashMap<>(13);
        for (CardDataTable ccc : map.values()) {
            List<CardDataTable> cf = cardFaceMap.getOrDefault(ccc.getFace(), null);
            if (cf == null) {
                cf = new ArrayList<>(4);
                cardFaceMap.put(ccc.getFace(), cf);
            }
            cf.add(ccc);
        }
        return cardFaceMap;
    }

    /**
     * 找同花
     *
     * @param map
     * @return
     */
    private Map<Integer, List<CardDataTable>> getSameColor(Map<Integer, CardDataTable> map) {
        Map<Integer, List<CardDataTable>> cardTypeMap = new HashMap<>(4);
        for (CardDataTable ccc : map.values()) {
            List<CardDataTable> c = cardTypeMap.getOrDefault(ccc.getType(), null);
            if (c == null) {
                c = new ArrayList<>(13);
                cardTypeMap.put(ccc.getType(), c);
            }
            c.add(ccc);
        }
        return cardTypeMap;
    }

    /**
     * 从同花转换成map
     *
     * @param list
     * @return
     */
    private Map<Integer, CardDataTable> getStraightFlush(List<CardDataTable> list) {
        Map<Integer, CardDataTable> map = new HashMap<>();
        for (CardDataTable c : list) {
            map.put(c.getId(), c);
        }
        return map;
    }

    /**
     * 是否是顺子
     *
     * @param cardIds
     * @return
     */
    private boolean isStraight(int[] cardIds) {
        Arrays.sort(cardIds);
        for (int i = 0, j = cardIds.length - 1; i < j; i++) {
            if (cardIds[i] - cardIds[i + 1] != -1) {
                return false;
            }
        }
        return true;
    }

    /**
     * 是否是同花
     *
     * @param list
     * @return
     */
    private boolean isStraightFlush(List<CardDataTable> list) {
        for (int i = 0; i < list.size() - 1; i++) {
            if (list.get(i).getType() != list.get(i + 1).getType()) {
                return false;
            }
        }
        return true;
    }

    /**
     * 是否是顺子
     *
     * @param list
     * @return
     */
    private boolean isStraight(List<CardDataTable> list) {
        int[] cardIds = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            cardIds[i] = list.get(i).getFace();
        }
        Arrays.sort(cardIds);
        for (int i = 0, j = cardIds.length - 1; i < j; i++) {
            if (cardIds[i] - cardIds[i + 1] != -1) {
                return false;
            }
        }
        return true;
    }

    private boolean isPair(List<CardDataTable> list) {
        int[] cardIds = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            cardIds[i] = list.get(i).getFace();
        }
        Arrays.sort(cardIds);
        for (int i = 0, j = cardIds.length - 1; i < j; i++) {
            if (cardIds[i] == cardIds[i + 1]) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        ExcelUtils.init("excel");
        long start = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            Map<Integer, CardDataTable> map = getInstance().getAllCardIdMap();
            CardDataTable[] a = getInstance().getAAA(map);
//            System.out.println(Arrays.toString(a) + " <-aaa");
            CardDataTable[] b = getInstance().getLeopard(map);
//            System.out.println(Arrays.toString(b) + " <-豹子");
            CardDataTable[] b1 = getInstance().getLeopard(map);
//            System.out.println(Arrays.toString(b1) + " <-豹子");
            CardDataTable[] b2 = getInstance().getLeopard(map);
//            System.out.println(Arrays.toString(b2) + " <-豹子");
            CardDataTable[] b3 = getInstance().getLeopard(map);
//            System.out.println(Arrays.toString(b3) + " <-豹子");
            CardDataTable[] b4 = getInstance().getLeopard(map);
//            System.out.println(Arrays.toString(b4) + " <-豹子");
        }
        System.out.println("查找豹子50000次耗时>" + (System.currentTimeMillis() - start));
        long start1 = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            Map<Integer, CardDataTable> map = getInstance().getAllCardIdMap();
            CardDataTable[] c = getInstance().getStraightFlush(map);
//            System.out.println(Arrays.toString(c) + " <-同花顺");
            CardDataTable[] c1 = getInstance().getStraightFlush(map);
//            System.out.println(Arrays.toString(c1) + " <-同花顺");
            CardDataTable[] c2 = getInstance().getStraightFlush(map);
//            System.out.println(Arrays.toString(c2) + " <-同花顺");
            CardDataTable[] c3 = getInstance().getStraightFlush(map);
//            System.out.println(Arrays.toString(c3) + " <-同花顺");
            CardDataTable[] c4 = getInstance().getStraightFlush(map);
//            System.out.println(Arrays.toString(c4) + " <-同花顺");
        }
        System.out.println("查找同花顺50000次耗时>" + (System.currentTimeMillis() - start1));
        long start2 = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            Map<Integer, CardDataTable> map = getInstance().getAllCardIdMap();
            CardDataTable[] d = getInstance().getSameColorCard(map);
//            System.out.println(Arrays.toString(d) + " <-花");
            CardDataTable[] d1 = getInstance().getSameColorCard(map);
//            System.out.println(Arrays.toString(d1) + " <-花");
            CardDataTable[] d2 = getInstance().getSameColorCard(map);
//            System.out.println(Arrays.toString(d2) + " <-花");
            CardDataTable[] d3 = getInstance().getSameColorCard(map);
//            System.out.println(Arrays.toString(d3) + " <-花");
            CardDataTable[] d4 = getInstance().getSameColorCard(map);
//            System.out.println(Arrays.toString(d4) + " <-花");
        }
        System.out.println("查找同花50000次耗时>" + (System.currentTimeMillis() - start2));
        long start3 = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            Map<Integer, CardDataTable> map = getInstance().getAllCardIdMap();
            CardDataTable[] e = getInstance().getStraight(map);
//            System.out.println(Arrays.toString(e) + " <-顺");
            CardDataTable[] e1 = getInstance().getStraight(map);
//            System.out.println(Arrays.toString(e1) + " <-顺");
            CardDataTable[] e2 = getInstance().getStraight(map);
//            System.out.println(Arrays.toString(e2) + " <-顺");
            CardDataTable[] e3 = getInstance().getStraight(map);
//            System.out.println(Arrays.toString(e3) + " <-顺");
            CardDataTable[] e4 = getInstance().getStraight(map);
//            System.out.println(Arrays.toString(e4) + " <-顺");
        }
        System.out.println("查找顺子50000次耗时>" + (System.currentTimeMillis() - start3));
        long start4 = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            Map<Integer, CardDataTable> map = getInstance().getAllCardIdMap();
            CardDataTable[] f = getInstance().getPair(map);
//            System.out.println(Arrays.toString(f) + " <-对");
            CardDataTable[] f1 = getInstance().getPair(map);
//            System.out.println(Arrays.toString(f1) + " <-对");
            CardDataTable[] f2 = getInstance().getPair(map);
//            System.out.println(Arrays.toString(f2) + " <-对");
            CardDataTable[] f3 = getInstance().getPair(map);
//            System.out.println(Arrays.toString(f3) + " <-对");
            CardDataTable[] f4 = getInstance().getPair(map);
//            System.out.println(Arrays.toString(f4) + " <-对");
        }
        System.out.println("查找对子50000次耗时>" + (System.currentTimeMillis() - start4));
        long start5 = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            Map<Integer, CardDataTable> map = getInstance().getAllCardIdMap();
            CardDataTable[] g = getInstance().getHigh(map);
//            System.out.println(Arrays.toString(g) + " <-散");
            CardDataTable[] g1 = getInstance().getHigh(map);
//            System.out.println(Arrays.toString(g1) + " <-散");
            CardDataTable[] g2 = getInstance().getHigh(map);
//            System.out.println(Arrays.toString(g2) + " <-散");
            CardDataTable[] g3 = getInstance().getHigh(map);
//            System.out.println(Arrays.toString(g3) + " <-散");
            CardDataTable[] g4 = getInstance().getHigh(map);
//            System.out.println(Arrays.toString(g4) + " <-散");
        }
        System.out.println("查找散牌50000次耗时>" + (System.currentTimeMillis() - start5));
    }
}
