package NotificationsSystem;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    String testUserName = "test user";
    String testTopicString = "test topic";
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

    

}