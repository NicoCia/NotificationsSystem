package NotificationsSystem;

public class Register {
    private Notifier miNotifier;

    public Register(Notifier miNotifier) {
        this.miNotifier = miNotifier;
    }

    public void createNewRegistration(){
        // TODO implement method
    }

    public Boolean registerUser(String newUserName){
        User newUser = new User(newUserName, miNotifier);

        return miNotifier.addNewUser(newUser);
    }

    public Boolean registerNewTopic(String newTopString){
        return miNotifier.addNewTopic(newTopString);
    }

    public Boolean registerUserToTopic(String suscriberUserName, String topic){
        return miNotifier.suscribeUserToTopic(suscriberUserName, topic);
    }
}
