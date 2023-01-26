package NotificationsSystem;

public class Alert {
    private String topic;
    private String type;
    private String text;
    private String alertedUsers;
    private Boolean ExpirationFlag;

    public Alert(String topic, String type, String text, String alertedUsers) {
        this.topic = topic;
        this.type = type;
        this.text = text;
        this.alertedUsers = alertedUsers;
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

    public void setExpirationFlag(Boolean ExpirationFlag) {
        this.ExpirationFlag = ExpirationFlag;
    }
    
}
