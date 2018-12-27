package by.fertigi.app.util;

import org.junit.Test;

import static org.junit.Assert.*;

public class ValidatorTest {

    @Test
    public void replace() {
        String badString = " !\"#$%&'()*+,-./0123456789:;<=>?@ABCDE¢FGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~";
        String replaceString = " !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~";

        assertEquals(Validator.replace(badString), replaceString);
    }

    @Test
    public void contains() {
        String stringContainsTrue = " !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~";
        String stringContainsFalse = " !\"#$%&'()*+,-./0123456789:¢;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~";

        assertTrue(Validator.contains(stringContainsTrue));
        assertTrue(!Validator.contains(stringContainsFalse));
    }
}