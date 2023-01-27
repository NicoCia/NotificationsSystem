package NotificationsSystem;

import java.util.List;

public class App {
    public static void main(String[] args) throws Exception {
        Notifier miNotifier = new Notifier();
        Register miRegister = new Register(miNotifier);
        NotificationsRetriever miRetriever = new NotificationsRetriever(miNotifier);
        
        for(String arg: args){
            String aux[] = arg.split(" -", 2);

            /*El campo 0 de aux sera el comando a ejecutar. Quito espacios que pueda haber al comienzo o final
             * del comando y convierto a minusculas para evitar case sensitive
             */
            String command = aux[0].toLowerCase().replaceAll(" ", "");
            String params = "";
            if(aux.length>0) params = aux[1];

            switch(command){
                case "register":                    miRegister.createNewRegistration(params);                       break;
                case "subscribe":                   miNotifier.suscribeUserToTopic(params);                         break;
                case "sendalert":                   miNotifier.createNewNotification(params);                       break;
                case "markalertasread":             miNotifier.markUserAlertAsRead(params);                         break;
                case "recoveruserpendingalerts":    miRetriever.getEspecificUserUnreadAndUnexpiredAlerts(params);   break;
                case "recovertopicunexpiredalerts": miRetriever.getTopicUnexpiredAlerts(params);                    break;

                default: break;
            }
        }

        List<Thread> threadsToInterruptAndJoin = miNotifier.getExpirationsControllersThreadsList();
        
        for(Thread thread : threadsToInterruptAndJoin){
            try{
                thread.interrupt();
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        
    }
}
