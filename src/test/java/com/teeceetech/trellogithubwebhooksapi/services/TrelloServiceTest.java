package com.teeceetech.trellogithubwebhooksapi.services;

import com.teeceetech.trellogithubwebhooksapi.web.models.trello.Card;
import com.teeceetech.trellogithubwebhooksapi.web.models.trello.Root;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doReturn;

class TrelloServiceTest {

    private HttpService httpService;
    private TrelloService trelloService;

    @BeforeEach
    void setUp() {
        httpService = Mockito.mock(HttpService.class);
        trelloService = new TrelloService(httpService);
    }

    @Nested
    @DisplayName("when getting a card and given")
    class getCardId {

        @Test
        @DisplayName("valid parameters - return card ID")
        void validQuery() {
            String name = "testName";
            String trelloKey = "validKey";
            String token = "validToken";

            Card card = new Card();
            card.id = "testID";
            card.name = name;
            Root mockResponse = new Root();
            mockResponse.cards = new ArrayList<>();
            mockResponse.cards.add(card);

            doReturn(mockResponse).when(httpService).searchTrelloCards(anyString());

            String cardID = trelloService.getCardId(name, trelloKey, token);

            assertEquals(card.id, cardID);
        }

        @Test
        @DisplayName("invalid card name - return empty string")
        void invalidNameQuery() {
            String name = "WrongName";
            String trelloKey = "validKey";
            String token = "validToken";

            Card card = new Card();
            card.id = "testID";
            card.name = "testName";
            Root mockResponse = new Root();
            mockResponse.cards = new ArrayList<>();
            mockResponse.cards.add(card);

            doReturn(mockResponse).when(httpService).searchTrelloCards(anyString());

            String cardID = trelloService.getCardId(name, trelloKey, token);

            assertNotEquals(card.id, cardID);
            assertEquals("",cardID);
        }

        @Test
        @DisplayName("invalid card key - return empty string")
        void invalidKeyQuery() {
            String name = "testName";
            String trelloKey = "invalidKey";
            String token = "validToken";

            doReturn(new Root()).when(httpService).searchTrelloCards(anyString());

            String cardID = trelloService.getCardId(name, trelloKey, token);

            assertEquals("", cardID);
        }

        @Test
        @DisplayName("invalid card token - return empty string")
        void invalidTokenQuery() {
            String name = "testName";
            String trelloKey = "validKey";
            String token = "invalidToken";

            doReturn(new Root()).when(httpService).searchTrelloCards(anyString());

            String cardID = trelloService.getCardId(name, trelloKey, token);

            assertEquals("", cardID);
        }

        @Test
        @DisplayName("null as name - throw exception")
        void nameNullThrowException() throws NullPointerException{
            String name = "";
            String trelloKey = "Key";
            String token = "Token";

            NullPointerException exception = assertThrows(NullPointerException.class, () ->
                    trelloService.getCardId(name, trelloKey, token));

            assertEquals("missing name string", exception.getMessage());
        }

        @Test
        @DisplayName("null as key - throw exception")
        void keyNullThrowException() throws NullPointerException{
            String name = "name";
            String trelloKey = "";
            String token = "token";

            NullPointerException exception = assertThrows(NullPointerException.class, () ->
                    trelloService.getCardId(name, trelloKey, token));

            assertEquals("missing key", exception.getMessage());
        }

        @Test
        @DisplayName("null as token - throw exception")
        void tokenNullThrowException() throws NullPointerException{
            String name = "name";
            String trelloKey = "key";
            String token = "";

            NullPointerException exception = assertThrows(NullPointerException.class, () ->
                    trelloService.getCardId(name, trelloKey, token));

            assertEquals("missing token", exception.getMessage());
        }

        @Test
        @DisplayName("parameters are out of order - return empty string")
        void parametersWrongOrder() {
            String name = "name";
            String trelloKey = "key";
            String token = "token";

            doReturn(null).when(httpService).searchTrelloCards(anyString());

            String cardID = trelloService.getCardId(token, name, trelloKey);

            assertEquals("",cardID);
        }
    }

    @Nested
    @DisplayName("when adding attachment and given")
    class addCardAttachment {

