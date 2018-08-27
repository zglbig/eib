package org.zgl.service.server.hall;

import org.zgl.proxy.ClassDesc;
import org.zgl.proxy.MethodDesc;
import org.zgl.proxy.rule.ITcpAsyncService;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/8/18
 * @文件描述：
 */
@ClassDesc("充值操作")
public interface PayOperation extends ITcpAsyncService {
    @MethodDesc("payType:充值类型 1 商城购买充值 2 活动购买充值")
    boolean wxPay(int payType,int productId);
    @MethodDesc("payType:充值类型 1 商城购买充值 2 活动购买充值")
    boolean aliPay(int payType,int productId);
}
