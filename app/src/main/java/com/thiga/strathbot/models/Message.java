package com.thiga.strathbot.models;

public class Message {
    private String id;
    private String userMessage;
    private String botMessage;
    private String optionMessage;
    private String side;

    public Message() {
    }

    public Message(String botMessage, String side) {
        this.id = id;
        this.side = side;
    }

//    public Message(String id, String userMessage, String side) {
//        this.id = id;
//        this.userMessage = userMessage;
//        this.side = side;
//    }


    public Message(String userMessage, String botMessage, String side) {
        this.userMessage = userMessage;
        this.botMessage = botMessage;
        this.side = side;
    }

    public Message(String userMessage, String botMessage, String optionMessage, String side) {
        this.userMessage = userMessage;
        this.botMessage = botMessage;
        this.optionMessage = optionMessage;
        this.side = side;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserMessage() {
        return userMessage;
    }

    public void setUserMessage(String userMessage) {
        this.userMessage = userMessage;
    }

    public String getBotMessage() {
        return botMessage;
    }

    public void setBotMessage(String botMessage) {
        this.botMessage = botMessage;
    }

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }

    public String getOptionMessage() {
        return optionMessage;
    }

    public void setOptionMessage(String optionMessage) {
        this.optionMessage = optionMessage;
    }
}
