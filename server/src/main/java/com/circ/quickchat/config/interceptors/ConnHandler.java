package com.circ.quickchat.config.interceptors;

import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.WebSocketHandlerDecorator;

import com.circ.quickchat.entity.Group;
import com.circ.quickchat.entity.User;
import com.circ.quickchat.service.GroupService;
import com.circ.quickchat.service.UserService;
import com.circ.quickchat.utils.Alerts.ChatAllert;
import com.circ.quickchat.utils.Alerts.UserAllert;

public class ConnHandler extends WebSocketHandlerDecorator {

	public ConnHandler(WebSocketHandler delegate) {
		super(delegate);
	}

	@Autowired
	private UserAllert userAlert;

	@Autowired
	private ChatAllert chatAllert;

	@Autowired
	private UserService userService;

	@Autowired
	private GroupService groupService;
	
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {

		String sessionId = (String) session.getAttributes().get("sessionId").toString();
		User user = userService.getUserBySessionId(sessionId);
		List<Group> groups = groupService.getChatThatContainsUser(user);
		if (groups != null) {
			groups.forEach(group -> {
				if (group.getChat().getUsers().size() == 1) {
					groupService.deleteGroup(group);
				} else {
					group.getChat().setUsers(group.getChat().getUsers().stream().filter(usr -> !usr.getId().equals(user.getId()))
							.collect(Collectors.toSet()));
					chatAllert.deleteUserInChat(group, user);
					groupService.save(group);
				}
			});
		}
		userService.deleteUser(user);
	}

//	@Override
//	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
//		super.afterConnectionEstablished(session);
//		String sessionId = (String) session.getAttributes().get("sessionId");
//		
//		new Thread(new Runnable() {
//			
//			@Override
//			public void run() {
//				try {
//					Thread.sleep(1000);
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				//userAlert.sendUserListTo(sessionId);
//				chatAllert.sendChatToUser(chats.get(ChatConstants.principalChatId), sessionId);
//			}
//		}).start();
//
//	}

}
