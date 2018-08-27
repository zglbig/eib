package org.zgl.datable;

import org.zgl.build.desc.DataTable;
import org.zgl.build.excel.read.DataTableMessage;
import org.zgl.build.excel.read.StaticConfigMessage;
import org.zgl.common.StringUtils;
import org.zgl.dto.clinet.dice.DiceCountDto;
import org.zgl.weightRandom.IWeihtRandom;

@DataTable
public class DiceDataTable implements DataTableMessage,IWeihtRandom {
    private final int id;
    /**概率*/
    private final int probability;
    /**点数*/
    private final int count;
    /**是否是围骰 1，是 2，不是*/
    private final int weiTou;
    private final DiceCountDto splitCount;
    /**是否是围骰对应的点数*/
    public DiceDataTable() {
        this.id = 0;
        this.probability = 0;
        this.count = 0;
        this.weiTou = 0;
        this.splitCount = null;
    }
    public static DiceDataTable get(int id){
        return StaticConfigMessage.getInstance().get(DiceDataTable.class,id);
    }

    public int getCount() {
        return count;
    }
    public int getProbability() {
        return probability;
    }

    private DiceCountDto splitCount4Init(String value){
        DiceCountDto d = new DiceCountDto();
        String[] str = StringUtils.split(value,",");
        int one = Integer.parseInt(str[0]);
        int two = Integer.parseInt(str[1]);
        d.setOne(one);
        d.setTwo(two);
        return d;
    }

    public DiceCountDto getSplitCount() {
        return splitCount;
    }

    @Override
    public int id() {
        return id;
    }

    @Override
    public void afterInit() {
    }

    public int getId() {
        return id;
    }

    @Override
    public int elementId() {
        return id;
    }

    @Override
    public int probability() {
        return probability;
    }

    public int getWeiTou() {
        return weiTou;
    }

}
