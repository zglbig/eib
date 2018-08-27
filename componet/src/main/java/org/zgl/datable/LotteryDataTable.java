package org.zgl.datable;


import org.zgl.build.excel.read.DataTableMessage;
import org.zgl.weightRandom.IWeihtRandom;

/**
 * @作者： big
 * @创建时间： 2018/5/24
 * @文件描述：
 */
public class LotteryDataTable implements DataTableMessage,IWeihtRandom {
    private final int id;
    /**概率*/
    private final int probability;
    public LotteryDataTable(){
        this.id = 0;
        this.probability = 0;
    }
    @Override
    public int id() {
        return id;
    }

    public int getId() {
        return id;
    }

    public int getProbability() {
        return probability;
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
