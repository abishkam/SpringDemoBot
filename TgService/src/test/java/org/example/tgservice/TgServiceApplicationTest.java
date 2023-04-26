package org.example.tgservice;

import org.example.tgservice.handler.MessageHandler;
import org.example.tgservice.handler.StartMessageHandler;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.springframework.boot.test.context.SpringBootTest;

import org.junit.jupiter.api.Assertions;

import java.util.Map;


@SpringBootTest
public class TgServiceApplicationTest {



    @Test
    void startMessageHandlerTest() {

        Map<String, MessageHandler> rightHandler = mock(Map.class);
        StartMessageHandler startMessageHandler = mock(StartMessageHandler.class);
        when(rightHandler.get(anyString())).thenReturn(startMessageHandler);
        Assertions.assertTrue(rightHandler.get("/start") instanceof StartMessageHandler);

    }




}
