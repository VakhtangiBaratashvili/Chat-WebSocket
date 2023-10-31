package com.example.demo5.endpoints;

import com.example.demo5.messagehandler.MessageDecoder;
import com.example.demo5.messagehandler.MessageEncoder;
import com.example.demo5.models.Message;
import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
@SuppressWarnings("all")
@ServerEndpoint(
        value = "/chat/{username}",
        encoders = MessageEncoder.class,
        decoders = MessageDecoder.class
)
public class ChatEndPoint {
    private Session session;
    private static Set<ChatEndPoint> chatEndPoints = new CopyOnWriteArraySet<>();
    private static Map<String, String> sessionMap = new HashMap<>();

    @OnOpen
    public void onOpen(Session session, @PathParam("username") String username) {
        this.session = session;
        sessionMap.put(session.getId(), username);
        chatEndPoints.add(this);
        Message message = new Message(username, "connected");
        broadcast(message);

    }

    @OnMessage
    public void onMessage(Session session, Message message) {
        message.setFrom(sessionMap.get(session.getId()));
        broadcast(message);
    }

    @OnClose
    public void onClose(Session session) {
        Message message = new Message(sessionMap.get(session.getId()), "disconnected");
        chatEndPoints.remove(session.getId());
        sessionMap.remove(session.getId());
        broadcast(message);
    }

    @OnError
    public void onError(Session session, Throwable throwable) {

    }

    public void broadcast(Message message) {
        chatEndPoints.forEach(chatEndPoint -> {
            synchronized (chatEndPoint) {
                try {
                    chatEndPoint.session.getBasicRemote().sendObject(message);
                } catch (IOException | EncodeException e) {
                    throw new RuntimeException(e);
                }
            }

        });
    }
}
