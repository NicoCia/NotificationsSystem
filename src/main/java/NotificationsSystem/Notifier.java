package NotificationsSystem;

// import java.util.ArrayList;
import java.util.HashMap;
// import java.util.List;

public class Notifier {
    private HashMap<String, User> registeredUsersMap;
    private HashMap<String, Subject> topicsObserversMap;
    private Alert lastAlert; 
    
    public Notifier() {
        registeredUsersMap = new HashMap<String, User>();
        topicsObserversMap = new HashMap<String, Subject>();
        
    }

    public void createNewNotification(){
        // TODO implement method
    }

    public Boolean addNewTopic(String topic){
        if(!topicsObserversMap.containsKey(topic)){
            NotificationsDispatcher newNotificationDispatcherForNewTopic = new NotificationsDispatcher(Notifier.this);
            topicsObserversMap.put(topic, newNotificationDispatcherForNewTopic);
            return true;
        }
        else return false;
    }

    public Boolean addNewUser(User user){

        if(!registeredUsersMap.containsKey(user.getName()))
        {
            registeredUsersMap.put(user.getName(), user);
            return true;
        }
        else return false;
    }

    public Boolean suscribeUserToTopic (String userName, String topic){
        
        if(registeredUsersMap.containsKey(userName) && topicsObserversMap.containsKey(topic)){
            User suscriberUser = registeredUsersMap.get(userName);
            Subject notificationDispatcherOfTopic = topicsObserversMap.get(topic);
            notificationDispatcherOfTopic.registerObserver(suscriberUser);
            return true;
        }
        else return false;
    }

    public Alert getLastAlert() {
        return lastAlert;
    }

    public User getUserByName(String userName){
        if(registeredUsersMap.containsKey(userName)){
            return registeredUsersMap.get(userName);
        }
        else return null;
    }
    
}
