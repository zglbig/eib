package org.zgl.dto.clinet.db;

import org.zgl.build.desc.Protostuff;
import org.zgl.dto.SerializeMessage;

import java.util.List;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/8/21
 * @文件描述：
 */
@Protostuff("db")
public class RankingListDto implements SerializeMessage {
    private List<RankingBaseDto> list;

    public RankingListDto() {
    }

    public RankingListDto(List<RankingBaseDto> list) {
        this.list = list;
    }

    public List<RankingBaseDto> getList() {
        return list;
    }

    public void setList(List<RankingBaseDto> list) {
        this.list = list;
    }
}
