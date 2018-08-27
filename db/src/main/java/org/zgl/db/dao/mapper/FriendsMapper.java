package org.zgl.db.dao.mapper;

import org.zgl.db.dao.entity.Friends;

import java.util.List;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/8/13
 * @文件描述：
 */
public interface FriendsMapper {
    List<Friends> queryFriendsUid(Long id);
    int updateFriends(Friends sigin);
    int insertFriends(Friends sigin);
    int deleteFriends(Integer id);
}
