package xyz.ioc.model;

import java.util.List;

public class OutputMessage {

    long recipientId;

    String recipient;

    String recipientImageUri;

    List<Message> messages;

    public long getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(long recipientId) {
        this.recipientId = recipientId;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getRecipientImageUri() {
        return recipientImageUri;
    }

    public void setRecipientImageUri(String recipientImageUri) {
        this.recipientImageUri = recipientImageUri;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

}
