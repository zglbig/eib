package org.zgl.dto.clinet.hall;

import org.zgl.build.desc.Protostuff;
import org.zgl.dto.SerializeMessage;

import java.util.List;

/**
 * @作者： big
 * @创建时间： 2018/6/16
 * @文件描述：
 */
@Protostuff("hall")
public class GeneralizeListDto implements SerializeMessage {
    private long allAward;
    private String generalizeUserName;
    private List<GeneralizeDto> generalizeDtoList;

    public GeneralizeListDto() {
    }

    public GeneralizeListDto(long allAward, String generalizeUserName, List<GeneralizeDto> generalizeDtoList) {
        this.allAward = allAward;
        this.generalizeUserName = generalizeUserName;
        this.generalizeDtoList = generalizeDtoList;
    }

    public String getGeneralizeUserName() {
        return generalizeUserName;
    }

    public void setGeneralizeUserName(String generalizeUserName) {
        this.generalizeUserName = generalizeUserName;
    }

    public long getAllAward() {
        return allAward;
    }

    public void setAllAward(long allAward) {
        this.allAward = allAward;
    }

    public List<GeneralizeDto> getGeneralizeDtoList() {
        return generalizeDtoList;
    }

    public void setGeneralizeDtoList(List<GeneralizeDto> generalizeDtoList) {
        this.generalizeDtoList = generalizeDtoList;
    }
}