package NotificationsSystem;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

public class ExpiredNotificationsController implements Runnable {

    private LocalDateTime alertExpirationDateTime;
    private Alert expiredAlert;

    

    public ExpiredNotificationsController(String alertExpirationDateTimeString, Alert expiredAlert) {
        this.alertExpirationDateTime = createLocalDateTimeFromString(alertExpirationDateTimeString);
        this.expiredAlert = expiredAlert;
    }

    public Duration getDurationBetweenNowAndExpirationDateTime(LocalDateTime expirationDateTime){
        Duration expirationDuration = Duration.between(LocalDateTime.now(), expirationDateTime);
        return expirationDuration;
    }

    public LocalDateTime createLocalDateTimeFromString(String dateTimeString){
        LocalDateTime dateTime;
        try{
            dateTime = LocalDateTime.parse(dateTimeString);
        }
        catch (DateTimeParseException e){
            dateTime = LocalDateTime.now().minusSeconds(1);
        }
        return dateTime;
    }

    public static Boolean checkValidDateTime(String dateTimeString){
        // 2007-12-03T10:15:30
        String validYears = "([0-9][0-9][0-9][0-9])"; // Controla años de cuatro digitos 
        String validMonths = "((0[1-9])|(1[0-9])|(1[0-2]))"; // Controla meses entre 01 y 12 (de enero a diciembre)
        String validDays = "((0[1-9])|([1-2][0-9])|3[01])"; // Controla dias entre 01 y 31;
        String validDates = validYears + "\\-" + validMonths + "\\-" + validDays;
        String validHours = "(([01][0-9])|(2[0-3]))"; // Controla horas entre 00 y  24
        String validMinutesAndSeconds = "([0-5][0-9])";
        String validTime = validHours + ":" + validMinutesAndSeconds + ":" + validMinutesAndSeconds;
        String validDateTimeRegex = validDates + "T" + validTime;
        return dateTimeString.matches(validDateTimeRegex);
    }


    @Override
    public void run() {
        Duration alertExpirationDuration = getDurationBetweenNowAndExpirationDateTime(alertExpirationDateTime);
        
        if(!alertExpirationDuration.isNegative()){
            try {
                Thread.sleep(alertExpirationDuration.toMillis());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            expiredAlert.getConcurrentSemaphore().acquire();
            expiredAlert.setExpirationFlag(true);
            expiredAlert.getConcurrentSemaphore().release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
    }
    
}
