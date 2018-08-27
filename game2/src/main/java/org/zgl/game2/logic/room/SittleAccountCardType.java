package org.zgl.game2.logic.room;


import org.zgl.RandomUtils;

import java.util.List;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/8/14
 * @文件描述：结算牌型
 */
public class SittleAccountCardType {
    /**
     * 输一副
     *
     * @param positipn 输的位置
     */
    public static int[] OneLowe(List<Integer> positipn) {
        int[] result = {1, 1, 1, 1, 1};
        int bankerRandom = RandomUtils.getRandom(2, 5);
        result[0] = bankerRandom;
        for (int i = 1; i < result.length; i++) {
            int playerRandom = 1;
            if (!positipn.contains(i)) {
                //赢得牌
                playerRandom = RandomUtils.getRandom(bankerRandom + 1, 7);
                //不能同时出现几副aaa的牌
                if (playerRandom == 7 && hasAAA(result, 7)) {
                    playerRandom = 6;
                }
            } else {
                //输得牌
                playerRandom = RandomUtils.getRandom(1, bankerRandom - 1);
            }
            result[i] = playerRandom;
        }
        return result;
    }

    /**
     * 全出同样得牌
     * @param start
     * @param end
     * @return
     */
    public static int[] randomResult(int start,int end) {
        int[] result = {7, 6, 6, 6, 6};
        int randomOne = RandomUtils.getRandom(start, end);
        if (randomOne == 7) {
            int position = RandomUtils.getRandom(0, 4);
            for (int i = 0; i < result.length; i++) {
                if (i == position) {
                    result[i] = randomOne;
                } else {
                    result[i] = 6;
                }
            }
        } else {
            for (int i = 0; i < result.length; i++) {
                result[i] = randomOne;
            }
        }
        return result;
    }

    private static boolean hasAAA(int[] result, int element) {
        for (int i = 0; i < result.length; i++) {
            if (result[i] == element) {
                return true;
            }
        }
        return false;
    }
}
