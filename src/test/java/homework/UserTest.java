package homework;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UserTest {
    @Test
    public void testUserCreation() {
        User user = new User();
        user.setName("Olga");
        assertEquals("Olga", user.getName());
    }
}


