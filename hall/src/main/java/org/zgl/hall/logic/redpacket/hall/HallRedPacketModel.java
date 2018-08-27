package org.zgl.hall.logic.redpacket.hall;

import org.zgl.RandomUtils;
import org.zgl.dto.clinet.hall.HallRedEnvelopePlayerDto;
import org.zgl.dto.clinet.hall.HallRedPacketDto;

import java.util.List;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/8/24
 * @文件描述：
 */
public class HallRedPacketModel {
    private List<Integer> residueCount;
    private int count;
    private long allGold;
    /**
     * 那个人发的 发了多少钱
     */
    private HallRedPacketDto dto;
    /**
     * 哪些人领了 每个人领了多少钱
     */
    private List<HallRedEnvelopePlayerDto> list;
    private final long createTime;

    public HallRedPacketModel() {
        this.createTime = System.currentTimeMillis();
    }

    public HallRedPacketDto getDto() {
        return dto;
    }

    public void setDto(HallRedPacketDto dto) {
        this.dto = dto;
    }

    public List<HallRedEnvelopePlayerDto> getList() {
        return list;
    }

    public void setList(List<HallRedEnvelopePlayerDto> list) {
        this.list = list;
    }

    public List<Integer> getResidueCount() {
        return residueCount;
    }

    public void setResidueCount(List<Integer> residueCount) {
        this.residueCount = residueCount;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public long getAllGold() {
        return allGold;
    }

    public void setAllGold(long allGold) {
        this.allGold = allGold;
    }

    public long getGold() {
        int random = RandomUtils.getRandom(0, residueCount.size() - 1);
        return residueCount.remove(random);
    }

    public void initCount() {
        for (int i = 0; i < count; i++) {
            int random = RandomUtils.getRandom(1, (int) allGold);
            residueCount.add(random);
        }
    }

    public long getCreateTime() {
        return createTime;
    }
}
