package org.zgl.dto.clinet.dice;

import org.zgl.build.desc.Protostuff;
import org.zgl.dto.SerializeMessage;

import java.util.List;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/8/16
 * @文件描述：
 */
@Protostuff("dice")
public class GameDicePositionPlayerInfoListDto implements SerializeMessage {
    private List<GameDicePositionPlayerInfoDto> playerList;

    public GameDicePositionPlayerInfoListDto() {
    }

    public GameDicePositionPlayerInfoListDto(List<GameDicePositionPlayerInfoDto> playerList) {
        this.playerList = playerList;
    }

    public List<GameDicePositionPlayerInfoDto> getPlayerList() {
        return playerList;
    }

    public void setPlayerList(List<GameDicePositionPlayerInfoDto> playerList) {
        this.playerList = playerList;
    }
}
