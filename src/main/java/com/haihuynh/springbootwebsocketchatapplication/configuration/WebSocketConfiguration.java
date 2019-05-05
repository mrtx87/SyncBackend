package com.haihuynh.springbootwebsocketchatapplication.configuration;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;


import Services.SyncService;
import interceptor.HttpRequestInterceptor;

@SuppressWarnings("deprecation")
@Configuration
@EnableWebSocketMessageBroker
@EnableWebSocket
public class WebSocketConfiguration extends AbstractWebSocketMessageBrokerConfigurer {

	public static MessageBrokerRegistry registryInstance;
	
	
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/socket").setAllowedOrigins("*").withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.setApplicationDestinationPrefixes("/app")
            .enableSimpleBroker("/chat");
        
        registryInstance = registry;
    }
    
    @Bean
    public HttpRequestInterceptor httpRequestInterceptor() {
     return new HttpRequestInterceptor();
    }
    
    @Bean
    public SyncService syncService() {
     return new SyncService();
     
    }

}
