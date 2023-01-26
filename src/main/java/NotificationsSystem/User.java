package NotificationsSystem;

import java.util.ArrayList;
import java.util.List;

public class User implements Observer{
    private String name;
    private List<Alert> unreadUrgentAlerts;
    private List<Alert> unreadInformativeAlerts;
    private Notifier miNotifier;
    

    public User(String name, Notifier miNotifier) {
        this.name = name;
        this.miNotifier = miNotifier;
        unreadUrgentAlerts = new ArrayList<Alert> ();
        unreadInformativeAlerts = new ArrayList<Alert> ();
    }

    
    public String getName() {
        return name;
    }


    public List<Alert> getUnreadUrgentAlerts() {
        return unreadUrgentAlerts;
    }


    public List<Alert> getUnreadInformativeAlerts() {
        return unreadInformativeAlerts; 
    }

    public void addNewUrgentAlert(Alert newUrgentAlert){
        unreadUrgentAlerts.add(0,newUrgentAlert);
    }

    public void addNewInformativeAlert(Alert newInformativeAlert){
        unreadInformativeAlerts.add(0,newInformativeAlert);
    }

    public void markAlertAsRead(Alert readAlert){
        if(readAlert.getType().equals("urgente")) 
            unreadUrgentAlerts.remove(readAlert);

        else unreadInformativeAlerts.remove(readAlert);
    }


    @Override
    public void update() {
        Alert newAlert = miNotifier.getLastAlert();

        if(newAlert.getType().equals("urgente")){
            unreadUrgentAlerts.add(newAlert);
        }
        else unreadInformativeAlerts.add(newAlert);
        
    }


    @Override
    public String toString() {
        return getName();
    }

    
    
}
