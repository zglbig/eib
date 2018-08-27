package org.zgl.gamelottery.socket.tcp;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;
import org.zgl.JsonUtils;
import org.zgl.MD5;
import org.zgl.ProtostuffUtils;
import org.zgl.config.ConfigFactory;
import org.zgl.config.SocketUrlCfg;
import org.zgl.gamelottery.socket.tcp.channel.GameSimpleChannelInboundHandler;
import org.zgl.gamelottery.socket.tcp.code.GameByteToMessageDecoder;
import org.zgl.gamelottery.socket.tcp.code.GameMessageToByteEncoder;
import org.zgl.message.ClientTcpIoMessage;
import org.zgl.message.GateIoMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author big
 */
public final class GameServer {
    private final Bootstrap bootstrap = new Bootstrap();
    private ChannelFuture future;
    private Channel channel;
    private final SocketUrlCfg socketUrlCfg;
    private static GameServer instance;
    public static GameServer getInstance(){
        if(instance == null){
            instance = new GameServer();
        }
        return instance;
    }

    private GameServer() {
        socketUrlCfg = ConfigFactory.getInstance().getSocketUrlCfg();
    }

    public void start(){
        //配置服务器端的nio
        try {
            EventLoopGroup group = new NioEventLoopGroup();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG,100)
                    //通过NoDelay禁用Nagle,使消息立即发出去，不用等待到一定的数据量才发出去
                    .option(ChannelOption.TCP_NODELAY,true)
                    .option(ChannelOption.SO_KEEPALIVE, false)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new GameByteToMessageDecoder());
                            ch.pipeline().addLast(new GameMessageToByteEncoder());
                            ch.pipeline().addLast(new IdleStateHandler(90,90,90, TimeUnit.SECONDS));
                            ch.pipeline().addLast(new GameSimpleChannelInboundHandler());
                        }
                    });
            //绑定端口
            this.future = bootstrap.connect(socketUrlCfg.getGateTcpIp(),socketUrlCfg.getGateTcpPort()).sync();
            this.channel = future.channel();
            if(future.isSuccess()){
                System.err.println("开启成功");
                future.channel().writeAndFlush(getRegistMessage());
                System.err.println("注册成功");
            }
        }catch (Exception e){
        }finally {
            //优雅退出线程
        }

    }
    public void shutdown(){
    }
    private final GateIoMessage getRegistMessage(){
        GateIoMessage gateIoMessage = new GateIoMessage();
        gateIoMessage.setCmd(socketUrlCfg.getGateId());
        gateIoMessage.setGameId(socketUrlCfg.getGame1Id());
        ClientTcpIoMessage clientRequsetIoMessage = new ClientTcpIoMessage();
        List<Object> list = new ArrayList<>();
        list.add(socketUrlCfg.getGameLotteryId());
        list.add(MD5.wxMd5(System.currentTimeMillis()+""));
        clientRequsetIoMessage.setArgs(JsonUtils.jsonSerialize(list));
        byte[] buf = ProtostuffUtils.serializer(clientRequsetIoMessage);
        gateIoMessage.setMessage(buf);
        return gateIoMessage;
    }
    public final void write(Object msg){
        this.channel.writeAndFlush(msg);
    }
}