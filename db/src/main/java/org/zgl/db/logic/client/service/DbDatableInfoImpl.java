package org.zgl.db.logic.client.service;

import org.springframework.stereotype.Component;
import org.zgl.JsonUtils;
import org.zgl.build.desc.DataTable;
import org.zgl.build.excel.read.StaticConfigMessage;
import org.zgl.datable.DatableModelDto;
import org.zgl.datable.DatableModelListDto;
import org.zgl.db.logic.Operation;
import org.zgl.desc.ClinetProxy;
import org.zgl.service.client.db.DbDatableInfo;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/8/16
 * @文件描述：
 */
@Component
@ClinetProxy
public class DbDatableInfoImpl extends Operation implements DbDatableInfo {
    @Override
    public DatableModelListDto getData() {
        StaticConfigMessage staticConfigMessage = StaticConfigMessage.getInstance();
        Map<Class<?>,Map<Serializable,Object>> map = staticConfigMessage.getAllData();
        List<DatableModelDto> list = new ArrayList<>();
        for(Map.Entry<Class<?>,Map<Serializable,Object>> e : map.entrySet()){
            Annotation proco = e.getKey().getAnnotation(DataTable.class);
            if(proco == null){
                //客户端不需要的数据或略不记
                continue;
            }
            String objType = e.getKey().getName();
            List<String> jsonMsg = new ArrayList<>(e.getValue().size());
            for(Map.Entry<Serializable,Object> me : e.getValue().entrySet()){
                jsonMsg.add(JsonUtils.jsonSerialize(me.getValue()));
            }
            list.add(new DatableModelDto(objType,jsonMsg));
        }
        return new DatableModelListDto(list);
    }
}
