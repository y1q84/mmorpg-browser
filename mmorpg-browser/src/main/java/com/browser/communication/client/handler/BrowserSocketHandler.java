package com.browser.communication.client.handler;

import org.jboss.netty.channel.*;
import org.springframework.stereotype.Service;

/**
 * 消息接受处理类
 *
 */
@Service
public class BrowserSocketHandler extends SimpleChannelHandler {

	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
//			Response message = (Response)e.getMessage();
			//处理消息
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {
		System.out.println("exceptionCaught");
		super.exceptionCaught(ctx, e);
	}

	@Override
	public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
		System.out.println("channelConnected");
		super.channelConnected(ctx, e);
	}

	@Override
	public void channelDisconnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
		System.out.println("channelDisconnected");
		super.channelDisconnected(ctx, e);
	}

	@Override
	public void channelClosed(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
		System.out.println("channelClosed");
		super.channelClosed(ctx, e);
	}
}
