package NotificationsSystem;

public class Alert {
    private String topic;
    private String type;
    private String text;
    private String alertedUsers;
    private Boolean expirationFlag;
    private long alertIndex;

    public Alert(String topic, String type, String alertedUsers, long alertIndex) {
        this.topic = topic;
        this.type = type;
        this.alertedUsers = alertedUsers;
        this.alertIndex = alertIndex;
        expirationFlag = false;
    }

    public String getTopic() {
        return topic;
    }

    public String getType() {
        return type;
    }

    public String getText() {
        return text;
    }

    public String getAlertedUsers() {
        return alertedUsers;
    }

    public long getAlertIndex() {
        return alertIndex;
    }
    public Boolean getExpirationFlag() {
        return expirationFlag;
    } 
    public void setExpirationFlag(Boolean ExpirationFlag) {
        this.expirationFlag = ExpirationFlag;
    }

    public void setText(String text) {
        this.text = text;
    } 
    
}
