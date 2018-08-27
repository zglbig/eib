package org.zgl.db.dao.mapper;

import org.zgl.db.dao.entity.RebateDial;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/8/22
 * @文件描述：
 */
public interface RebateDialMapper {
    RebateDial queryRebateDialUid(Long id);
    int updateRebateDial(RebateDial rebateDial);
    int insertRebateDial(RebateDial rebateDial);
    int deleteRebateDialByUid(Long uid);
}
