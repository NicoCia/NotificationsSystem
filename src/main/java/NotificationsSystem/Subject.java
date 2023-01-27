package NotificationsSystem;

/* Interfaz para patron de diseño Observer 
 */
public interface Subject {

    public void registerObserver(Observer o);
    public void removeObserver(Observer o);
    public void notifyObservers();
}