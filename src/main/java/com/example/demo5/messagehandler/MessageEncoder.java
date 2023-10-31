package com.example.demo5.messagehandler;

import com.example.demo5.models.Message;
import com.google.gson.Gson;
import jakarta.websocket.EncodeException;
import jakarta.websocket.Encoder;

@SuppressWarnings("all")
public class MessageEncoder implements Encoder.Text<Message> {
    private static Gson gson = new Gson();

    @Override
    public String encode(Message message) throws EncodeException {
        return gson.toJson(message);
    }
}
