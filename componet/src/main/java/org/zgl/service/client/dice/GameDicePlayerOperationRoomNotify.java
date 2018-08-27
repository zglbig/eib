package org.zgl.service.client.dice;

import org.zgl.dto.ItemListDto;
import org.zgl.dto.clinet.commond.ChatDto;
import org.zgl.dto.clinet.dice.DiceSettleRankingDto;
import org.zgl.dto.clinet.dice.DiceSettleRankingListDto;
import org.zgl.dto.clinet.dice.GameDicePositionPlayerInfoDto;
import org.zgl.proxy.MethodDesc;
import org.zgl.proxy.rule.INotify;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/8/18
 * @文件描述：
 */
public interface GameDicePlayerOperationRoomNotify extends INotify {
    @MethodDesc("房间有玩家财富变更同步更新，uid：变更得uid，dto：变更得财富")
    void playerWeathUpdate(long uid, ItemListDto dto);
    @MethodDesc("结算是财富更新")
    void settleAccountsWeathUpdate(long betGold, long winGold, long residueGold, int betPosition, float rate);
    @MethodDesc("结算是财富更新")
    void positionPlayerWeathUpdate(long uid, long exchangeGold, long residueGold);
    @MethodDesc("有玩家清除下注")
    void playerClearBet(long uid, long residueGold, long roomAllGold, int nowBetCount);
    @MethodDesc("有人上位置")
    void playerPositionUp(GameDicePositionPlayerInfoDto dto);
    @MethodDesc("有人下位置")
    void playerPositionDown(long uid);
    @MethodDesc("踢人下坐,selfUserName：踢人的用户名，targetUserName：被踢的人的用户名，targetUid：被踢的人的uid")
    void kicking(String selfUserName, String targetUserName, long targetUid);
    @MethodDesc("结算排行")
    void settleAccountRanking(int count1, int count2, DiceSettleRankingListDto dto);
    void start();
    void stopBet();
    void end();
    @MethodDesc("收到聊天信息")
    void receiveChatMsg(ChatDto msg);
    void playerSettle(DiceSettleRankingDto d);
}
