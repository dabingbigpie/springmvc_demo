package com.web.websocket;

import java.io.IOException;  
import java.util.ArrayList;  
import java.util.Date;   
import org.springframework.web.socket.CloseStatus;  
import org.springframework.web.socket.TextMessage;  
import org.springframework.web.socket.WebSocketHandler;  
import org.springframework.web.socket.WebSocketMessage;  
import org.springframework.web.socket.WebSocketSession;  
  
public class SystemWebSocketHandler implements WebSocketHandler {  
        
      
    private static final ArrayList<WebSocketSession> users = new ArrayList<WebSocketSession>();;  
   
   
    @Override  
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {  
        System.out.println("ConnectionEstablished");  
        users.add(session);  
          
        session.sendMessage(new TextMessage("connect"));  
        session.sendMessage(new TextMessage("new_msg"));  
          
    }  
   
    @Override  
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {  
        System.out.println("handleMessage" + message.toString());  
        //sendMessageToUsers();  
        session.sendMessage(new TextMessage(new Date() + ""));  
    }  
   
    @Override  
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {  
        if(session.isOpen()){  
            session.close();  
        }  
        users.remove(session);  
    }  
   
    @Override  
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {  
        users.remove(session);  
          
    }  
   
    @Override  
    public boolean supportsPartialMessages() {  
        return false;  
    }  
   
    /** 
     * 给所有在线用户发送消息 
     * 
     * @param message 
     */  
    public void sendMessageToUsers(TextMessage message) {  
        for (WebSocketSession user : users) {  
            try {  
                if (user.isOpen()) {  
                    user.sendMessage(message);  
                }  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
    }  
   
}  
