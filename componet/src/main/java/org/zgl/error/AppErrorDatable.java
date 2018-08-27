package org.zgl.error;

import org.zgl.build.desc.DataTable;
import org.zgl.build.excel.read.DataTableMessage;
import org.zgl.build.excel.read.StaticConfigMessage;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/8/16
 * @文件描述：
 */
@DataTable
public class AppErrorDatable implements DataTableMessage {
    private final int id;
    private final String name;
    private final String value;
    public AppErrorDatable() {
        this.id = 0;
        this.name = "";
        this.value = "";
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    @Override
    public int id() {
        return id;
    }
    public static AppErrorDatable get(int id){
        return StaticConfigMessage.getInstance().get(AppErrorDatable.class,id);
    }
    @Override
    public void afterInit() {

    }
}
