package com.teeceetech.trellogithubwebhooksapi.services;

import com.teeceetech.trellogithubwebhooksapi.web.models.github.Root;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;

class EventServiceTest {

    private EventService eventService;
    private EventService mockEventService;

    @BeforeEach
    void setUp() {
        BuilderService builderService = Mockito.mock(BuilderService.class);
        eventService = new EventService(builderService);
        mockEventService = Mockito.mock(EventService.class);
    }

    @Nested
    @DisplayName("when receiving an event and given")
    class receiveEvent {
        @Test
        @DisplayName("valid query - return [true, true]")
        void validQuery() {
            Root payload = new Root();
            String trelloKey = "key";
            String token = "token";
            String event = "event";
            List<Boolean> mockResponse = new ArrayList<>();
            mockResponse.add(true);
            mockResponse.add(true);

            doReturn(mockResponse).when(mockEventService).receiveEvent(payload, trelloKey, token, event);

            List<Boolean> response = mockEventService.receiveEvent(payload, trelloKey, token, event);

            assertEquals(mockResponse, response);
        }

        @Test
        @DisplayName("invalid trello key - return [false, false]")
        void invalidKeyQuery() {
            Root payload = new Root();
            String trelloKey = "INVALID";
            String token = "token";
            String event = "event";
            List<Boolean> mockResponse = new ArrayList<>();
            mockResponse.add(false);
            mockResponse.add(false);

            doReturn(mockResponse).when(mockEventService).receiveEvent(payload, trelloKey, token, event);

            List<Boolean> response = mockEventService.receiveEvent(payload, trelloKey, token, event);

            assertEquals(mockResponse, response);
        }

        @Test
        @DisplayName("invalid token - return [false, false]")
        void invalidTokenQuery() {
            Root payload = new Root();
            String trelloKey = "key";
            String token = "INVALID";
            String event = "event";
            List<Boolean> mockResponse = new ArrayList<>();
            mockResponse.add(false);
            mockResponse.add(false);

            doReturn(mockResponse).when(mockEventService).receiveEvent(payload, trelloKey, token, event);

            List<Boolean> response = mockEventService.receiveEvent(payload, trelloKey, token, event);

            assertEquals(mockResponse, response);
        }

        @Test
        @DisplayName("invalid event - return [false, false]")
        void invalidEventQuery() {
            Root payload = new Root();
            String trelloKey = "key";
            String token = "token";
            String event = "INVALID";
            List<Boolean> mockResponse = new ArrayList<>();
            mockResponse.add(false);
            mockResponse.add(false);

            doReturn(mockResponse).when(mockEventService).receiveEvent(payload, trelloKey, token, event);

            List<Boolean> response = mockEventService.receiveEvent(payload, trelloKey, token, event);

            assertEquals(mockResponse, response);
        }

        @Test
        @DisplayName("valid parameters in wrong order - return [false, false]")
        void wrongParametersOrderQuery() {
            Root payload = new Root();
            String trelloKey = "key";
            String token = "token";
            String event = "event";
            List<Boolean> mockResponse = new ArrayList<>();
            mockResponse.add(false);
            mockResponse.add(false);

            doReturn(mockResponse).when(mockEventService).receiveEvent(payload, token, event, trelloKey);

            List<Boolean> response = mockEventService.receiveEvent(payload, token, event, trelloKey);

            assertEquals(mockResponse, response);
        }

        @Test
        @DisplayName("null as trello key - throw exception")
        void nullKeyQuery() {
            Root payload = new Root();
            String trelloKey = "";
            String token = "token";
            String event = "event";

            NullPointerException exception = assertThrows(NullPointerException.class, () ->
                    eventService.receiveEvent(payload, trelloKey, token, event));

            assertEquals("missing key", exception.getMessage());
        }

        @Test
        @DisplayName("null as token - throw exception")
        void nullTokenQuery() {
            Root payload = new Root();
            String trelloKey = "key";
            String token = "";
            String event = "event";

            NullPointerException exception = assertThrows(NullPointerException.class, () ->
                    eventService.receiveEvent(payload, trelloKey, token, event));

            assertEquals("missing token", exception.getMessage());
        }

        @Test
        @DisplayName("null as event - throw exception")
        void nullEventQuery() {
            Root payload = new Root();
            String trelloKey = "key";
            String token = "token";
            String event = "";

            NullPointerException exception = assertThrows(NullPointerException.class, () ->
                    eventService.receiveEvent(payload, trelloKey, token, event));

            assertEquals("missing event", exception.getMessage());
        }

        @Test
        @DisplayName("null as payload - throw exception")
        void nullPayloadQuery() {
            String trelloKey = "key";
            String token = "token";
            String event = "event";

            NullPointerException exception = assertThrows(NullPointerException.class, () ->
                    eventService.receiveEvent(null, trelloKey, token, event));

            assertEquals("missing payload", exception.getMessage());
        }

        @Test
        @DisplayName("null as payload.action - throw exception")
        void nullActionQuery() {
            Root payload = new Root();
            String trelloKey = "key";
            String token = "token";
            String event = "event";

            NullPointerException exception = assertThrows(NullPointerException.class, () ->
                    eventService.receiveEvent(payload, trelloKey, token, event));

            assertEquals("missing action", exception.getMessage());
        }
    }

    @Test
    @DisplayName("when handling an event and given null response, throw exception")
    void handleEvent() {
        NullPointerException exception = assertThrows(NullPointerException.class, () ->
                eventService.handleEvent(null));

        assertEquals("missing response", exception.getMessage());
    }
}