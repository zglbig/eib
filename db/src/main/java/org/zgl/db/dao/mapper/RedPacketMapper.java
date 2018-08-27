package org.zgl.db.dao.mapper;

import org.zgl.db.dao.entity.RedPacket;

import java.util.List;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/8/13
 * @文件描述：
 */
public interface RedPacketMapper {
    List<RedPacket> queryRedPacketUid(Long id);
    int updateRedPacket(RedPacket sigin);
    int insertRedPacket(RedPacket sigin);
    int deleteRedPacket(RedPacket id);
}
