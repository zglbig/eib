package org.zgl.datable;

import org.zgl.build.excel.read.DataTableMessage;
import org.zgl.build.excel.read.StaticConfigMessage;

/**
 * @author zgl
 * 牌的类型
 */
public class CardDataTable implements DataTableMessage {
    private final int id;
    /**类型 红黑梅方*/
    private final int type;
    /**面值大小 1-13*/
    private final int face;
    private final String desc;
    public static CardDataTable get(int id){
        return StaticConfigMessage.getInstance().get(CardDataTable.class,id);
    }
    public CardDataTable() {
        this.id = 0;
        this.type = 0;
        this.face = 0;
        this.desc = "";
    }

    public int getId() {
        return id;
    }

    public int getType() {
        return type;
    }

    public int getFace() {
        return face;
    }

    public String getDesc() {
        return desc;
    }

    @Override
    public int id() {
        return id;
    }

    @Override
    public void afterInit() {

    }

}
