package org.zgl.game.socket.tcp.channel;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.zgl.ProtostuffUtils;
import org.zgl.game.socket.handler.IoMessageHandler;
import org.zgl.message.GateIoMessage;
import org.zgl.message.ServerCommunicationIoMessage;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/7/16
 * @文件描述：
 */
public class GameSimpleChannelInboundHandler extends SimpleChannelInboundHandler<GateIoMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, GateIoMessage o) throws Exception {
        IoMessageHandler.getIntance().handler(ProtostuffUtils.deserializer(o.getMessage(),ServerCommunicationIoMessage.class),channelHandlerContext.channel());
    }
}