        @Test
        @DisplayName("valid query parameters - return true")
        void validAttachmentQuery() {
            String ID = "ID";
            String attachmentURL = "attachment_url";
            String trelloKey = "key";
            String token = "token";

            doReturn(200).when(httpService).postToTrello(anyString(), any());

            Boolean success = trelloService.addCardAttachment(ID, attachmentURL, trelloKey, token);

            assertEquals(true, success);
        }

        @Test
        @DisplayName("invalid card ID - return false")
        void invalidIdQuery() {
            String ID = "INVALID";
            String attachmentURL = "attachment_url";
            String trelloKey = "key";
            String token = "token";

            doReturn(0).when(httpService).postToTrello(anyString(), any());

            Boolean success = trelloService.addCardAttachment(ID, attachmentURL, trelloKey, token);

            assertEquals(false, success);
        }

        @Test
        @DisplayName("invalid attachment url - return false")
        void invalidUrlQuery() {
            String ID = "ID";
            String attachmentURL = "INVALID";
            String trelloKey = "key";
            String token = "token";

            doReturn(0).when(httpService).postToTrello(anyString(), any());

            Boolean success = trelloService.addCardAttachment(ID, attachmentURL, trelloKey, token);

            assertEquals(false, success);
        }

        @Test
        @DisplayName("invalid trello key - return false")
        void invalidKeyQuery() {
            String ID = "ID";
            String attachmentURL = "attachment_url";
            String trelloKey = "INVALID";
            String token = "token";

            doReturn(0).when(httpService).postToTrello(anyString(), any());

            Boolean success = trelloService.addCardAttachment(ID, attachmentURL, trelloKey, token);

            assertEquals(false, success);
        }

        @Test
        @DisplayName("invalid trello token - return false")
        void invalidTokenQuery() {
            String ID = "ID";
            String attachmentURL = "attachment_url";
            String trelloKey = "key";
            String token = "INVALID";

            doReturn(0).when(httpService).postToTrello(anyString(), any());

            Boolean success = trelloService.addCardAttachment(ID, attachmentURL, trelloKey, token);

            assertEquals(false, success);
        }

        @Test
        @DisplayName("parameters are valid but out of order - return false")
        void parametersWrongOrder() {
            String ID = "ID";
            String attachmentURL = "attachment_url";
            String trelloKey = "key";
            String token = "token";

            doReturn(0).when(httpService).postToTrello(anyString(), any());

            Boolean success = trelloService.addCardAttachment(trelloKey, token, ID, attachmentURL);

            assertEquals(false, success);
        }

        @Test
        @DisplayName("null card ID - throw null exception")
        void idNullThrowException() {
            String ID = "";
            String attachmentURL = "attachment_url";
            String trelloKey = "key";
            String token = "token";

            NullPointerException exception = assertThrows(NullPointerException.class, () ->
                    trelloService.addCardAttachment(ID, attachmentURL, trelloKey, token));

            assertEquals("missing card ID", exception.getMessage());
        }

        @Test
        @DisplayName("null attachment URL - throw null exception")
        void attachmentUrlNullThrowException() {
            String ID = "ID";
            String attachmentURL = "";
            String trelloKey = "key";
            String token = "token";

            NullPointerException exception = assertThrows(NullPointerException.class, () ->
                    trelloService.addCardAttachment(ID, attachmentURL, trelloKey, token));

            assertEquals("missing attachment URL", exception.getMessage());
        }

        @Test
        @DisplayName("null trello key - throw null exception")
        void keyNullThrowException() {
            String ID = "ID";
            String attachmentURL = "attachment_url";
            String trelloKey = "";
            String token = "token";

            NullPointerException exception = assertThrows(NullPointerException.class, () ->
                    trelloService.addCardAttachment(ID, attachmentURL, trelloKey, token));

            assertEquals("missing key", exception.getMessage());
        }

        @Test
        @DisplayName("null trello token - throw null exception")
        void tokenNullThrowException() {
            String ID = "ID";
            String attachmentURL = "attachment_url";
            String trelloKey = "key";
            String token = "";

            NullPointerException exception = assertThrows(NullPointerException.class, () ->
                    trelloService.addCardAttachment(ID, attachmentURL, trelloKey, token));

            assertEquals("missing token", exception.getMessage());
        }
    }

