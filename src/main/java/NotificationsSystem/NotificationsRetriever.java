package NotificationsSystem;

import java.util.ArrayList;
import java.util.List;

public class NotificationsRetriever {
    private Notifier miNotifier;

    public NotificationsRetriever(Notifier miNotifier) {
        this.miNotifier = miNotifier;
    }

    public List<Alert> getEspecificUserUnreadAndUnexpiredAlerts(String params){
   
        String userName = getUserNameOrTopicFromParam(params);
        List<Alert> returnList = new ArrayList<Alert>();
        User especificUser = miNotifier.getUserByName(userName);
        
        if(especificUser != null){
            especificUser.deleteExpiredAlerts();
            List<Alert> unreadUrgentAlerts = especificUser.getUnreadUrgentAlertsList();
            List<Alert> unreadInformativeAlerts = especificUser.getUnreadInformativeAlertsList();
            returnList.addAll(unreadUrgentAlerts);
            returnList.addAll(unreadInformativeAlerts);
        }

        return returnList;
    }

    public List<Alert> getTopicUnexpiredAlerts(String params){
        
        String topic = getUserNameOrTopicFromParam(params);
        List<Alert> returnList = new ArrayList<Alert>();
        NotificationsDispatcher especificTopic = (NotificationsDispatcher) miNotifier.getSubjectByTopic(topic);
        
        if(especificTopic != null){
            especificTopic.deleteExpiredAlerts();
            List<Alert> sendUrgentAlerts = especificTopic.getSendUrgentAlertsList();
            List<Alert> sendInformativeAlerts = especificTopic.getSendInformativeAlertsList();
            returnList.addAll(sendUrgentAlerts);
            returnList.addAll(sendInformativeAlerts);
        }

        return returnList;
    }

    private String getUserNameOrTopicFromParam(String param){
        // TODO obtener el nombre de usuario o de topico desde el parameto
        String returnNameText = "";

        if(!param.equals("")&&param.contains("=")){
            /* se espera un parametro de la forma name=NombreDeUsuarioOTOpico
            * por lo que el texto a retornar estara en la posicion 1 del split
            * al dividir el texto con el caracter '='
            */
            returnNameText = param.split("=",2)[1];
            returnNameText = returnNameText.replace(" ", ""); // quito espacios en blanco
        } 
        return returnNameText;
    }

}
