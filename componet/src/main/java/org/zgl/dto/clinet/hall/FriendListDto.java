package org.zgl.dto.clinet.hall;

import org.zgl.build.desc.Protostuff;
import org.zgl.dto.SerializeMessage;
import org.zgl.dto.clinet.commond.BasePlayerDto;

import java.util.List;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/8/21
 * @文件描述：
 */
@Protostuff("hall")
public class FriendListDto implements SerializeMessage {
    private List<BasePlayerDto> playerDtos;

    public FriendListDto() {
    }

    public FriendListDto(List<BasePlayerDto> playerDtos) {
        this.playerDtos = playerDtos;
    }

    public List<BasePlayerDto> getPlayerDtos() {
        return playerDtos;
    }

    public void setPlayerDtos(List<BasePlayerDto> playerDtos) {
        this.playerDtos = playerDtos;
    }
}
