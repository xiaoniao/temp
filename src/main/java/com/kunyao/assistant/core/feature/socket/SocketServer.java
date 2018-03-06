package com.kunyao.assistant.core.feature.socket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.DataListener;
import com.google.gson.Gson;
import com.kunyao.assistant.core.feature.cache.redis.RedisCache;
import com.kunyao.assistant.web.controller.manage.ChatController.Chat;

import redis.clients.jedis.Jedis;

public class SocketServer implements Runnable {

	@Resource
	private RedisCache redisCache;
	
	private static SocketIOServer server;
	private Thread t;

	// 保存群组id和客户端连接
	private Map<Long, List<SocketIOClient>> map = new HashMap<>();
	
	public SocketServer(Configuration config) {
		server = new SocketIOServer(config);
		t = new Thread(this, "SocketServer");
		t.start();
	}
	
	@Override
	public void run() {
		// 绑定groupId和client关系
		server.addEventListener("bindGroupId", Chat.class, new DataListener<Chat>() {
			@Override
			public void onData(SocketIOClient client, Chat data, AckRequest ackRequest) {
				Long key = Long.valueOf(data.getGroupId());
				if (!map.containsKey(key)) {
					map.put(key, new ArrayList<SocketIOClient>());
				}
				
				List<SocketIOClient> socketIOClients = map.get(key);
				if (!socketIOClients.contains(client)) {
					socketIOClients.add(client);
				} else {
					System.out.println("socketIOClients.contains(client)");
				}
				ackRequest.sendAckData("success");
				System.out.println("bing key:" + key + " sessionId:" + client.getSessionId());
			}
		});
		
		server.addEventListener("chatevent", Chat.class, new DataListener<Chat>() {
			@Override
			public void onData(SocketIOClient client, Chat data, AckRequest ackRequest) {
				System.out.println(data);
				Jedis jedis = redisCache.getJedis();

				// 发送人昵称
				String nickName = jedis.hget(data.getUserName(), "nickName");
				// 发送人头像
				String icon = jedis.hget(data.getUserName(), "icon");
				// 发送组
				long groupId = data.getGroupId();
				
				Chat chat = new Chat(data.getUserName(), nickName, icon, data.getMessage());

				jedis.lpush("chat:" + groupId, new Gson().toJson(chat));
				
				// 只推送给该组别的客户端
				List<SocketIOClient> socketIOClients = map.get(Long.valueOf(data.getGroupId()));
				for (SocketIOClient socketIOClient : socketIOClients) {
					socketIOClient.sendEvent("chatevent", chat);
				}
				System.out.println("推送设备个数：" + socketIOClients.size());
			}
		});

		// 启动服务
		server.start();
		try {
			Thread.sleep(Long.MAX_VALUE);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		server.stop();
	}
	
	/**
	 * 停止监听
	 */
	public void destroy() {
		if (t != null) {
			t.interrupt();
		}
	}

	/**
	 * 发送浏览器推送
	 */
	public static void sendNotify(String title, String content) {
		server.getBroadcastOperations().sendEvent("chatevent", new Notify(title, content));
	}
	
	/**
	 * 发送浏览器推送
	 */
	public static void sendNotify(Notify notify) {
		server.getBroadcastOperations().sendEvent("chatevent", notify);
	}
	
	public static class Notify {
		private String title;
		private String content;
		
		public Notify() {
			
		}
		
		public Notify(String title, String content) {
			this.title = title;
			this.content = content;
		}

		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public String getContent() {
			return content;
		}
		public void setContent(String content) {
			this.content = content;
		}
	}
}
