package NotificationsSystem;

import java.util.concurrent.Semaphore;

public class Alert {
    private String topic;
    private String type;
    private String text;
    private String alertedUsers;
    private Boolean expirationFlag;
    private Semaphore concurrentSemaphore;
    private long alertIndex;

    public Alert(String topic, String type, String alertedUsers, long alertIndex) {
        this.topic = topic;
        this.type = type;
        this.alertedUsers = alertedUsers;
        this.alertIndex = alertIndex;
        this.concurrentSemaphore = new Semaphore(1,true);
        this.expirationFlag = false;
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

    public Semaphore getConcurrentSemaphore() {
        return concurrentSemaphore;
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
