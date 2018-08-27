package org.zgl.db.dao.mapper;

import org.zgl.db.dao.entity.UserLog;

import java.util.List;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/8/18
 * @文件描述：
 */
public interface UserLogMapper {
    List<UserLog> queryUserLogUid(Long id);
    int updateUserLog(UserLog sigin);
    int insertUserLog(UserLog sigin);
    int deleteUserLog(Integer id);
}
