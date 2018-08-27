package org.zgl.datable;


import org.zgl.build.desc.DataTable;
import org.zgl.build.excel.read.DataTableMessage;
import org.zgl.build.excel.read.StaticConfigMessage;
import org.zgl.weightRandom.IWeihtRandom;

@DataTable
public class SiginDialDataTable implements DataTableMessage,IWeihtRandom {
    /**id*/
    private final int id;
    /**奖品id*/
    private final int awardId;
    /**奖品数量*/
    private final int count;
    /**中奖概率*/
    private final int probability;
    /**奖品类型*/
    private final int awardType;
    public SiginDialDataTable() {
        this.id = 0;
        this.awardId = 0;
        this.count = 0;
        this.probability = 0;
        this.awardType = 0;
    }
    public static SiginDialDataTable get(int id){
        return StaticConfigMessage.getInstance().get(SiginDialDataTable.class,id);
    }
    public int getId() {
        return id;
    }

    public int getAwardId() {
        return awardId;
    }

    public int getCount() {
        return count;
    }

    public int getProbability() {
        return probability;
    }

    public int getAwardType() {
        return awardType;
    }

    @Override
    public int id() {
        return id;
    }

    @Override
    public int elementId() {
        return id;
    }

    @Override
    public int probability() {
        return id;
    }

    @Override
    public void afterInit() {

    }
}
