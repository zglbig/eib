package org.zgl.db.dao.mapper;

import org.zgl.db.dao.entity.Player;

import java.util.List;
/**
 * @作者： big
 * @创建时间： 2018/6/13
 * @文件描述：
 */
public interface PlayerMapper {
    Player queryDBUserByAccount(String account);
    Player queryDBUserByUid(Long uid);
    Player queryDBUserById(Integer id);
    List<Player> queryDBUserList(Integer id);
    int updateDBUser(Player user);
    int insertDBUser(Player user);
    int deleteDBUser(Long uid);
}
