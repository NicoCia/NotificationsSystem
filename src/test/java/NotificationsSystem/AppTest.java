package NotificationsSystem;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;

import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    String testUserName = "test user";
    String testTopicString = "test topic";
    String testAlertTypeString = "urgente";
    Notifier testNotifier = new Notifier();
    
    
    User testUser = new User(testUserName, testNotifier);
    App testApp = new App();
    
    /**
     * Testeo que el nuevo user retorne el nombre asignado correctamente
     */
    @Test
    public void newUserNameShouldMatchWithTestUserName(){
        assertEquals(testUserName, testUser.getName());
    }
    /**
     * Testeo que al agregar un nuevo usuario el Notifier logre agregarlo correctamente
     */
    @Test
    public void addNewUserShouldReturnTrue(){
        assertTrue(testNotifier.addNewUser(testUser));
    }
    /**
     * Testeo que al agregar un nuevo usuario y recuperarlo por el nombre, me devuelva el user correcto
     */
    @Test
    public void addNewUserAndRecoverItByNameShouldBeEquals(){
        testNotifier.addNewUser(testUser);
        assertEquals(testNotifier.getUserByName(testUserName), testUser);
    }

    /**
     * Testeo que al intentar agregar un usuario duplicado el Notifier lo rechace
     */
    @Test
    public void addDuplicateUserShouldReturnFalse(){
        testNotifier.addNewUser(testUser);
        assertFalse(testNotifier.addNewUser(testUser));
    }

    /**
     * Testeo que al agregar un nuevo topico el Notifier logre agregarlo correctamente
     */
    @Test
    public void addNewTopicShouldReturnTrue(){
        assertTrue(testNotifier.addNewTopic(testTopicString));
    }

    /**
     * Testeo que al intentar agregar un topico duplicado el Notifier lo rechace
     */
    @Test
    public void addDuplicateTopicShouldReturnFalse(){
        testNotifier.addNewTopic(testTopicString);
        assertFalse(testNotifier.addNewTopic(testTopicString));
    }

    @Test
    public void createNotificationWithBasicParamsShouldReturnTrue(){
        String testParams = "topic=" + testTopicString + " -user=all  -type=urgente";
        testNotifier.addNewTopic(testTopicString);
        assertTrue(testNotifier.createNewNotification(testParams));
    }

    @Test
    public void createNotificationWithoutTopicParamShouldReturnFalse(){
        String testParams = "user=all  -type=urgente";
        assertFalse(testNotifier.createNewNotification(testParams));
    }

    @Test
    public void testExpiredNotifcationsControllerCheckDateTimeWithValidEntryShouldReturnTrue(){
        String testDateTimeString = "2007-12-03T10:15:30";
        assertTrue(ExpiredNotificationsController.checkValidDateTime(testDateTimeString));
    }

    @Test
    public void testExpiredNotifcationsControllerCheckDateTimeWithInvalidDay32ShouldReturnFalse(){
        String testDateTimeString = "2007-12-32T10:15:30";
        assertFalse(ExpiredNotificationsController.checkValidDateTime(testDateTimeString));
    }

    @Test
    public void testExpiredNotifcationsControllerCheckDateTimeWithInvalidDay00ShouldReturnFalse(){
        String testDateTimeString = "2007-12-00T10:15:30";
        assertFalse(ExpiredNotificationsController.checkValidDateTime(testDateTimeString));
    }

    @Test
    public void testExpiredNotifcationsControllerGetDurationMethoodWithFutureDateTimeShouldBePositive(){
        String testDateTimeString = "2023-12-31T10:15:30";
        Alert testAlert = new Alert(testTopicString, testAlertTypeString, testUserName, 0);
        ExpiredNotificationsController tExpiredNotificationsController = new ExpiredNotificationsController(testDateTimeString ,testAlert);
        LocalDateTime testDateTime = LocalDateTime.parse(testDateTimeString);
        assertFalse(tExpiredNotificationsController.getDurationBetweenNowAndExpirationDateTime(testDateTime).isNegative());
    }

    @Test
    public void testExpiredNotifcationsControllerGetDurationMethoodWithPastDateTimeShouldBeNegative(){
        String testDateTimeString = "2007-12-31T10:15:30";
        Alert testAlert = new Alert(testTopicString, testAlertTypeString, testUserName, 0);
        ExpiredNotificationsController tExpiredNotificationsController = new ExpiredNotificationsController(testDateTimeString ,testAlert);
        LocalDateTime testDateTime = LocalDateTime.parse(testDateTimeString);
        assertTrue(tExpiredNotificationsController.getDurationBetweenNowAndExpirationDateTime(testDateTime).isNegative());
    }

    @Test
    public void testCreateAlertWithFutureExpirationDateTimeShouldReturnTrue(){
        String testDateTimeString = LocalDateTime.now().plusSeconds(1).toString().substring(0,19);
        testNotifier.addNewTopic(testTopicString);
        String testAlertCreationParams = "topic=" + testTopicString + " -user=all -type=urgente -expirationdate=" + testDateTimeString;
        
        assertTrue(testNotifier.createNewNotification(testAlertCreationParams));
    }

    @Test
    public void testCreateAlertWithFutureExpirationDateTimeShouldCreateANewTrhead(){
        String testDateTimeString = LocalDateTime.now().plusNanos(10000).toString().substring(0,19);
        String testAlertCreationParams = "topic=" + testTopicString + " -user=all  -type=urgente -expirationdate=" + testDateTimeString;
        testNotifier.addNewTopic(testTopicString);
        testNotifier.createNewNotification(testAlertCreationParams);
        assertTrue(testNotifier.getExpirationsControllersThreadsList().size()>0);
    }

    @Test
    public void testCreateAlertWithFutureExpirationDateTimeAndWaitToExpireShouldBeTrue(){
        String testDateTimeString = LocalDateTime.now().plusSeconds(1).toString().substring(0,19);
        String testAlertCreationParams = "topic=" + testTopicString + " -user=all  -type=urgente -expirationdate=" + testDateTimeString;
        testNotifier.addNewTopic(testTopicString);
        testNotifier.createNewNotification(testAlertCreationParams);
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertTrue(testNotifier.getLastAlert().getExpirationFlag());
    }

    @Test
    public void testCreateAlertWithPastExpirationDateTimeShouldExpireInstantly(){
        String testDateTimeString = "2007-12-31T10:15:30";
        String testAlertCreationParams = "topic=" + testTopicString + " -user=all  -type=urgente -expirationdate=" + testDateTimeString;
        testNotifier.addNewTopic(testTopicString);
        testNotifier.createNewNotification(testAlertCreationParams);
        try {
            Thread.sleep(10);
            testNotifier.getLastAlert().getConcurrentSemaphore().acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Boolean result = testNotifier.getLastAlert().getExpirationFlag();
        testNotifier.getLastAlert().getConcurrentSemaphore().release();

        assertTrue(result);
    }

    // @Test
    // public void testExpiredNotifcationsController6(){
    //     String testDateTimeString = "2007-12-31T10:15:30";
    //     LocalDateTime testDateTime = LocalDateTime.parse(testDateTimeString);
    //     assertTrue(tExpiredNotificationsController.getDurationBetweenNowAndExpirationDateTime(testDateTime).isNegative());
    // }

}