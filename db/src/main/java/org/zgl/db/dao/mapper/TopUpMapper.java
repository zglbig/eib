package org.zgl.db.dao.mapper;

import org.zgl.db.dao.entity.TopUp;

import java.util.List;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/8/18
 * @文件描述：
 */
public interface TopUpMapper {
    List<TopUp> queryTopUpUid(Long id);
    int updateTopUp(TopUp sigin);
    int insertTopUp(TopUp sigin);
    int deleteTopUp(Integer id);
}
