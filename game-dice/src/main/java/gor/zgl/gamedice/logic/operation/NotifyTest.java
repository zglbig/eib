package gor.zgl.gamedice.logic.operation;

import org.zgl.desc.ClinetProxy;
import org.zgl.dto.ItemListDto;
import org.zgl.dto.clinet.commond.ChatDto;
import org.zgl.dto.clinet.dice.DiceSettleRankingDto;
import org.zgl.dto.clinet.dice.DiceSettleRankingListDto;
import org.zgl.dto.clinet.dice.GameDicePositionPlayerInfoDto;
import org.zgl.service.client.dice.GameDicePlayerOperationRoomNotify;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/8/17
 * @文件描述：
 */
@ClinetProxy
public class NotifyTest implements GameDicePlayerOperationRoomNotify {

    @Override
    public void playerWeathUpdate(long uid, ItemListDto dto) {

    }

    @Override
    public void settleAccountsWeathUpdate(long betGold, long winGold, long residueGold, int betPosition, float rate) {

    }

    @Override
    public void positionPlayerWeathUpdate(long uid, long exchangeGold, long residueGold) {

    }

    @Override
    public void playerClearBet(long uid, long reduceGold, long roomAllGold, int nowBetCount) {

    }

    @Override
    public void playerPositionUp(GameDicePositionPlayerInfoDto dto) {

    }


    @Override
    public void playerPositionDown(long uid) {

    }

    @Override
    public void kicking(String selfUserName, String targetUserName, long targetUid) {

    }

    @Override
    public void settleAccountRanking(int count1, int count2, DiceSettleRankingListDto dto) {

    }

    @Override
    public void start() {

    }

    @Override
    public void stopBet() {

    }

    @Override
    public void end() {

    }

    @Override
    public void receiveChatMsg(ChatDto msg) {

    }

    @Override
    public void playerSettle(DiceSettleRankingDto d) {

    }
}
