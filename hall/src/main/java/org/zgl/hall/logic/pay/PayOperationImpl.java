package org.zgl.hall.logic.pay;

import org.springframework.stereotype.Controller;
import org.zgl.desc.ClinetProxy;
import org.zgl.hall.logic.Operation;
import org.zgl.service.server.hall.PayOperation;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/8/18
 * @文件描述：
 */
@Controller
@ClinetProxy
public class PayOperationImpl extends Operation implements PayOperation {
    @Override
    public boolean aliPay(int payType,int productId) {
        PayCacheModel model = new PayCacheModel(player.getUid(), "", 10, productId);
        PayManager.getInstance().submitForm(model);
        return false;
    }

    @Override
    public boolean wxPay(int payType,int productId) {
        return false;
    }
}
