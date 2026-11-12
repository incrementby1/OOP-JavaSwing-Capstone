package foodDelivery.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic"); // client channel
        config.setApplicationDestinationPrefixes("/app"); // msgs client sent to server
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // endpoint clients use to connect w/ WebSocket
        registry.addEndpoint("/ws").setAllowedOriginPatterns("*").withSockJS();
    }
}
