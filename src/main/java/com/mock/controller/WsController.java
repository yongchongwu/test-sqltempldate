package com.mock.controller;

import com.mock.domain.SocketMessage;
import com.mock.domain.SocketResponse;
import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class WsController {

  @Autowired
  private SimpMessagingTemplate simpMessagingTemplate;

  @MessageMapping("/welcome")
  @SendTo("/topic/getResponse")
  public SocketResponse say(SocketMessage message) throws InterruptedException {
    Thread.sleep(3000);
    return new SocketResponse("Welcome, " + message.getName() + "!");
  }

  @MessageMapping("/chat")
  public void handleChat(Principal principal, String msg) {
    if (principal.getName().equalsIgnoreCase("wyf")) {
      simpMessagingTemplate
          .convertAndSendToUser("wisely", "/queue/notifications", principal.getName() + "-send:"+msg);
    }else {
      simpMessagingTemplate.convertAndSendToUser("wyf","/queue/notifications", principal.getName() + "-send:"+msg);
    }
  }

}
