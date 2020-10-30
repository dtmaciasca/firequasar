package com.mercadolibre.firequasar.service.test.unitary;

import com.mercadolibre.firequasar.jpa.MessageReceivedRepository;
import com.mercadolibre.firequasar.jpa.SatelliteRepository;
import com.mercadolibre.firequasar.model.*;
import com.mercadolibre.firequasar.service.MessageReceivedService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class MessageReceivedServiceTest {

    @Mock
    MessageReceivedRepository messageReceivedRepository;

    MessageReceivedService messageReceivedService;

    @BeforeEach
    public void beforeEach(){
        messageReceivedService = new MessageReceivedService(messageReceivedRepository);
    }

    @Nested
    @DisplayName("Pruebas unitarias para crear mensaje recibido por parte del satélite")
    class Create {

        Satellite satellite;
        DistanceMessage distanceMessage;
        String result;

        @Test
        void createNewMessageReceivedSuccess(){

            givenSatelliteAndDistanceMessage();
            giveMessageReceivedNewSuccess();
            whenCreate();
            thenResultIsCorrect("Agregó la distancia y el mensaje del satélite kenobi");

        }

        @Test
        void updateMessageReceivedSuccess(){

            givenSatelliteAndDistanceMessage();
            giveExistsMessageReceivedSuccess();
            whenCreate();
            thenResultIsCorrect("Actualizó la distancia y el mensaje para el satélite kenobi");

        }

        @Test
        void createNotFoundSatellite(){

            givenSatelliteNotExists();
            giveMessageReceivedNotFoundSatellite();
            thenShowMessageReceivedServiceException();

        }

        @Test
        void createFailed(){

            givenSatelliteAndDistanceMessage();
            giveSaveFailed();
            thenShowMessageReceivedServiceException();

        }

        private void thenShowMessageReceivedServiceException(){
            Assertions.assertThrows(MessageReceivedService.MessageReceivedServiceException.class, this::whenCreate);
        }

        private void giveExistsMessageReceivedSuccess() {
            given(messageReceivedRepository.findBySatellite(any()))
                    .willReturn(MessageReceivedMother.messageReceivedKenobi());
            given(messageReceivedRepository.save(any())).willReturn(MessageReceivedMother.messageReceivedKenobi());
        }

        private void giveMessageReceivedNewSuccess() {
            given(messageReceivedRepository.findBySatellite(any()))
                    .willReturn(null);
            given(messageReceivedRepository.save(any())).willReturn(MessageReceivedMother.messageReceivedKenobi());
        }

        private void giveMessageReceivedNotFoundSatellite() {
            given(messageReceivedRepository.findBySatellite(any()))
                    .willThrow(SatelliteRepository.NotFoundSatelliteException.class);
        }

        private void giveSaveFailed() {
            given(messageReceivedRepository.save(any()))
                    .willThrow(RuntimeException.class);
        }

        private void thenResultIsCorrect(String message) {
            Assertions.assertNotNull(result);
            Assertions.assertEquals(message, result);
        }

        private void whenCreate() {
            result = messageReceivedService.create(satellite, distanceMessage);
        }

        private void givenSatelliteAndDistanceMessage() {
            satellite = SatelliteMother.satelliteKenobi();
            distanceMessage = DistanceMessageMother.distanceMessageKenobi();
        }

        private void givenSatelliteNotExists() {
            satellite = SatelliteMother.satelliteNotExits();
            distanceMessage = DistanceMessageMother.distanceMessageKenobi();
        }

    }

    @Nested
    @DisplayName("Pruebas unitarias para encontrar todos los mensaje recibido por parte de los satélites")
    class FindAll {

        List<MessageReceived> messageReceiveds;

        @Test
        void findAllSuccess(){

            givenfindAllSuccess();
            whenFindAll();
            thenMessageReceivedNotEmpty();

        }

        private void givenfindAllSuccess() {
            given(messageReceivedRepository.findAll())
                    .willReturn(MessageReceivedMother.all());
        }

        private void whenFindAll() {
            messageReceiveds = messageReceivedService.findAll();
        }

        private void thenMessageReceivedNotEmpty() {
            Assertions.assertNotNull(messageReceiveds);
            Assertions.assertFalse(messageReceiveds.isEmpty());
            Assertions.assertEquals(3, messageReceiveds.size());
        }

    }
}
