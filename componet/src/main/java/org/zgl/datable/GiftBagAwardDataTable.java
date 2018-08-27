package org.zgl.datable;

import org.zgl.build.excel.read.DataTableMessage;
import org.zgl.build.excel.read.StaticConfigMessage;
import org.zgl.common.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 猪哥亮
 * 成长礼包
 */
public class GiftBagAwardDataTable implements DataTableMessage {
    private final int id;
    /**描述*/
    private final String describe;
    /**初级场次*/
    private final int primary;
    /**中级场次*/
    private final int intermediate;
    /**高级场次*/
    private final int advanced;
    private final List<GiftBagAwardModel> award;
    public static GiftBagAwardDataTable get(int id){
        return StaticConfigMessage.getInstance().get(GiftBagAwardDataTable.class,id);
    }

    public GiftBagAwardDataTable() {
        this.id = 0;
        this.award = null;
        this.primary = 0;
        this.describe = "";
        this.intermediate = 0;
        this.advanced = 0;
    }

    public String getDescribe() {
        return describe;
    }

    public int getPrimary() {
        return primary;
    }

    public int getIntermediate() {
        return intermediate;
    }

    public int getAdvanced() {
        return advanced;
    }

    public int getId() {
        return id;
    }

    public List<GiftBagAwardModel> getAward() {
        return award;
    }

    private List<GiftBagAwardModel> award4Init(String value){
        String[] str = StringUtils.split(value,";");
        List<GiftBagAwardModel> obj = new ArrayList<>(8);
        for(int i = 0;i<str.length;i++){
            String[] str1 = StringUtils.split(str[i],",");
            obj.add(new GiftBagAwardModel(Integer.parseInt(str1[0]),Integer.parseInt(str1[1]),Integer.parseInt(str1[2])));
        }
        return obj;
    }
    @Override
    public int id() {
        return id;
    }

    @Override
    public void afterInit() {

    }

}