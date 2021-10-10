package com.circ.quickchat.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;
import org.springframework.web.socket.handler.WebSocketHandlerDecoratorFactory;

import com.circ.quickchat.config.interceptors.FinishConnHandler;
import com.circ.quickchat.config.interceptors.NewConnInterceptor;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

	@Autowired
	private NewConnInterceptor newConnInterceptor;
	
	@Autowired
	private  AutowireCapableBeanFactory autowireCapableBeanFactory;

	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		registry.enableSimpleBroker("/user");
	}

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		//endpoint for websocket connection
		registry.addEndpoint("/ws-quick")
				.addInterceptors(newConnInterceptor)
				.setAllowedOriginPatterns("*")
				.withSockJS();
	}

	@Override
	public void configureWebSocketTransport(WebSocketTransportRegistration registry) {
		registry.addDecoratorFactory(new WebSocketHandlerDecoratorFactory() {

			@Override
			public WebSocketHandler decorate(WebSocketHandler handler) {
				FinishConnHandler finishConnHandler = new FinishConnHandler(handler);
				autowireCapableBeanFactory.autowireBean(finishConnHandler);
				return finishConnHandler;
			}
		});
	}
	
	
	
}
