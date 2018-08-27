package org.zgl.hall.logic.pay;

import org.springframework.stereotype.Controller;
import org.zgl.HttpProxyOutboundHandler;
import org.zgl.common.StringUtils;
import org.zgl.config.ConfigFactory;
import org.zgl.service.server.commond.PlayerWeathUpdate;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/8/18
 * @文件描述：
 */
@Controller
public class PayCallBack {
    private void aliPayCallBack() {
        //验证是否购买成功
    }

    private void wxPayCallBack() {
    }

    /**
     * 支付回调
     *
     * @param callArgs 回调的参数 uid_productId
     */
    private void payCallBack(String callArgs) {
        try {
            String[] args = StringUtils.split(callArgs, "_");
            if (args.length == 2) {
                return;
            }
            //获取uid
            //获取订单号
            long uid = Long.parseLong(args[0]);
            int productId = Integer.parseInt(args[1]);
            PayCacheModel model = PayManager.getInstance().model(uid);
            if (model != null) {
                boolean key1 = model.getProductId() == productId;
                if (key1) {
                    PayManager.getInstance().removeForm(uid);
                    PlayerWeathUpdate weathUpdate = HttpProxyOutboundHandler.createProxy(PlayerWeathUpdate.class, ConfigFactory.getInstance().getSocketUrlCfg().getDbHttp());
                    weathUpdate.payWeath(uid, productId);
                    //购买成功
                }
            }
        } catch (Exception e) {

        }
    }
}
