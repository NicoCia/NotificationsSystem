package NotificationsSystem;

import java.util.Iterator;
import java.util.List;

public class NotificationsRetriever {
    private Notifier miNotifier;

    public NotificationsRetriever(Notifier miNotifier) {
        this.miNotifier = miNotifier;
    }

    public String getEspecificUserUnreadAlerts(String userName){
        String returnText = "";
        User especificUser = miNotifier.getUserByName(userName);
        
        if(especificUser != null){
            List<Alert> unreadUrgentAlerts = especificUser.getUnreadUrgentAlertsList();
            List<Alert> unreadInformativeAlerts = especificUser.getUnreadInformativeAlertsList();
            returnText = "Alertas para el usuario - " + userName;
            returnText += getTextForUserAlerts(unreadUrgentAlerts);
            returnText += getTextForUserAlerts(unreadInformativeAlerts);
        }

        return returnText;
    }

    public String getTopicUnreadAlerts(String topic){
        String returnText = "";
        NotificationsDispatcher especificTopic = (NotificationsDispatcher) miNotifier.getSubjectByTopic(topic);
        
        if(especificTopic != null){
            List<Alert> sendUrgentAlerts = especificTopic.getSendUrgentAlertsList();
            List<Alert> sendInformativeAlerts = especificTopic.getSendInformativeAlertsList();
            returnText = "Alertas para el topico - " + topic;
            returnText += getTextForTopicAlerts(sendUrgentAlerts);
            returnText += getTextForTopicAlerts(sendInformativeAlerts);
        }

        return returnText;
    }

    private String getTextForUserAlerts(List<Alert> alertsList){
        String returnText = "";

        Iterator<Alert> alertIterator = alertsList.iterator();

        while(alertIterator.hasNext()){
            Alert nextAlert = alertIterator.next();
            if(!nextAlert.getExpirationFlag()) 
                returnText += nextAlert.getType() + " - " + nextAlert.getTopic() 
                            + " - " + nextAlert.getText() + "\n";
        }

        return returnText;
    }

    private String getTextForTopicAlerts(List<Alert> alertsList){
        String returnText = "";

        Iterator<Alert> alertIterator = alertsList.iterator();

        while(alertIterator.hasNext()){
            Alert nextAlert = alertIterator.next();
            if(!nextAlert.getExpirationFlag()) 
                returnText += nextAlert.getType() + " - " + nextAlert.getTopic() 
                            + " - " + nextAlert.getText() + " - Para: " + nextAlert.getAlertedUsers() + "\n";
        }

        return returnText;
    }


}
