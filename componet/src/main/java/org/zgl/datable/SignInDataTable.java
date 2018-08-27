package org.zgl.datable;

import org.zgl.build.desc.DataTable;
import org.zgl.build.excel.read.DataTableMessage;
import org.zgl.build.excel.read.StaticConfigMessage;

@DataTable
public class SignInDataTable implements DataTableMessage {
    private final int id;
    private final long gold;
    private final int changeCard;

    public SignInDataTable() {
        id = 0;
        gold = 0L;
        changeCard = 0;
    }
    public static SignInDataTable get(int id){
        return StaticConfigMessage.getInstance().get(SignInDataTable.class,id);
    }
    public int getId() {
        return id;
    }

    public long getGold() {
        return gold;
    }

    public int getChangeCard() {
        return changeCard;
    }

    @Override
    public int id() {
        return id;
    }

    @Override
    public void afterInit() {

    }
}
