package com.mercadolibre.firequasar.service.test.unitary;

import com.mercadolibre.firequasar.model.Position;
import com.mercadolibre.firequasar.model.PositionMother;
import com.mercadolibre.firequasar.model.SatelliteMessage;
import com.mercadolibre.firequasar.model.SatelliteMessageMother;
import com.mercadolibre.firequasar.service.SatelliteOperatorService;
import com.mercadolibre.firequasar.service.SatelliteService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class SatelliteOperatorServiceTest {

    @Mock
    SatelliteService satelliteService;

    SatelliteOperatorService satelliteOperatorService;

    List<SatelliteMessage> satelliteMessages;

    @BeforeEach
    public void beforeEach(){
        satelliteOperatorService = new SatelliteOperatorService(satelliteService);
    }

    @Nested
    @DisplayName("Pruebas unitarias para encontrar la localización de la nave")
    class GetLocation {

        Position position;

        @Test
        void getLocationSuccess(){

            givenSatelliteMessages();
            givenSatellitesWithPositions();
            whenGetLocation();
            thenPositionIsCorrect();

        }

        @Test
        void getLocationWhenNotExistSatellites(){

            givenSatelliteMessagesNotExistPositionSatellite();
            givenFindPositionByNameFailed();
            thenShowNotFoundSatelliteException();

        }

        @Test
        void getLocationWhenOnlyThereIsOneSatelliteMessage(){

            givenOneSatelliteMessages();
            givenFindPositionByNameFailed();
            thenShowNotFoundSatelliteException();

        }

        @Test
        void getLocationWhenFailed(){

            givenSatelliteMessages();
            givenGetLocationFailed();
            thenShowNotFoundSatelliteException();

        }

        private void givenSatellitesWithPositions() {
            given(satelliteService.findPositionByName(any()))
                    .willReturn(PositionMother.allPositionSatellites().stream().map(Position::getPosition)
                            .toArray(double[][]::new));
        }

        private void givenFindPositionByNameFailed() {
            given(satelliteService.findPositionByName(any()))
                    .willThrow(IllegalArgumentException.class);
        }

        private void givenGetLocationFailed() {
            given(satelliteService.findPositionByName(any()))
                    .willThrow(RuntimeException.class);
        }

        private void thenPositionIsCorrect() {
            Assertions.assertEquals(-487.28591249999994, position.getX());
            Assertions.assertEquals(1557.0142249999997, position.getY());
        }

        private void thenShowNotFoundSatelliteException(){
            Assertions.assertThrows(SatelliteOperatorService.SatelliteOperatorServiceException.class, this::whenGetLocation);
        }

        private void whenGetLocation() {
            position = satelliteOperatorService.getLocation(satelliteMessages);
        }

        private void givenSatelliteMessagesNotExistPositionSatellite() {
            satelliteMessages = Arrays.asList(SatelliteMessageMother.satelliteMessageWithSatelliteNotExist(), SatelliteMessageMother.satelliteMessageWithSatelliteNotExist());
        }

        private void givenOneSatelliteMessages() {
            satelliteMessages = Arrays.asList(SatelliteMessageMother.satelliteMessageKenobi());
        }

    }

    @Nested
    @DisplayName("Pruebas unitarias para descifrar el mensaje")
    class GetMessage {

        String message;

        @Test
        void getMessageSuccess(){

            givenSatelliteMessages();
            whenGetMessage();
            thenMessageIsCorrect();

        }

        @Test
        void getMessageWhenFailed(){

            givenSatelliteIsNull();
            thenShowSatelliteOperatorServiceException();

        }

        @Test
        void getMessageDeleteWordRepeatedBefore(){

            givenSatelliteWordRepetead();
            whenGetMessage();
            thenMessageIsCorrect();

        }

        private void givenSatelliteWordRepetead(){
            satelliteMessages = Arrays.asList(SatelliteMessageMother.satelliteMessageKenobiTwo(), SatelliteMessageMother.satelliteMessageSkywalkerTwo(), SatelliteMessageMother.satelliteMessageSatoTwo());
        }

        private void whenGetMessage() {
            message = satelliteOperatorService.getMessage(satelliteMessages);
        }

        private void thenShowSatelliteOperatorServiceException(){
            Assertions.assertThrows(SatelliteOperatorService.SatelliteOperatorServiceException.class, this::whenGetMessage);
        }

        private void thenMessageIsCorrect() {
            Assertions.assertNotNull(message);
            Assertions.assertFalse(message.isEmpty());
            Assertions.assertEquals("este es un mensaje secreto", message);
        }

    }

    private void givenSatelliteMessages() {
        satelliteMessages = SatelliteMessageMother.allSatellitesExist();
    }

    private void givenSatelliteIsNull() {
        satelliteMessages = null;
    }

}
