package org.zgl.service.client.game2;

import org.zgl.dto.ItemListDto;
import org.zgl.dto.clinet.commond.ChatDto;
import org.zgl.dto.clinet.commond.GameRankingListDto;
import org.zgl.dto.clinet.game2.Game2CardListDto;
import org.zgl.dto.clinet.game2.Game2PositionPlayerInfoDto;
import org.zgl.proxy.ClassDesc;
import org.zgl.proxy.MethodDesc;
import org.zgl.proxy.rule.INotify;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/8/14
 * @文件描述：
 */
@ClassDesc("有玩家对房间操作时服务器主动通知客户端")
public interface Game2PlayerOperationRoomNotify extends INotify {
    @MethodDesc("有新的人进入房间并上了位置")
    void newPlayerUpPosition(Game2PositionPlayerInfoDto dto);

    @MethodDesc("房间有玩家财富变更同步更新，uid：变更得uid，dto：变更得财富")
    void playerWeathUpdate(long uid, ItemListDto dto);

    void playerBetUpdateRoomWeath(long betUid, long reduceGold, long betGold, int betPosition);

    @MethodDesc("结算同步财富，reduceGold：玩家剩余金币，winGold：玩家本局获得金币")
    void playerSettleAccounts(long reduceGold, long winGold);

    @MethodDesc("结算同步位置玩家财富，reduceGold：玩家剩余金币，winGold：玩家本局获得金币")
    void positionPlayerSettleAccounts(long reduceGold, long winGold);

    @MethodDesc("结算同步位置玩家财富，reduceGold：玩家剩余金币，winGold：玩家本局获得金币，nowJackpot：当前奖池")
    void bankerSettleAccounts(long reduceGold, long winGold, long nowJackpot);

    @MethodDesc("通知手牌")
    void showCards(Game2CardListDto listDto);

    void start0();

    void start1();

    void stopBet();

    void end();

    void positionPlayerExitRoom(long uid);

    @MethodDesc("上庄列表的人数")
    void bankerListCount(int count);

    @MethodDesc("达到上庄限制次数自动下庄")
    void bankerCountLimit();

    @MethodDesc("通知换庄了 bankerType：庄家类型 1 系统庄家 player返回null 、2 玩家庄家 playter返回数据")
    void bankerExchange(int bankerType, Game2PositionPlayerInfoDto player);
    @MethodDesc("主动下庄成功")
    void bankerDownSuccess();
    @MethodDesc("金币不足被迫下庄")
    void bankerDownByGlodInsufficient();
    @MethodDesc("收到聊天信息")
    void receiveChatMsg(ChatDto msg);
    @MethodDesc("结算之后的排行")
    void ranking(GameRankingListDto dto);
    @MethodDesc("踢人下坐")
    boolean kicking(String srcUserName, long targetUid, String targetUserName);
}
