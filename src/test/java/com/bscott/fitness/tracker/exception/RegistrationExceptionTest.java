package com.bscott.fitness.tracker.exception;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class RegistrationExceptionTest {

    @Test
    public void testCreateException() {
        RegistrationException registrationException = new RegistrationException("There is already an account with this userName");
        assertEquals("There is already an account with this userName", registrationException.getMessage());
    }
}
