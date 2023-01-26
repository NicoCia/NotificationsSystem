package NotificationsSystem;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class User implements Observer{
    private String name;
    private List<Alert> unreadUrgentAlertsList;
    private List<Alert> unreadInformativeAlertsList;
    private Notifier miNotifier;
    

    public User(String name, Notifier miNotifier) {
        this.name = name;
        this.miNotifier = miNotifier;
        unreadUrgentAlertsList = new ArrayList<Alert> ();
        unreadInformativeAlertsList = new ArrayList<Alert> ();
    }

    
    public String getName() {
        return name;
    }


    public List<Alert> getUnreadUrgentAlertsList() {
        return unreadUrgentAlertsList;
    }


    public List<Alert> getUnreadInformativeAlertsList() {
        return unreadInformativeAlertsList; 
    }

    public void addNewUrgentAlert(Alert newUrgentAlert){
        unreadUrgentAlertsList.add(0,newUrgentAlert);
    }

    public void addNewInformativeAlert(Alert newInformativeAlert){
        unreadInformativeAlertsList.add(0,newInformativeAlert);
    }

    public void markAlertAsRead(Alert readAlert){
        if(readAlert.getType().equals("urgente")) 
            unreadUrgentAlertsList.remove(readAlert);

        else unreadInformativeAlertsList.remove(readAlert);
    }

    public void deleteExpiredAlerts(){
        Iterator<Alert> controlExpiredIterator = unreadInformativeAlertsList.iterator();
        while(controlExpiredIterator.hasNext()){
            Alert alertToContronl = controlExpiredIterator.next();
            if(alertToContronl.getExpirationFlag()) unreadInformativeAlertsList.remove(alertToContronl);
        }

        controlExpiredIterator = unreadUrgentAlertsList.iterator();
        while(controlExpiredIterator.hasNext()){
            Alert alertToContronl = controlExpiredIterator.next();
            if(alertToContronl.getExpirationFlag()) unreadUrgentAlertsList.remove(alertToContronl);
        }

    }


    @Override
    public void update() {
        Alert newAlert = miNotifier.getLastAlert();

        if(newAlert.getType().equals("urgente")){
            unreadUrgentAlertsList.add(newAlert);
        }
        else unreadInformativeAlertsList.add(newAlert);
        
    }


    @Override
    public String toString() {
        return getName();
    }

    
    
}
