package org.zgl.gate.tcp.code;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.zgl.message.GateIoMessage;

/**
 * @author ： 猪哥亮
 * @创建时间： 2018/6/20
 * @文件描述：
 */
public class SMessageToByteEncoder extends MessageToByteEncoder<GateIoMessage> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, GateIoMessage o, ByteBuf byteBuf) throws Exception {
        try {
            if (o == null) {
                channelHandlerContext.close();
                throw new RuntimeException("在 encode消息编码时发现 msg != IoMessage !!!");
            } else {
                byteBuf.writeByte(19);
                byteBuf.writeShort(1024);
                byteBuf.writeInt(5);
                //数据来源
                byteBuf.writeShort(o.getCmd());
                //服务器地址
                byteBuf.writeShort(o.getGameId());
                //数据长度
                int length = o.getLength();
                byteBuf.writeInt(length);
                //数据
                if(length > 0){
                    byteBuf.writeBytes(o.getMessage());
                }
            }
        } catch (Exception var6) {
            var6.printStackTrace();
        }
    }
    /**
     * 包头
     * 数据源
     * 数据长度
     * 数据
     *  服务器地址
     *  接口名
     *  方法名
     *  参数类型
     *  参数
     */
}
