package webApp.http;

import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class HttpServer {
	
	public static void main(String[] args) throws Exception {
		HttpConstants.init();
		
		HttpServer server = new HttpServer();
		server.start(1234);
	}

	public void start(int port) throws Exception {
		Selector selector = Selector.open();
		ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
		serverSocketChannel.configureBlocking(false);
		serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
		serverSocketChannel.socket().setReuseAddress(true);
		serverSocketChannel.socket().bind(new InetSocketAddress(port));
		while (true) {
			int select = selector.select();
			if (select == 0) {
				continue;
			}
			Iterator<SelectionKey> selectedKeys = selector.selectedKeys()
					.iterator();
			while (selectedKeys.hasNext()) {
				SelectionKey key = selectedKeys.next();
				if (key.isAcceptable()) {
					ServerSocketChannel ssc = (ServerSocketChannel) key
							.channel();
					SocketChannel channel = ssc.accept();
					if (channel != null) {
						channel.configureBlocking(false);
						channel.register(selector, SelectionKey.OP_READ);// 客户socket通道注册读操作
					}
				} else if (key.isReadable()) {
					SocketChannel channel = (SocketChannel) key.channel();
					channel.configureBlocking(false);

					// 创建reqeust对象
					HttpRequest request = new HttpRequest(channel);
					request.parseHead();

					//创建response
					HttpResponse response = new HttpResponse(request);
					
					channel.register(selector, SelectionKey.OP_WRITE, response);

				} else if (key.isWritable()) {
					SocketChannel channel = (SocketChannel) key.channel();
					HttpResponse response = (HttpResponse)key.attachment();
					channel.write(response.buffer);
					channel.shutdownOutput();
					channel.close();
				}
				selectedKeys.remove();
			}
		}
	}
}
