package com.bscott.fitness.tracker.exception;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class AuthenticationExceptionTest {

    @Test
    public void testCreateException() {
        AuthenticationException authenticationException = new AuthenticationException("Invalid login");
        assertEquals("Invalid login", authenticationException.getMessage());
    }
}
