package org.zgl.game.logic.operation;

import org.zgl.desc.ClinetProxy;
import org.zgl.dto.ItemListDto;
import org.zgl.dto.clinet.commond.ChatDto;
import org.zgl.dto.clinet.commond.PbIntegerArr;
import org.zgl.dto.clinet.game1.Game1BettleWeatnUpdateListDto;
import org.zgl.dto.clinet.game1.Game1PlayerRoomBaseInfoDto;
import org.zgl.dto.clinet.game1.Game1SattleEndDto;
import org.zgl.service.client.game1.Game1PlayerOperationRoomNotify;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/8/15
 * @文件描述：
 */
@ClinetProxy
public class NotifyTest implements Game1PlayerOperationRoomNotify {

    @Override
    public void ready(long readyPlayerUid) {

    }

    @Override
    public void nextPlayerOperation(long nextOperationUid) {

    }

    @Override
    public void roomBetWeathUpate(long lastTimeBetPlayerUid, long lastTimePlayerBetGoldCount, long lastTimeBetPlayerResidue, long nextBetPlayerUid, long nowRoomAllGold) {

    }

    @Override
    public void betAdd(long addBetUid, int chipPosition) {

    }

    @Override
    public void hasPlayerGiveUp(long giveUpUid, long nextOperationUid) {

    }

    @Override
    public void hasPlayerLookCard(long uid) {

    }

    @Override
    public void compare(long srcUid, long targetUid, long loseUid, int loseCardType, PbIntegerArr loseCardIds) {

    }

    @Override
    public void betAll(long srcUid, long nextOperationUid, long betGold, long holdGold, long roomAllGold) {

    }

    @Override
    public void battle(int betGold, long roomAllGold, long nextOperationUid, Game1BettleWeatnUpdateListDto dtoList) {

    }

    @Override
    public void operationTimeOut(long targetUid) {

    }

    @Override
    public void sattleEnd(Game1SattleEndDto endDto) {

    }

    @Override
    public void playerWeathUpdate(long uid, ItemListDto dto) {

    }

    @Override
    public void playerLoginOut(long uid) {

    }

    @Override
    public void playerEnterRoom(Game1PlayerRoomBaseInfoDto dto) {

    }


    @Override
    public void receiveChatMsg(ChatDto msg) {

    }

    @Override
    public void kicking(String srcUserName, long targetUid, String targetUserName) {

    }
}
