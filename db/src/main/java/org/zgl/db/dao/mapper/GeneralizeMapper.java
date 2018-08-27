package org.zgl.db.dao.mapper;

import org.zgl.db.dao.entity.Generalize;

import java.util.List;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/8/21
 * @文件描述：
 */
public interface GeneralizeMapper {
    List<Generalize> queryGeneralizeByUid(Long uid);
    Generalize queryGeneralizeByTargetUid(Long uid);
    List<Generalize> queryGeneralizeListById(Integer id);
    int insertGeneralize(Generalize generalize);
    int updateGeneralize(Generalize generalize);
    int updateGeneralizeByTargetUid(Generalize generalize);
    int deleteGeneralizeByUid(Long uid);
}
