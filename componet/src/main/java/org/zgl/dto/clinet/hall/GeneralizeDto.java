package org.zgl.dto.clinet.hall;

import org.zgl.build.desc.Protostuff;
import org.zgl.dto.SerializeMessage;

@Protostuff("hall")
public class GeneralizeDto implements SerializeMessage {
    private String userNmae;
    private long award;
    private String time;

    public GeneralizeDto() {
    }

    public GeneralizeDto(String userNmae, long award, String time) {
        this.userNmae = userNmae;
        this.award = award;
        this.time = time;
    }

    public String getUserNmae() {
        return userNmae;
    }

    public void setUserNmae(String userNmae) {
        this.userNmae = userNmae;
    }

    public long getAward() {
        return award;
    }

    public void setAward(long award) {
        this.award = award;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
