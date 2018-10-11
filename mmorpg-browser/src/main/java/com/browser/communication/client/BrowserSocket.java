package com.browser.communication.client;

import com.browser.communication.client.handler.BrowserSocketHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

import java.util.Scanner;

/**
 *浏览器
 *
 */

@Service
public class BrowserSocket {

    private static Logger logger= LoggerFactory.getLogger(BrowserSocket.class);

    public static void clientStart() {

        Bootstrap bootstrapToServer = new Bootstrap();
        EventLoopGroup worker=null;
        try {
            worker=new NioEventLoopGroup();

            bootstrapToServer.group(worker)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .handler(new ChannelInitializer<SocketChannel>(){

                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast("decoder", new StringDecoder());
                            ch.pipeline().addLast("encoder", new StringEncoder());
                            ch.pipeline().addLast("clientHandler", new BrowserSocketHandler());
                        }
                    });

            //连接服务端
            ChannelFuture f=bootstrapToServer.connect("127.0.0.1", 11111).sync();

            logger.info("浏览器启动成功...");

            Scanner input = new Scanner(System.in);
            while(true){

                System.out.println("请输入命令:");
                String content=input.nextLine();
                //发送请求
                f.channel().writeAndFlush(content+"\n");
                logger.info("消息发送成功...");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ApplicationContext applicationContext=new ClassPathXmlApplicationContext("applicationContext.xml");
        BrowserSocket gameServer=applicationContext.getBean(BrowserSocket.class);
        gameServer.clientStart();
    }

}

