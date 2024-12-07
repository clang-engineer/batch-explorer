package io.clang_engineer.batch_explorer.config

import org.springframework.context.annotation.Configuration
import org.springframework.messaging.simp.config.MessageBrokerRegistry
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker
import org.springframework.web.socket.config.annotation.StompEndpointRegistry
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer

@Configuration
@EnableWebSocketMessageBroker
class WebsocketConfiguration : WebSocketMessageBrokerConfigurer {
    // 클라이언트가 웹소켓 서버에 연결하는데 사용할 웹소켓 엔드포인트를 등록합니다.
    override fun registerStompEndpoints(registry: StompEndpointRegistry) {
        registry.addEndpoint("/websocket/tracker")
            .setAllowedOrigins("http://localhost:8080")
            .withSockJS()
    }

    // 클라이언트가 구독할 엔드포인트를 정의합니다.
    override fun configureMessageBroker(config: MessageBrokerRegistry) {
        config.enableSimpleBroker("/topic")
    }
}