package org.zgl.db.logic.client.service;

import org.springframework.stereotype.Controller;
import org.zgl.config.ConfigFactory;
import org.zgl.db.logic.Operation;
import org.zgl.desc.ClinetProxy;
import org.zgl.dto.clinet.db.VersionDto;
import org.zgl.service.client.db.GameVersion;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/8/20
 * @文件描述：
 */
@Controller
@ClinetProxy
public class GameVersionImpl extends Operation implements GameVersion {
    @Override
    public VersionDto versionChech() {
        org.zgl.config.GameVersion version = ConfigFactory.getInstance().getGameVersion();
        VersionDto dto = new VersionDto();
        dto.setGameV(version.getGameV());
        dto.setGameUrl(version.getGameUrl());
        dto.setDataUrl(version.getDataUrl());
        dto.setDataV(version.getDataV());
        dto.setAbV(version.getAbV());
        dto.setAbUrl(version.getAbUrl());
        dto.setSecretKey(version.getSecretKey());
        dto.setSize(version.getSize());
        return dto;
    }
}
