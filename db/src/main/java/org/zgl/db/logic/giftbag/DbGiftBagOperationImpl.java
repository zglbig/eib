package org.zgl.db.logic.giftbag;

import org.springframework.stereotype.Controller;
import org.zgl.db.dao.entity.GiftBag;
import org.zgl.db.dao.entity.Player;
import org.zgl.db.dao.mapper.GiftBagMapper;
import org.zgl.db.logic.Operation;
import org.zgl.db.player.PlayerServerModel;
import org.zgl.db.utils.SpringUtils;
import org.zgl.desc.ClinetProxy;
import org.zgl.dto.ItemListDto;
import org.zgl.dto.clinet.db.GiftBagInfoDto;
import org.zgl.error.AppErrorCode;
import org.zgl.error.AppGeneralError;
import org.zgl.service.client.db.DbGiftBagOperation;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/8/24
 * @文件描述：
 */
@ClinetProxy
@Controller
public class DbGiftBagOperationImpl extends Operation implements DbGiftBagOperation {
    @Override
    public GiftBagInfoDto open(long uid) {
        Player player = PlayerServerModel.getInstance().getPlayerByPlayerId(uid);
        if(player == null){
            new AppGeneralError(AppErrorCode.ACCOUNT_IS_NOT_LOGIN);
        }
        GiftBag bag = player.getGiftBag();
        if(bag == null){
            GiftBagMapper mapper = SpringUtils.getBean(GiftBagMapper.class);
            bag = mapper.queryGiftBagByUid(uid);
            if(bag == null){
                bag = new GiftBag();
                bag.setUid(player.getUid());
                bag.reset();
                mapper.insertGiftBag(bag);
            }
        }
        return new GiftBagInfoDto(bag.getDayCount(),bag.getDone(),bag.getAward());
    }

    @Override
    public ItemListDto receiveAward(long uid) {
        Player player = PlayerServerModel.getInstance().getPlayerByPlayerId(uid);
        if(player == null){
            new AppGeneralError(AppErrorCode.ACCOUNT_IS_NOT_LOGIN);
        }
        return GiftBagListener.handler(player);
    }
}
