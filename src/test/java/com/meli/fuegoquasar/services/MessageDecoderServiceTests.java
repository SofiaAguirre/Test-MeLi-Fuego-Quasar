package com.meli.fuegoquasar.services;

import com.meli.fuegoquasar.exceptions.InvalidMessageException;
import com.meli.fuegoquasar.entities.Satellite;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringRunner.class)
public class MessageDecoderServiceTests {

    @MockBean
    private MessageDecoderService messageDecoderService;

    private List<Satellite> satelliteList;

    @BeforeEach
    public void setUp() {
        Satellite satellite1 = new Satellite("kenobi", 100, Arrays.asList("este", "", "", "mensaje", ""));
        Satellite satellite2 = new Satellite("skywalker", 115.5, Arrays.asList("", "es", "", "",  "secreto"));
        Satellite satellite3 = new Satellite("sato", 142.7, Arrays.asList("este", "", "un", "", ""));
        satelliteList = Arrays.asList(satellite1, satellite2, satellite3);
    }

    @Test
    public void testInvalidMessageSize() {
        // given
        Satellite satellite1 = new Satellite("kenobi", 100, Arrays.asList("este", "", "", "mensaje", ""));
        Satellite satellite2 = new Satellite("skywalker", 115.5, Arrays.asList("", "es", "", "",  "secreto", ""));
        Satellite satellite3 = new Satellite("sato", 142.7, Arrays.asList("este", "", "un"));
        List<Satellite> satelliteList = Arrays.asList(satellite1, satellite2, satellite3);

        when(messageDecoderService.getMessage(satelliteList)).thenThrow(InvalidMessageException.class);

        // then
        assertThrows(InvalidMessageException.class, () -> messageDecoderService.getMessage(satelliteList));
    }

    @Test
    public void testValidDecodedMessage() {
        // given
        String expectedMessage = "las fuerzas enemigas avanzan hacia el oeste";
        when(messageDecoderService.getMessage(satelliteList)).thenReturn(expectedMessage);

        // when
        String decodedMessage = messageDecoderService.getMessage(satelliteList);

        // then
        Assert.assertEquals(expectedMessage, decodedMessage);
    }

}
