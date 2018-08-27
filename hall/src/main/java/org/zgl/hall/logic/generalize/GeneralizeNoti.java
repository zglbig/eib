package org.zgl.hall.logic.generalize;

import org.zgl.desc.ClinetProxy;
import org.zgl.dto.clinet.hall.GeneralizeAwardDto;
import org.zgl.service.client.hall.HallGeneralizeNotify;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/8/22
 * @文件描述：
 */
@ClinetProxy
public class GeneralizeNoti implements HallGeneralizeNotify {
    @Override
    public void notifyInvite(GeneralizeAwardDto dto) {

    }
}
