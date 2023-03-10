package NotificationsSystem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Notifier {
    private HashMap<String, User> registeredUsersMap;
    private HashMap<String, Subject> topicsObserversMap;
    private List<Thread> expirationsControllersThreadsList;
    private MyThreadFactory threadFactory;
    private Alert lastAlert;
    private long alertsIndex;
    
    public Notifier() {
        this.registeredUsersMap = new HashMap<String, User>();
        this.topicsObserversMap = new HashMap<String, Subject>();
        this.expirationsControllersThreadsList = new ArrayList<Thread>();
        this.threadFactory = new MyThreadFactory("NotificationsSystem");
        this.alertsIndex = 0;
    }

    public Boolean createNewNotification(String params){
        String alertedUser, topic, type;
        HashMap<String, String> paramsValuesMap = getParamsValuesMapFromString(params);
        if(paramsValuesMap.containsKey("user")&&paramsValuesMap.containsKey("topic")&&paramsValuesMap.containsKey("type")){
            alertedUser = paramsValuesMap.get("user");
            topic = paramsValuesMap.get("topic");
            type = paramsValuesMap.get("type");
            Alert newAlert = new Alert(topic, type, alertedUser, alertsIndex);
            if(paramsValuesMap.containsKey("text")) newAlert.setText(paramsValuesMap.get("text"));

            if((registeredUsersMap.containsKey(alertedUser)||alertedUser.equals("all"))&&topicsObserversMap.containsKey(topic)){

                if(paramsValuesMap.containsKey("expirationdate")){
                    String newAlertExpirationDateTimeString = paramsValuesMap.get("expirationdate");
                    
                    if(ExpiredNotificationsController.checkValidDateTime(newAlertExpirationDateTimeString)){
                        
                        ExpiredNotificationsController newAlertExpirationController = new ExpiredNotificationsController(newAlertExpirationDateTimeString, newAlert);
                        Thread newAlertExpirationControllerThread = threadFactory.newThread(newAlertExpirationController);
                        expirationsControllersThreadsList.add(newAlertExpirationControllerThread);
                        newAlertExpirationControllerThread.start();
                    }
                    else {
                        
                        return false;
                    }
                }
                lastAlert=newAlert;
                Subject notificationDispatcherOfTopic = topicsObserversMap.get(topic);
                notificationDispatcherOfTopic.notifyObservers();
                alertsIndex++;
                return true;
            }   
        }
        return false;

    }

    public Boolean addNewTopic(String topicText){
        String topic = topicText.replace(" ", ""); //quito espacios en blanco
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

    public Boolean suscribeUserToTopic (String params){
        String userName="", topic="";
        HashMap<String, String> paramsValuesMap = getParamsValuesMapFromString(params);
        if(paramsValuesMap.containsKey("user")&&paramsValuesMap.containsKey("topic")){
            userName = paramsValuesMap.get("user");
            topic = paramsValuesMap.get("topic");            
            if(registeredUsersMap.containsKey(userName) && topicsObserversMap.containsKey(topic)){
                User suscriberUser = registeredUsersMap.get(userName);
                Subject notificationDispatcherOfTopic = topicsObserversMap.get(topic);
                notificationDispatcherOfTopic.registerObserver(suscriberUser);
                return true;
            }
        }
        return false;
    }

    public Boolean markUserAlertAsRead(String params){
        String userName=""; 
        long alertIndex;
        HashMap<String, String> paramsValuesMap = getParamsValuesMapFromString(params);
        if(paramsValuesMap.containsKey("user")&&paramsValuesMap.containsKey("alertIndex")){
            userName = paramsValuesMap.get("user");
            alertIndex = Long.parseLong(paramsValuesMap.get("alertIndex"));
            if(registeredUsersMap.containsKey(userName)){
                User subscriberUser = registeredUsersMap.get(userName);
                return subscriberUser.markAlertAsRead(alertIndex);
            }
        }
        return false;
    }

    private HashMap<String, String> getParamsValuesMapFromString(String params){
        String[] paramsList = params.split(" -", 10);
        HashMap<String, String> returnParamsValuesMap = new HashMap<String, String>();

        for(String param: paramsList){
            param = param.replace(" ", ""); //quito espacios en blanco
            if(param.contains("=")){
                String[] camp_valueList = param.split("=", 2); // el indice sero sera el campo/key y el indice 1 sera el valor a asignar
                returnParamsValuesMap.put(camp_valueList[0], camp_valueList[1]);
            }
        }
        return returnParamsValuesMap;
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

    public Subject getSubjectByTopic(String topic){
        if(topicsObserversMap.containsKey(topic)){
            return topicsObserversMap.get(topic);
        }

        else return null;
    }

    public List<Thread> getExpirationsControllersThreadsList() {
        return expirationsControllersThreadsList;
    }
    
}
