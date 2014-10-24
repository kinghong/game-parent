package com.base.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInboundHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * 服务器启动基类
 * 
 * @author xi.mu
 * 
 */
public class BaseServer {
	private static final Logger log = LoggerFactory.getLogger(BaseServer.class);
	
	
	public boolean start(){
		
		return true;
	}
	
	/**
	 * 初始化相关磨快
	 * @param initResult
	 * @param componentName
	 * @return
	 */
	public boolean initComponent(boolean initResult,String componentName){
		if (initResult) {
			log.error(componentName+"加载完成");
		}else {
			log.error(componentName+"错误");
		}
		return initResult;
	}

	/**
	 * 初始化战斗服务器和网关服务器连接
	 * 
	 * @param handler
	 * @param type
	 * @return
	 */
	public boolean initNetty(final ChannelInboundHandler handler, int type) {
		EventLoopGroup bossGroup=null;
		EventLoopGroup workGroup=null;
		try {
			 bossGroup = new NioEventLoopGroup(Runtime.getRuntime().availableProcessors() + 1);// 用于接受连接到到服务器的客户端
			 workGroup = new NioEventLoopGroup(Runtime.getRuntime().availableProcessors() + 1);// 有新的连接时注册到work
			ServerBootstrap bootstrap = new ServerBootstrap();
			bootstrap.group(bossGroup, workGroup)//EventLoopGroup用来处理SocketChannel和Channel上面的所有时间和IO。
			.channel(NioServerSocketChannel.class).childHandler(new ChannelInitializer<SocketChannel>() {

				@Override
				protected void initChannel(SocketChannel channel) throws Exception {
					channel.config().setSendBufferSize(4096);
					channel.config().setReceiveBufferSize(4096);
					channel.config().setReuseAddress(false);
					channel.config().setTcpNoDelay(true);
					channel.config().setSoLinger(5);//5秒
					channel.pipeline().addLast(handler);
				}
			}).option(ChannelOption.SO_BACKLOG, 128).childOption(ChannelOption.SO_KEEPALIVE, true);
			ChannelFuture future=bootstrap.bind(8000).sync();
			future.channel().closeFuture().sync();
		} catch (InterruptedException e) {
			log.error("初始化战斗服务器和网关服务器连接异常："+e.getMessage(),e);
			return false;
		}finally{
			workGroup.shutdownGracefully();
			bossGroup.shutdownGracefully();
		}
		return true;
	}

	public static void main(String[] args) {
		System.out.println("Hello World!");
	}

}
