package com.vesinitsyn.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class DummyServiceTest {

    @InjectMocks
    private DummyService dummyService;

    @Test
    void doSomething_WhenCalled_ShouldReturnExpectedValue() {
        // Given
        int expectedValue = 1;

        // When
        int actualValue = dummyService.doSomething();

        // Then
        assertEquals(expectedValue, actualValue);
    }
}