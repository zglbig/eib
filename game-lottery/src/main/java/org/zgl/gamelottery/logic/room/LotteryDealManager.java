package org.zgl.gamelottery.logic.room;

import org.zgl.build.excel.read.StaticConfigMessage;
import org.zgl.datable.CardDataTable;
import org.zgl.datable.LotteryDataTable;
import org.zgl.dto.clinet.lottery.LotteryHistoryDto;
import org.zgl.gamelottery.logic.card.CardTypeManaer;
import org.zgl.weightRandom.WeightRandom;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @作者： big
 * @创建时间： 2018/5/24
 * @文件描述：
 */
public class LotteryDealManager {
    private static LotteryDealManager instance;
    private static final List<LotteryDataTable> AH_DATA;

    static {
        Map<Serializable, Object> o = StaticConfigMessage.getInstance().getMap(LotteryDataTable.class);
        AH_DATA = new ArrayList<>(o.size());
        for (Object ox : o.values()) {
            AH_DATA.add((LotteryDataTable) ox);
        }
    }

    private final CardTypeManaer cardTypeManaer;

    public static LotteryDealManager getInstance() {
        if (instance == null) {
            instance = new LotteryDealManager();
        }
        return instance;
    }

    public LotteryDealManager() {
        this.cardTypeManaer = CardTypeManaer.getInstance();
    }

    /**
     * 发牌洗牌
     */
    public LotteryHistoryDto shuffleAndDeal() {
        int result = WeightRandom.awardPosition(AH_DATA);
        Map<Integer, CardDataTable> map = cardTypeManaer.getAllCardIdMap();
        CardDataTable[] cardDataTables = null;
        switch (result) {
            case 1:
                cardDataTables = cardTypeManaer.getHigh(map);
                break;
            case 2:
                cardDataTables = cardTypeManaer.getPair(map);
                break;
            case 3:
                cardDataTables = cardTypeManaer.getStraight(map);
                break;
            case 4:
                cardDataTables = cardTypeManaer.getSameColorCard(map);
                break;
            case 5:
                cardDataTables = cardTypeManaer.getStraightFlush(map);
                break;
            case 6:
                cardDataTables = cardTypeManaer.getLeopard(map);
                break;
            case 7:
                cardDataTables = cardTypeManaer.getAAA(map);
                break;
            default:
                cardDataTables = cardTypeManaer.getHigh(map);
        }
        int oddEven = 0;
        List<Integer> ids = new ArrayList<>(3);
        for (CardDataTable c : cardDataTables) {
            int faceExchange = c.getFace() == 14 ? 1 : c.getFace();
            oddEven += faceExchange;
            ids.add(c.getId());
        }
        oddEven = oddEven % 2 == 0 ? 8 : 9;
        return new LotteryHistoryDto(0, result, oddEven, ids);
    }

}
