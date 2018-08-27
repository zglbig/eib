package org.zgl.db.dao.mapper;

import org.zgl.db.dao.entity.Weath;

import java.util.List;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/7/31
 * @文件描述：
 */
public interface WeathMapper {
    int insertWeath(Weath weath);
    int updateWeath(Weath weath);
    Weath queryWeath(Integer id);
    int deleteWeath(Integer id);
    List<Weath> queryWeathList(Integer id);
}
