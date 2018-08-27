package org.zgl.gate.tcp.channel;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.zgl.gate.utils.LoggerUtils;
import org.zgl.message.GateIoMessage;

/**
 * @author ： big
 * @创建时间： 2018/6/20
 * @文件描述：
 */
public class SSimpleChannelInboundHandler extends SimpleChannelInboundHandler<GateIoMessage> {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        LoggerUtils.getLogicLog().debug("用户连接"+ctx.channel().remoteAddress());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        LoggerUtils.getLogicLog().debug("用户断开连接"+ctx.channel().remoteAddress());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        IoMessageReceive.exceptionCaught(ctx.channel());
        LoggerUtils.getLogicLog().error("用户异常断开连接"+ctx.channel().remoteAddress(),cause);
    }
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, GateIoMessage o) throws Exception {
        //获取相关服务器链接
        //发送数据
        IoMessageReceive.ioMessageHandler(channelHandlerContext.channel(),o);
    }
}
