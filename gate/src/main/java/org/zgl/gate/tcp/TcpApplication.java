package org.zgl.gate.tcp;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;
import org.zgl.config.ConfigFactory;
import org.zgl.gate.tcp.channel.SSimpleChannelInboundHandler;
import org.zgl.gate.tcp.code.SByteToMessageDecoder;
import org.zgl.gate.tcp.code.SMessageToByteEncoder;
import org.zgl.gate.utils.LoggerUtils;

import java.util.concurrent.TimeUnit;

/**
 * @author ： 猪哥亮
 * @创建时间： 2018/7/11
 * @文件描述：
 */
public class TcpApplication {
    public static void run(){
        EventLoopGroup boss = new NioEventLoopGroup(1);
        EventLoopGroup work = new NioEventLoopGroup(4);
        //配置服务器端的nio
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(boss, work)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG,100)
                    //通过NoDelay禁用Nagle,使消息立即发出去，不用等待到一定的数据量才发出去
                    .option(ChannelOption.TCP_NODELAY,true)
                    .childOption(ChannelOption.SO_KEEPALIVE, false)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new SMessageToByteEncoder());
                            ch.pipeline().addLast(new SByteToMessageDecoder());
                            ch.pipeline().addLast(new IdleStateHandler(90,90,90, TimeUnit.SECONDS));
                            ch.pipeline().addLast(new SSimpleChannelInboundHandler());
                        }
                    });
            //绑定端口
            ChannelFuture f = b.bind(ConfigFactory.getInstance().getSocketUrlCfg().getGateTcpPort()).sync();
            if(f.isSuccess()){
                System.err.println("tcp网关服务器启动成功");
                LoggerUtils.getLogicLog().warn("----------------->>房间服务器启动成功<<------------------端口"+ConfigFactory.getInstance().getSocketUrlCfg().getGateTcpPort()+">时间<"+System.currentTimeMillis());
            }
            f.channel().closeFuture().sync();//等待服务端监听关闭
        }catch (Exception e){
            LoggerUtils.getLogicLog().warn("---------------服务器启动失败------------------>"+System.currentTimeMillis(),e);
        }finally {
            //优雅退出线程
            boss.shutdownGracefully();
            work.shutdownGracefully();
            LoggerUtils.getLogicLog().warn("---------------服务器关闭------------------>"+System.currentTimeMillis());
        }

    }
}
