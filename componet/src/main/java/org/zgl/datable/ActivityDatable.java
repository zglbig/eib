package org.zgl.datable;

import org.zgl.build.excel.read.DataTableMessage;
import org.zgl.build.excel.read.StaticConfigMessage;
import org.zgl.common.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/8/21
 * @文件描述：
 */
public class ActivityDatable implements DataTableMessage {
    private final int id;
    private final int selling;
    private final List<ActivityModel> model;
    public static ActivityDatable get(int id){
        return StaticConfigMessage.getInstance().get(ActivityDatable.class,id);
    }
    public ActivityDatable() {
        this.id = 0;
        this.selling = 0;
        this.model = null;
    }

    public int getId() {
        return id;
    }

    public int getSelling() {
        return selling;
    }

    public List<ActivityModel> getModel() {
        return model;
    }
    private List<ActivityModel> model4Init(String value){
        List<ActivityModel> list = new ArrayList<>();
        String[] str1 = StringUtils.split(value,",");
        for(String s:str1){
            String[] str2 = StringUtils.split(s,":");
            list.add(new ActivityModel(Integer.valueOf(str2[0]),Integer.valueOf(str2[1])));
        }
        return list;
    }
    @Override
    public int id() {
        return id;
    }

    @Override
    public void afterInit() {

    }
}
