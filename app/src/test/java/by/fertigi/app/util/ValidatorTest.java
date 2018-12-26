package by.fertigi.app.util;

import org.junit.Test;

import static org.junit.Assert.*;

public class ValidatorTest {

    @Test
    public void replace() {
        String badString = "qwe¥rty";
        String replaceString = "qwerty";

        assertEquals(Validator.replace(badString), replaceString);
    }

    @Test
    public void contains() {
        String stringContainsTrue = "qwertyuiopasdfghjklzxcvbnm1234567890+-!=,.";
        String stringContainsFalse = "qwertyuiopasdfghjkl¥zxcvbnm1234567890+-!=,.";

        assertTrue(Validator.contains(stringContainsTrue));
        assertTrue(!Validator.contains(stringContainsFalse));
    }
}