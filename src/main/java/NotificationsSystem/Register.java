package NotificationsSystem;

import java.util.ArrayList;
import java.util.List;

public class Register {
    private Notifier miNotifier;

    public Register(Notifier miNotifier) {
        this.miNotifier = miNotifier;
    }

    public void createNewRegistration(String params){

        List<String> paramsValuesList = getParamsValuesListFromString(params);
        if(paramsValuesList.size()>1){
            String name = paramsValuesList.get(1);
            String clase = paramsValuesList.get(0);
            switch(clase){
                case "topic": registerNewTopic(name); break;
                case "user": registerUser(name); break;
                default: break;
            }
        }
    }

    public Boolean registerUser(String newUserName){
        User newUser = new User(newUserName, miNotifier);

        return miNotifier.addNewUser(newUser);
    }

    public Boolean registerNewTopic(String newTopString){
        return miNotifier.addNewTopic(newTopString);
    }

    private List<String> getParamsValuesListFromString(String params){
        String[] paramsList = params.split(" -", 10);
        ArrayList<String> returnParamsValuesList = new ArrayList<>();

        for(String param: paramsList){
            param = param.replace(" ", ""); //quito espacios en blanco
            if(param.contains("=")){
                String[] camp_valueList = param.split("=", 2); // el indice sero sera el campor y el indice 1 sera el valor a asignar
                if(camp_valueList[0].equals("class")) returnParamsValuesList.add(0, camp_valueList[1]);
                else returnParamsValuesList.add(camp_valueList[1]);
            }
        }
        return returnParamsValuesList;
    }
}
