package gor.zgl.gamedice.socket.tcp.code;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.zgl.message.GateIoMessage;

import java.util.List;

/**
 * @author ： big
 * @创建时间： 2018/6/20
 * @文件描述：
 */
public class GameByteToMessageDecoder extends ByteToMessageDecoder {
    /**
     * 数据包的基本长度：包头+id+数据长度
     * 每个协议都是�?个int类型的基本数据占4个字�?
     */
    public static final int BASE_LENGTH = 1 + 2 + 4 + 2 + 2 + 4;

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf buffer, List<Object> list) throws Exception {

        //防止socket大量数据攻击
        if (buffer.readableBytes() > 2048) {
            buffer.skipBytes(buffer.readableBytes());
        }
        int readableBytes = buffer.readableBytes();
        if (readableBytes < BASE_LENGTH) {
            return;
        } else {
            byte key_1 = buffer.readByte();
            short key_2 = buffer.readShort();
            int key_3 = buffer.readInt();
            short dataSrc = buffer.readShort();
            short gameId = buffer.readShort();
            int msgSize = buffer.readInt();
            if (key_1 == 19 && key_2 == 1024 && key_3 == 5) {
                buffer.markReaderIndex();
                if (buffer.readableBytes() < msgSize) {
                    buffer.resetReaderIndex();
                    return;
                } else {
//                        ChannelAttachment attachment = (ChannelAttachment)chnl.getAttachment();
//                        if (attachment == null) {
//                            attachment = new ChannelAttachment();
//                            chnl.setAttachment(attachment);
//                        }
//
//                        attachment.addMessageNum();
//                        if (attachment.getMessageNum() >= 100) {
//                            LoggerService.getPlatformLog().error("[" + chnl.getId() + "]该客户端发送的消息太频繁了 ");
//                            channelBuffer.resetReaderIndex();
//                            chnl.close();
//                            return null;
//                        } else if (msgSize < 0) {
//                            return null;
//                        } else {

                    byte[] data = new byte[msgSize];
                    buffer.readBytes(data);
                    GateIoMessage ioMessage = new GateIoMessage(dataSrc, gameId, data);
                    list.add(ioMessage);
                }
            } else {
                buffer.resetReaderIndex();
                ctx.close();
                return;
            }
        }
    }
}

/**
 * 包头
 * 数据源
 * 请求的服务器id
 * 数据长度
 * 数据
 * 服务器id
 * 接口名
 * 方法名
 * 参数类型（如果是客户端的消息）
 * 参数
 */
