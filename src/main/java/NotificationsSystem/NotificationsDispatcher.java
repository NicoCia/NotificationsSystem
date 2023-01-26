package NotificationsSystem;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class NotificationsDispatcher implements Subject {
    private List<Observer> observersList; 
    private List<Alert> sendUrgentAlertsList;
    private List<Alert> sendInformativeAlertsList;
    private Notifier miNotifier;
    // private HashMap<String, List<User>> topicsMap;

    public NotificationsDispatcher(Notifier miNotifier) {
        observersList = new ArrayList<Observer>();
        sendUrgentAlertsList = new ArrayList<Alert>();
        sendInformativeAlertsList = new ArrayList<Alert>();
        this.miNotifier = miNotifier;
    }

    public List<Alert> getSendUrgentAlertsList() {
        return sendUrgentAlertsList;
    }

    public List<Alert> getSendInformativeAlertsList() {
        return sendInformativeAlertsList;
    }

    public void addNewAlertToList(Alert newAlert){
        if(newAlert.getType().equals("urgente")){
            sendUrgentAlertsList.add(newAlert);
        }
        else sendInformativeAlertsList.add(newAlert);
    }

    @Override
    public void registerObserver(Observer o) {
        observersList.add(o);
    }

    @Override
    public void removeObserver(Observer o) {
        if(observersList.contains(o)) {
            observersList.remove(o);
        }
    }

    @Override
    public void notifyObservers() {

        Alert newAlert = miNotifier.getLastAlert();
        Iterator<Observer> observersIterator = observersList.iterator();

        addNewAlertToList(newAlert);
        
        if(newAlert.getAlertedUsers().equals("all")){
            
            while(observersIterator.hasNext()){
                Observer observer = observersIterator.next();
                observer.update();
            }
        }
        else{
            while(observersIterator.hasNext()){
                String subscribedUserName = newAlert.getAlertedUsers();
                Observer observer = observersIterator.next();
                if(observer.toString().equals(subscribedUserName)) observer.update();
            }
        }
    }

    
}
