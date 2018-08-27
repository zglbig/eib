package org.zgl.db.logic.giftbag;

import org.zgl.datable.GiftBagAwardDataTable;
import org.zgl.datable.GiftBagAwardModel;
import org.zgl.db.dao.entity.GiftBag;
import org.zgl.db.dao.entity.Player;
import org.zgl.db.dao.mapper.GiftBagMapper;
import org.zgl.db.player.PlayerServerModel;
import org.zgl.db.utils.SpringUtils;
import org.zgl.dto.ItemDto;
import org.zgl.dto.ItemListDto;
import org.zgl.error.AppErrorCode;
import org.zgl.error.AppGeneralError;
import org.zgl.type.ScenesEnum;

import java.util.ArrayList;
import java.util.List;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/8/24
 * @文件描述：成长礼包监听者
 */
public class GiftBagListener {
    /**成长礼包监听*/
    public static void listen(Player player, int scenesId) {
        GiftBagMapper mapper = SpringUtils.getBean(GiftBagMapper.class);
        GiftBag bag = player.getGiftBag();
        if(bag == null){
            bag = mapper.queryGiftBagByUid(player.getUid());
        }
        if (bag == null) {
            bag = new GiftBag();
            bag.setUid(player.getUid());
            bag.reset();
            mapper.insertGiftBag(bag);
        }
        boolean isGiftUpdate = false;
        ScenesEnum scenesEnum = ScenesEnum.getEnum(scenesId);
        switch (scenesEnum) {
            case CLASSICAL_PRIMARY:
                bag.setPrimaryCount(bag.getPrimaryCount() + 1);
                isGiftUpdate = true;
                break;
            case CLASSICAL_INTERMEDITE:
                bag.setIntermedite(bag.getIntermedite() + 1);
                isGiftUpdate = true;
                break;
            case CLASSICAL_ADVANCED:
                bag.setAdvanced(bag.getAdvanced() + 1);
                isGiftUpdate = true;
                break;
            case GREAT_PRETENDERS_PRIMARY:
                bag.setPrimaryCount(bag.getPrimaryCount() + 1);
                isGiftUpdate = true;
                break;
            case GREAT_PRETENDERS_INTERMEDITE:
                bag.setIntermedite(bag.getIntermedite() + 1);
                isGiftUpdate = true;
                break;
            case GREAT_PRETENDERS_ADVANCED:
                bag.setAdvanced(bag.getAdvanced() + 1);
                isGiftUpdate = true;
                break;
            default:
                break;
        }
        bag.checkTaskHasDone();
        if (!isGiftUpdate) {
            return;
        }
        mapper.updateGiftBag(bag);
    }
    public static ItemListDto handler(Player player) {
        GiftBagMapper mapper = SpringUtils.getBean(GiftBagMapper.class);
        GiftBag bag = player.getGiftBag();
        if(bag == null){
            bag = mapper.queryGiftBagByUid(player.getUid());
        }
        if (bag == null || !bag.getDone()) {
            new AppGeneralError(AppErrorCode.TODAY_GIFT_BAG_NOT_DONE);
        }
        if(bag.getAward()){
            new AppGeneralError(AppErrorCode.AWARD_GET_ERR);
        }

        int day = bag.getDayCount();
        if (day > 7) {
            day = 1;
        }

        GiftBagAwardDataTable dataTable = GiftBagAwardDataTable.get(day);
        if (dataTable == null) {
            new AppGeneralError(AppErrorCode.DATA_ERR);
        }
        //加资源
        List<GiftBagAwardModel> list = dataTable.getAward();
        List<ItemDto> items = new ArrayList<>();
        for (GiftBagAwardModel m : list) {
            items.add(new ItemDto(m.getCommondity(),m.getCount()));
        }
        player.getWeath().insertWeath(items);
        bag.setAward(true);
        bag.setDayCount(++day);
        mapper.updateGiftBag(bag);
        return new ItemListDto(items);
    }
}
