package com.example.demo5.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Message {
    private String from;
    private String to;
    private String content;

    public Message(String from, String content) {
        this.from = from;
        this.content = content;
    }
}
