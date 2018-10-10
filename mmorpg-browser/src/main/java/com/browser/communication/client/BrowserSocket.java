package com.browser.communication.client;

import com.browser.communication.client.handler.BrowserSocketHandler;
import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.*;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import org.jboss.netty.handler.codec.string.StringDecoder;
import org.jboss.netty.handler.codec.string.StringEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

import java.net.InetSocketAddress;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *浏览器
 *
 */

@Service
public class BrowserSocket {

    private static Logger logger= LoggerFactory.getLogger(BrowserSocket.class);

    public static void clientStart() {

        ExecutorService boss=null;
        ExecutorService worker =null;
        try {
            ClientBootstrap bootstrapToServer = new  ClientBootstrap();

            boss = Executors.newCachedThreadPool();
            worker= Executors.newCachedThreadPool();

            bootstrapToServer.setFactory(new NioClientSocketChannelFactory(boss, worker));

            bootstrapToServer.setPipelineFactory(new ChannelPipelineFactory() {

                @Override
                public ChannelPipeline getPipeline() throws Exception {
                    ChannelPipeline pipeline = Channels.pipeline();
                    pipeline.addLast("decoder", new StringDecoder());
                    pipeline.addLast("encoder", new StringEncoder());
                    pipeline.addLast("clientHandler", new BrowserSocketHandler());
                    return pipeline;
                }
            });

            //连接服务端
            ChannelFuture connect = bootstrapToServer.connect(new InetSocketAddress("127.0.0.1", 11111));
            Channel channel = connect.sync().getChannel();

            logger.info("浏览器启动...");

            Scanner input = new Scanner(System.in);
            while(true){

                System.out.println("请输入命令:");
                String content=input.nextLine();
                //发送请求
                channel.write(content);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            boss.shutdown();
            worker.shutdown();
        }
    }

    public static void main(String[] args) {
        ApplicationContext applicationContext=new ClassPathXmlApplicationContext("applicationContext.xml");
        BrowserSocket gameServer=applicationContext.getBean(BrowserSocket.class);
        gameServer.clientStart();
    }

}

