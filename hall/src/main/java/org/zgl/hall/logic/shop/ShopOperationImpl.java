package org.zgl.hall.logic.shop;

import org.springframework.stereotype.Component;
import org.zgl.HttpProxyOutboundHandler;
import org.zgl.config.ConfigFactory;
import org.zgl.desc.ClinetProxy;
import org.zgl.dto.ItemListDto;
import org.zgl.hall.logic.Operation;
import org.zgl.service.client.hall.ShopOperation;
import org.zgl.service.server.commond.PlayerWeathUpdate;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/8/17
 * @文件描述：
 */
@Component
@ClinetProxy
public class ShopOperationImpl extends Operation implements ShopOperation {
    @Override
    public ItemListDto pay(int productId) {
        PlayerWeathUpdate weathUpdate = HttpProxyOutboundHandler.createProxy(PlayerWeathUpdate.class, ConfigFactory.getInstance().getSocketUrlCfg().getDbHttp());
        return weathUpdate.payWeath(player.getUid(), productId);
    }
}
