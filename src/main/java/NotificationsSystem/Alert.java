package NotificationsSystem;

public class Alert {
    private String topic;
    private String type;
    private String text;
    private String alertedUsers;
    private Boolean ExpirationFlag;
    private long alertIndex;

    public Alert(String topic, String type, String text, String alertedUsers, long alertIndex) {
        this.topic = topic;
        this.type = type;
        this.text = text;
        this.alertedUsers = alertedUsers;
        this.alertIndex = alertIndex;
        ExpirationFlag = false;
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

    public void setExpirationFlag(Boolean ExpirationFlag) {
        this.ExpirationFlag = ExpirationFlag;
    }

    public Boolean getExpirationFlag() {
        return ExpirationFlag;
    }  
    
}
