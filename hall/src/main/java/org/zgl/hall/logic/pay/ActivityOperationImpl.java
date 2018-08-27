package org.zgl.hall.logic.pay;

import org.springframework.stereotype.Controller;
import org.zgl.datable.ActivityDatable;
import org.zgl.desc.ClinetProxy;
import org.zgl.error.AppErrorCode;
import org.zgl.error.AppGeneralError;
import org.zgl.hall.logic.Operation;
import org.zgl.service.server.hall.ActivityOperation;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/8/21
 * @文件描述：
 */
@ClinetProxy
@Controller
public class ActivityOperationImpl extends Operation implements ActivityOperation {
    @Override
    public void pay(int productId) {
        ActivityDatable datable = ActivityDatable.get(productId);
        if (datable == null) {
            new AppGeneralError(AppErrorCode.NOT_ACTIVITY_DATA, productId);
        }
    }
    //提交订单
    //订单回调
}
