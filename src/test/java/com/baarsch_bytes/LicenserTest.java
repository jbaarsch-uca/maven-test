package com.baarsch_bytes;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class LicenserTest {

    @Test
    public void testIssueLicense_Success() {
        Licenser licenser = new Licenser();

        boolean result = licenser.issueLicense(40.0, true);

        assertTrue(result, "License should be issued when hours >= 40 and test is passed");
    }

    @Test
    void testIssueLicense_FailsDueToHours() {
        Licenser licenser = new Licenser();
        // Act
        boolean result = licenser.issueLicense(39.9, true);
        // Assert
        assertFalse(result, "License should not be issued if hours are less than 40.");
    }

    @Test
    void testIssueLicense_FailsDueToTest() {
        Licenser licenser = new Licenser();
        // Act
        boolean result = licenser.issueLicense(50.0, false);
        // Assert
        assertFalse(result, "License should not be issued if the test was not passed.");
    }


}
