package foodDelivery.model;

public class ChatMessage {
    private String sender;
        private String content;
    private String channelId;

    public ChatMessage(){}

    public ChatMessage(String sender, String content, String channelId) {
        this.sender = sender;
        this.content = content;
        this.channelId = channelId;
    }

    public String getSender() { return sender; }
    public void setSender(String sender) { this.sender = sender; }

    public String getChannelId() { return channelId; }
    public void setChannelId(String channelId) { this.channelId = channelId; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
}
