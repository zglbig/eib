package org.zgl.datable;

import org.zgl.build.desc.DataTable;
import org.zgl.build.excel.read.DataTableMessage;
import org.zgl.build.excel.read.StaticConfigMessage;
import org.zgl.weightRandom.IWeihtRandom;

@DataTable
public class RebateDialDataTable implements DataTableMessage,IWeihtRandom {
    private final int id;
    private final int awardId;
    private final int num;
    /**中奖概率*/
    private final int probability;
    public RebateDialDataTable() {
        this.id = 0;
        this.awardId = 0;
        this.num = 0;
        this.probability = 0;
    }

    public int getId() {
        return id;
    }

    public int getAwardId() {
        return awardId;
    }

    public int getNum() {
        return num;
    }

    @Override
    public int id() {
        return id;
    }
    public static RebateDialDataTable get(int id){
        return StaticConfigMessage.getInstance().get(RebateDialDataTable.class,id);
    }
    @Override
    public void afterInit() {

    }

    @Override
    public int elementId() {
        return id;
    }

    @Override
    public int probability() {
        return probability;
    }
}
