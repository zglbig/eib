package org.zgl.db.dao.mapper;

import org.zgl.db.dao.entity.Prop;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/8/13
 * @文件描述：
 */
public interface PropMapper {
    Prop queryPropId(Integer id);
    int updateProp(Prop prop);
    int insertProp(Prop prop);
    int deleteProp(Integer id);
}
