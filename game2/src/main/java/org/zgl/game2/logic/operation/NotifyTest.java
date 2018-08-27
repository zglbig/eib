package org.zgl.game2.logic.operation;

import org.zgl.desc.ClinetProxy;
import org.zgl.dto.ItemListDto;
import org.zgl.dto.clinet.commond.ChatDto;
import org.zgl.dto.clinet.commond.GameRankingListDto;
import org.zgl.dto.clinet.game2.Game2CardListDto;
import org.zgl.dto.clinet.game2.Game2PositionPlayerInfoDto;
import org.zgl.service.client.game2.Game2PlayerOperationRoomNotify;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/8/17
 * @文件描述：
 */
@ClinetProxy
public class NotifyTest implements Game2PlayerOperationRoomNotify {
    @Override
    public void newPlayerUpPosition(Game2PositionPlayerInfoDto dto) {

    }

    @Override
    public void playerWeathUpdate(long uid, ItemListDto dto) {

    }

    @Override
    public void playerBetUpdateRoomWeath(long betUid, long reduceGold, long betGold, int betPosition) {

    }

    @Override
    public void playerSettleAccounts(long reduceGold, long winGold) {

    }

    @Override
    public void positionPlayerSettleAccounts(long reduceGold, long winGold) {

    }

    @Override
    public void bankerSettleAccounts(long reduceGold, long winGold, long nowJackpot) {

    }

    @Override
    public void showCards(Game2CardListDto listDto) {

    }

    @Override
    public void start0() {

    }

    @Override
    public void start1() {

    }

    @Override
    public void stopBet() {

    }

    @Override
    public void end() {

    }

    @Override
    public void positionPlayerExitRoom(long uid) {

    }

    @Override
    public void bankerListCount(int count) {

    }

    @Override
    public void bankerCountLimit() {

    }

    @Override
    public void bankerExchange(int bankerType, Game2PositionPlayerInfoDto player) {

    }

    @Override
    public void bankerDownSuccess() {

    }

    @Override
    public void bankerDownByGlodInsufficient() {

    }

    @Override
    public void receiveChatMsg(ChatDto msg) {

    }

    @Override
    public void ranking(GameRankingListDto dto) {

    }

    @Override
    public boolean kicking(String srcUserName, long targetUid, String targetUserName) {
        return false;
    }
}
