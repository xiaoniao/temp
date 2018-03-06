package com.kunyao.assistant.web.controller.manage;

import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kunyao.assistant.core.feature.cache.redis.RedisCache;
import com.kunyao.assistant.core.utils.StringUtils;

import redis.clients.jedis.Jedis;

@Controller
@RequestMapping("/chat")
public class ChatController {
	private static final Logger log = LoggerFactory.getLogger(ChatController.class);
	
	@Resource
	private RedisCache redisCache;
	private String[] nickNames = { "关飞", "张羽", "刘备", "马超", "赵云", "曹操", "夏侯敦" };
	private String[] icons = { "caocao", "caohong", "caoren", "caowei", "caoxiu", "caoyu", "caozhang" };

	/**
	 * 创建用户
	 */
	private User createUser() {
		Jedis jedis = redisCache.getJedis();
		Random random = new Random();
		int randomIndex = random.nextInt(nickNames.length);
		String sessionId = StringUtils.getRandomString(20) + String.valueOf(System.currentTimeMillis());
		String userName = "userinfo:" + sessionId;
		String nickName = nickNames[randomIndex];
		String icon = icons[randomIndex];
		jedis.hset(userName, "nickName", nickName);
		jedis.hset(userName, "icon", icon);
		log.info("创建用户：" + userName + nickName + icon);
		return new User(userName, nickName, icon);
	}

	/**
	 * 获得用户
	 */
	private User getUser(String userName) {
		Jedis jedis = redisCache.getJedis();
		Map<String, String> map = jedis.hgetAll(userName);
		String nickName = map.get("nickName");
		String icon = map.get("icon");
		return new User(userName, nickName, icon);
	}

	/**
	 * 获得群组自增id，并自增
	 */
	private int groupIdAndIncr() {
		Jedis jedis = redisCache.getJedis();
		int groupId = 0;
		String result = jedis.hget("ids", "group");
		if (!StringUtils.isNull(result)) {
			groupId = Integer.valueOf(result);
		}
		jedis.hset("ids", "group", String.valueOf(groupId + 1));
		return groupId;
	}

	/**
	 * 创建群组 
	 * 返回用户信息和群组id、浏览器端保存用户信息
	 */
	@RequestMapping(value = "/create")
	@ResponseBody
	public User createGroup(String userName) {
		User user = null;
		Jedis jedis = redisCache.getJedis();
		if (StringUtils.isNull(userName)) {
			user = createUser();
		} else {
			user = getUser(userName);
		}

		// 获得群组id
		int groupId = groupIdAndIncr();

		// 创建群组
		String groupKey = "group:" + groupId;
		jedis.sadd(groupKey, userName);

		user.setGroupId(groupId);
		return user;
	}

	/**
	 * 聊天列表
	 */
	@RequestMapping(value = "/list")
	@ResponseBody
	public ChatResult list(HttpServletRequest request, @NotNull String userName, @Valid Integer groupId) {
		if (groupId == null) {
			return null;
		}
		ChatResult chatResult = new ChatResult();

		Jedis jedis = redisCache.getJedis();

		User user = null;
		if (StringUtils.isNull(userName)) {
			user = createUser();
			chatResult.setUser(user);
		} else {
			user = getUser(userName);
		}

		if (!jedis.sismember("group:" + groupId, user.getUserName())) {
			// 不在群组中，加入群组
			jedis.sadd("group:" + groupId, user.getUserName());
		}

		// 聊天先不分页 (之后要改成有序集合，时间是分值)
		List<String> list = jedis.lrange("chat:" + groupId, 0, -1);

		chatResult.setList(list);
		return chatResult;
	}

	/**
	 * Bean
	 */
	public static class ChatResult {
		private User user;
		private List<String> list;

		public User getUser() {
			return user;
		}

		public void setUser(User user) {
			this.user = user;
		}

		public List<String> getList() {
			return list;
		}

		public void setList(List<String> list) {
			this.list = list;
		}
	}

	public static class User {
		private String userName;
		private String nickName;
		private String icon;

		// vo
		private Integer groupId;

		public User() {
		}

		public User(String userName, String nickName, String icon) {
			this.userName = userName;
			this.nickName = nickName;
			this.icon = icon;
		}

		public String getUserName() {
			return userName;
		}

		public void setUserName(String userName) {
			this.userName = userName;
		}

		public String getNickName() {
			return nickName;
		}

		public void setNickName(String nickName) {
			this.nickName = nickName;
		}

		public String getIcon() {
			return icon;
		}

		public void setIcon(String icon) {
			this.icon = icon;
		}

		public Integer getGroupId() {
			return groupId;
		}

		public void setGroupId(Integer groupId) {
			this.groupId = groupId;
		}
	}

	public static class Chat {
		private String userName;
		private String nickName;
		private String icon;
		private long time;
		private String message;
		public long groupId;

		public Chat() {

		}

		public Chat(String userName, String message) {
			super();
			this.userName = userName;
			this.message = message;
		}

		public Chat(String userName, String nickName, String icon, String message) {
			super();
			this.userName = userName;
			this.nickName = nickName;
			this.icon = icon;
			this.message = message;
			this.time = System.currentTimeMillis();
		}

		public String getUserName() {
			return userName;
		}

		public void setUserName(String userName) {
			this.userName = userName;
		}

		public String getNickName() {
			return nickName;
		}

		public void setNickName(String nickName) {
			this.nickName = nickName;
		}

		public String getIcon() {
			return icon;
		}

		public void setIcon(String icon) {
			this.icon = icon;
		}

		public long getTime() {
			return time;
		}

		public void setTime(long time) {
			this.time = time;
		}

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}

		public long getGroupId() {
			return groupId;
		}

		public void setGroupId(long groupId) {
			this.groupId = groupId;
		}

		@Override
		public String toString() {
			return "Chat [userName=" + userName + ", nickName=" + nickName + ", icon=" + icon + ", time=" + time
					+ ", message=" + message + ", groupId=" + groupId + "]";
		}
	}
}