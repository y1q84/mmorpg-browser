package com.browser.communication.client.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.springframework.stereotype.Service;

/**
 * 消息接受处理类
 *
 */
@Service
public class BrowserSocketHandler extends SimpleChannelInboundHandler {

	@Override
	protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {
		//Response message = (Response)e.getMessage();
		//处理消息
	}
}
