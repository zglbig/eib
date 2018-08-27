package org.zgl.db.dao.mapper;

import org.zgl.db.dao.entity.Autos;

import java.util.List;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/8/13
 * @文件描述：
 */
public interface AutosMapper {
    List<Autos> queryAutosUid(Long id);
    int updateAutos(Autos sigin);
    int insertAutos(Autos sigin);
    int deleteAutos(Integer id);
}
