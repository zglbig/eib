package org.zgl.db.dao.mapper;

import org.zgl.db.dao.entity.Sigin;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/8/13
 * @文件描述：
 */
public interface SiginMapper {
    Sigin querySiginUid(Long uid);
    int updateSigin(Sigin sigin);
    int insertSigin(Sigin sigin);
    int deleteSigin(Integer id);
}
