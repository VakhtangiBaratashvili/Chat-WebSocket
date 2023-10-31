package com.example.demo5.messagehandler;

import com.example.demo5.models.Message;
import com.google.gson.Gson;
import jakarta.websocket.DecodeException;
import jakarta.websocket.Decoder;

@SuppressWarnings("all")
public class MessageDecoder implements Decoder.Text<Message> {
    private static Gson gson = new Gson();
    @Override
    public Message decode(String s) throws DecodeException {
        return gson.fromJson(s, Message.class);
    }

    @Override
    public boolean willDecode(String s) {
        return !s.equals(null);
    }
}
