package org.zgl.gamelottery.logic.card;

import org.zgl.datable.CardDataTable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CardComparableManager {
    //线程安全的懒汉式单利模式
    private static class SingletonHolder {
        public final static CardComparableManager instance = new CardComparableManager();
    }


    public static CardComparableManager getInstance() {
        return SingletonHolder.instance;
    }


    private CardComparableManager() {
    }

    /**
     * 洗牌之后要截取多少张牌
     *
     * @param num 这里要区分是千王场还是经典场
     */
    public List<CardDataTable> shuff(int num, int scenesType) {
        List<CardDataTable> c = null;
        switch (scenesType) {
            case 1:
                c = new ArrayList<>(CardTypeManaer.getInstance().allCardList());
                break;
            case 2:
                c = new ArrayList<>(CardTypeManaer.getInstance().bigCardList());
                break;
            default:
                c = new ArrayList<>(CardTypeManaer.getInstance().allCardList());
                break;
        }
        Collections.shuffle(c);
        return c.subList(0, num);
    }
}
