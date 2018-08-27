package org.zgl.db.dao.mapper;

import org.zgl.db.dao.entity.GiftBag;
/**
 * @author： 猪哥亮
 * @创建时间： 2018/8/13
 * @文件描述：
 */
public interface GiftBagMapper {
    GiftBag queryGiftBagByUid(Long id);
    int updateGiftBag(GiftBag sigin);
    int insertGiftBag(GiftBag sigin);
    int deleteGiftBag(Integer id);
}
