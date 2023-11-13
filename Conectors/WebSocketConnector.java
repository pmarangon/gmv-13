package com.guiame.websocket;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.ArrayList;
import java.util.List;

public class WebsocketConnector extends TextWebSocketHandler {

  private List<WebSocketSession> sessions = new ArrayList<>();

  @Override
  public void afterConnectionEstablished(WebSocketSession session) throws Exception {
    sessions.add(session);
  }

  @Override
  public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
    String messageText = message.getPayload();
    for (WebSocketSession connectedSession : sessions) {
      if (!connectedSession.equals(session)) {
        connectedSession.sendMessage(new TextMessage(messageText));
      }
    }
  }

  @Override
  public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
    sessions.remove(session);
  }

  public void sendToAll(String message) {
    for (WebSocketSession connectedSession : sessions) {
      try {
        connectedSession.sendMessage(new TextMessage(message));
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }
}
