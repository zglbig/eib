package org.zgl.service.client.game1;

import org.zgl.dto.ItemListDto;
import org.zgl.dto.clinet.commond.ChatDto;
import org.zgl.dto.clinet.commond.PbIntegerArr;
import org.zgl.dto.clinet.game1.Game1BettleWeatnUpdateListDto;
import org.zgl.dto.clinet.game1.Game1PlayerRoomBaseInfoDto;
import org.zgl.dto.clinet.game1.Game1SattleEndDto;
import org.zgl.proxy.ClassDesc;
import org.zgl.proxy.MethodDesc;
import org.zgl.proxy.rule.INotify;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/8/14
 * @文件描述：
 */
@ClassDesc("有玩家对房间操作时服务器主动通知客户端")
public interface Game1PlayerOperationRoomNotify extends INotify {
    /**
     *
     * @param readyPlayerUid
     */
    @MethodDesc("准备： readyPlayerUid 准备得人的uid")
    void ready(long readyPlayerUid);

    @MethodDesc("通知下以玩家操作 nextOperationUid：下一操作的玩家uid")
    void nextPlayerOperation(long nextOperationUid);

    @MethodDesc("房间财富变更 lastTimeBetPlayerUid: 上次下注的玩家uid ，" +
            "lastTimePlayerBetGoldCount :上次下注的玩家下注的金币数量，" +
            "lastTimeBetPlayerResidue: 上次下注的玩家剩余金币，" +
            "nextBetPlayerUid: 下一个下注的玩家uid，" +
            "nowRoomAllGold: 当前房间总金币")
    void roomBetWeathUpate(long lastTimeBetPlayerUid, long lastTimePlayerBetGoldCount, long lastTimeBetPlayerResidue, long nextBetPlayerUid, long nowRoomAllGold);

    @MethodDesc("通知有人加注 addBetUid:加注的人uid ><chipPosition：加注到哪个位置")
    void betAdd(long addBetUid, int chipPosition);
    @MethodDesc("通知有人弃牌 giveUpUid 弃牌的玩家uid，nextOperationUid：下一操作的玩家uid")
    void hasPlayerGiveUp(long giveUpUid, long nextOperationUid);
    @MethodDesc("通知有人看牌 uid 看牌的玩家uid")
    void hasPlayerLookCard(long uid);
    @MethodDesc("通知所有人比牌，并且将结果返回，" +
            " srcUid : 比牌发起人uid ，" +
            "targetUid : 比牌目标比的uid ，" +
            "loseUid ： 输的人的uid ，" +
            "loseCardType ：输的牌型 ，" +
            "loseCardIds ：输的牌得到uid")
    void compare(long srcUid, long targetUid, long loseUid, int loseCardType, PbIntegerArr loseCardIds);

    @MethodDesc("全压 srcUid：全压发起人，nextOperationUid：下一操作的玩家uid，betGold：全压了多少金币，holdGold：全压之后剩余的金币，roomAllGold：房间当前所有金币")
    void betAll(long srcUid, long nextOperationUid, long betGold, long holdGold, long roomAllGold);
    @MethodDesc("房间开局通知 betGold：减的底注数量，roomAllGold：房间当前所有金币，nextOperationUid：下一操作得到玩家uid，dtoList：减底注之后各个玩家剩余金币")
    void battle(int betGold, long roomAllGold, long nextOperationUid, Game1BettleWeatnUpdateListDto dtoList);
    @MethodDesc("通知所有玩家有人超时操作")
    void operationTimeOut(long targetUid);
    @MethodDesc("通知所有玩家有人牌局结束")
    void sattleEnd(Game1SattleEndDto endDto);
    @MethodDesc("房间有玩家财富变更同步更新，uid：变更得uid，dto：变更得财富")
    void playerWeathUpdate(long uid, ItemListDto dto);
    @MethodDesc("玩家下线")
    void playerLoginOut(long uid);
    @MethodDesc("玩家进入房间")
    void playerEnterRoom(Game1PlayerRoomBaseInfoDto dto);
    @MethodDesc("收到聊天信息")
    void receiveChatMsg(ChatDto msg);
    @MethodDesc("被人踢出房间 srcUserName:踢人得用户名，targetUid：被踢人得uid ,targetUserName :被踢人得用户名")
    void kicking(String srcUserName, long targetUid, String targetUserName);
}
