package foodDelivery.controller;

import foodDelivery.model.ChatMessage;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ChannelController {
    @MessageMapping("/chat/{channelId}")
    @SendTo("/topic/{channelId}")
    public ChatMessage sendMessage(@DestinationVariable String channelId, ChatMessage message) {
        return message;
    }
}
