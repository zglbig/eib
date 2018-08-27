package org.zgl.service.client.db;

import org.zgl.dto.clinet.db.LoginDto;
import org.zgl.proxy.ClassDesc;
import org.zgl.proxy.MethodDesc;
import org.zgl.proxy.rule.IHttpSyncService;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/7/31
 * @文件描述：
 */
@ClassDesc("登陆代理接口")
public interface LoginOperation extends IHttpSyncService {
    /**
     * 注册
     * @param account
     * @param password
     * @param headImgUrl
     * @param sex
     * @param version
     * @return
     */
    @MethodDesc("注册")
    LoginDto regist(String account, String password, String userName, String headImgUrl, String sex, String version);

    /**
     * 登陆
     * @param account
     * @param password
     * @return
     */
    @MethodDesc("登陆")
    LoginDto login(String account, String password);
}