    @Nested
    @DisplayName("when adding a comment and given")
    class addCardComment {

        @Test
        @DisplayName("valid query parameters - return true")
        void validCommentQuery() {
            String ID = "ID";
            String text = "text";
            String trelloKey = "key";
            String token = "token";

            doReturn(200).when(httpService).postToTrello(anyString(), any());

            Boolean success = trelloService.addCardComment(ID, text, trelloKey, token);

            assertEquals(true, success);
        }

        @Test
        @DisplayName("invalid card ID - return false")
        void invalidIdQuery() {
            String ID = "INVALID";
            String text = "text";
            String trelloKey = "key";
            String token = "token";

            doReturn(0).when(httpService).postToTrello(anyString(), any());

            Boolean success = trelloService.addCardComment(ID, text, trelloKey, token);

            assertEquals(false, success);
        }

        @Test
        @DisplayName("invalid text - return false")
        void invalidUrlQuery() {
            String ID = "ID";
            String text = "INVALID";
            String trelloKey = "key";
            String token = "token";

            doReturn(0).when(httpService).postToTrello(anyString(), any());

            Boolean success = trelloService.addCardComment(ID, text, trelloKey, token);

            assertEquals(false, success);
        }

        @Test
        @DisplayName("invalid trello key - return false")
        void invalidKeyQuery() {
            String ID = "ID";
            String text = "text";
            String trelloKey = "INVALID";
            String token = "token";

            doReturn(0).when(httpService).postToTrello(anyString(), any());

            Boolean success = trelloService.addCardComment(ID, text, trelloKey, token);

            assertEquals(false, success);
        }

        @Test
        @DisplayName("invalid trello token - return false")
        void invalidTokenQuery() {
            String ID = "ID";
            String text = "text";
            String trelloKey = "key";
            String token = "INVALID";

            doReturn(0).when(httpService).postToTrello(anyString(), any());

            Boolean success = trelloService.addCardComment(ID, text, trelloKey, token);

            assertEquals(false, success);
        }

        @Test
        @DisplayName("parameters are valid but out of order - return false")
        void parametersWrongOrder() {
            String ID = "ID";
            String text = "text";
            String trelloKey = "key";
            String token = "token";

            doReturn(0).when(httpService).postToTrello(anyString(), any());

            Boolean success = trelloService.addCardComment(trelloKey, token, ID, text);

            assertEquals(false, success);
        }

        @Test
        @DisplayName("null card ID - throw null exception")
        void idNullThrowException() {
            String ID = "";
            String text = "text";
            String trelloKey = "key";
            String token = "token";

            NullPointerException exception = assertThrows(NullPointerException.class, () ->
                    trelloService.addCardComment(ID, text, trelloKey, token));

            assertEquals("missing card ID", exception.getMessage());
        }

        @Test
        @DisplayName("null text - throw null exception")
        void attachmentUrlNullThrowException() {
            String ID = "ID";
            String text = "";
            String trelloKey = "key";
            String token = "token";

            NullPointerException exception = assertThrows(NullPointerException.class, () ->
                    trelloService.addCardComment(ID, text, trelloKey, token));

            assertEquals("missing comment text", exception.getMessage());
        }

        @Test
        @DisplayName("null trello key - throw null exception")
        void keyNullThrowException() {
            String ID = "ID";
            String text = "text";
            String trelloKey = "";
            String token = "token";

            NullPointerException exception = assertThrows(NullPointerException.class, () ->
                    trelloService.addCardComment(ID, text, trelloKey, token));

            assertEquals("missing key", exception.getMessage());
        }

        @Test
        @DisplayName("null trello token - throw null exception")
        void tokenNullThrowException() {
            String ID = "ID";
            String text = "text";
            String trelloKey = "key";
            String token = "";

            NullPointerException exception = assertThrows(NullPointerException.class, () ->
                    trelloService.addCardComment(ID, text, trelloKey, token));

            assertEquals("missing token", exception.getMessage());
        }
    }
}