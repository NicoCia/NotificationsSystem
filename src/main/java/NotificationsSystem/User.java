package NotificationsSystem;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String name;
    private List<Alert> unreadUrgentAlerts;
    private List<Alert> unreadInformativeAlerts;
    

    public User(String name) {
        this.name = name;
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
    
}
