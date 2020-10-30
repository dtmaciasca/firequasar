package com.mercadolibre.firequasar.service.test.unitary;

import com.mercadolibre.firequasar.model.*;
import com.mercadolibre.firequasar.service.FireQuasarService;
import com.mercadolibre.firequasar.service.MessageReceivedService;
import com.mercadolibre.firequasar.service.SatelliteOperatorService;
import com.mercadolibre.firequasar.service.SatelliteService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class FireQuasarServiceTest {

    @Mock
    SatelliteOperatorService satelliteOperatorService;

    @Mock
    SatelliteService satelliteService;

    @Mock
    MessageReceivedService messageReceivedService;

    FireQuasarService fireQuasarService;

    private SatelliteResponse satelliteResponse;

    @BeforeEach
    public void beforeEach(){
        fireQuasarService = new FireQuasarService(satelliteOperatorService, satelliteService, messageReceivedService);
    }

    @Nested
    @DisplayName("Pruebas unitarias para obtener la localización y mensaje")
    class GetUbicationShapeAndMessage {

        private SatelliteRequest satelliteRequest;

        @Test
        void getUbicationShapeAndMessageSuccess(){

            givenSatelliteRequest();
            giveGetUbicationShapeAndMessageSuccess();
            whenGetUbicationShapeAndMessage();
            thenValuesAreCorrect("este es un mensaje secreto", -487.28591249999994, 1557.0142249999997);

        }

        @Test
        void getUbicationShapeAndMessageWithSatelliteNotExits(){

            givenSatelliteRequestWithSatelliteNotExists();
            giveGetUbicationShapeAndMessageWithError();
            thenShowExceptionSatelliteNotFound();

        }

        @Test
        void getUbicationShapeAndMessageWithOneOnlySatellite(){

            givenSatelliteRequestWithOneSatellite();
            giveGetUbicationShapeAndMessageWithError();
            thenShowExceptionSatelliteNotFound();

        }

        @Test
        void getUbicationShapeAndMessageWithFourSatellite(){

            givenSatelliteRequestWithFourSatellite();
            giveGetUbicationShapeAndMessageWithError();
            thenShowExceptionSatelliteNotFound();

        }

        @Test
        void getUbicationShapeAndMessageWithTwoSatellites(){

            givenSatelliteRequestWithTwoSatellites();
            giveGetUbicationShapeAndMessageSuccessA();
            whenGetUbicationShapeAndMessage();
            thenValuesAreCorrect("este un mensaje", 82.72784999999999, -200);

        }

        private void givenSatelliteRequest() {
            satelliteRequest = SatelliteRequest.builder().satellites(SatelliteMessageMother.allSatellitesExist()).build();
        }

        private void givenSatelliteRequestWithSatelliteNotExists() {
            satelliteRequest = SatelliteRequest.builder().satellites(Arrays.asList(SatelliteMessageMother.satelliteMessageWithSatelliteNotExist())).build();
        }

        private void givenSatelliteRequestWithOneSatellite() {
            satelliteRequest = SatelliteRequest.builder().satellites(Arrays.asList(SatelliteMessageMother.satelliteMessageKenobi())).build();
        }

        private void givenSatelliteRequestWithTwoSatellites() {
            satelliteRequest = SatelliteRequest.builder().satellites(Arrays.asList(SatelliteMessageMother.satelliteMessageKenobi(), SatelliteMessageMother.satelliteMessageSato())).build();
        }

        private void givenSatelliteRequestWithFourSatellite() {
            satelliteRequest = SatelliteRequest.builder().satellites(SatelliteMessageMother.all()).build();
        }

        private void giveGetUbicationShapeAndMessageWithError(){
            given(satelliteOperatorService.getLocation(satelliteRequest.getSatellites()))
                    .willThrow(SatelliteOperatorService.SatelliteOperatorServiceException.class);
        }

        private void whenGetUbicationShapeAndMessage() {
            satelliteResponse = fireQuasarService.getUbicationShapeAndMessage(satelliteRequest);
        }

        private void giveGetUbicationShapeAndMessageSuccessA(){
            given(satelliteOperatorService.getLocation(satelliteRequest.getSatellites()))
                    .willReturn(PositionMother.positionTwo());
            given(satelliteOperatorService.getMessage(satelliteRequest.getSatellites()))
                    .willReturn("este un mensaje");
        }

        private void thenShowExceptionSatelliteNotFound(){
            Assertions.assertThrows(FireQuasarService.FireQuasarServiceException.class, this::whenGetUbicationShapeAndMessage);
        }
    }

    @Nested
    @DisplayName("Pruebas unitarias para guardar distancia y mensaje de un satelite")
    class SaveDistanceAndMessageSatellite {

        String name;
        DistanceMessage distanceMessage;
        String result;

        @Test
        void saveNewDistanceAndMessageSatelliteSuccess(){

            givenSatelliteWithDistanceAndMessage();
            giveSaveDistanceAndMessageSatelliteSuccess("Agregó la distancia y el mensaje del satélite kenobi");
            whenSaveDistanceAndMessageSatellite();
            thenResultIsCorrect("Agregó la distancia y el mensaje del satélite kenobi");

        }

        @Test
        void updateDistanceAndMessageSatelliteSuccess(){

            givenSatelliteWithMessageReceived();
            giveSaveDistanceAndMessageSatelliteSuccess("Actualizó la distancia y el mensaje del satélite kenobi");
            whenSaveDistanceAndMessageSatellite();
            thenResultIsCorrect("Actualizó la distancia y el mensaje del satélite kenobi");

        }

        @Test
        void saveDistanceAndMessageNotFoundSatellite(){

            givenSatelliteWithDistanceAndMessage();
            giveGetUbicationShapeAndMessageWithError();
            thenShowExceptionSatelliteNotFound();

        }

        private void giveGetUbicationShapeAndMessageWithError(){
            given(messageReceivedService.create(any(Satellite.class), any(DistanceMessage.class)))
                    .willThrow(MessageReceivedService.MessageReceivedServiceException.class);
        }

        private void givenSatelliteWithDistanceAndMessage(){
            name = "kenobi";
            distanceMessage = DistanceMessageMother.distanceMessageOne();
        }

        private void givenSatelliteWithMessageReceived(){
            name = "kenobi";
            distanceMessage = DistanceMessageMother.distanceMessageTwo();
        }

        private void giveSaveDistanceAndMessageSatelliteSuccess(String message){
            given(satelliteService.findByName(name)).willReturn(SatelliteMother.satelliteKenobi());
            given(messageReceivedService.create(any(Satellite.class), any(DistanceMessage.class)))
                    .willReturn(message);
        }

        private void whenSaveDistanceAndMessageSatellite() {
            result = fireQuasarService.saveDistanceAndMessageSatellite(name, distanceMessage);
        }

        private void thenResultIsCorrect(String message) {
            Assertions.assertNotNull(message);
            Assertions.assertEquals(message, result);
        }

        private void thenShowExceptionSatelliteNotFound(){
            Assertions.assertThrows(FireQuasarService.FireQuasarServiceException.class, this::whenSaveDistanceAndMessageSatellite);
        }
    }

    @Nested
    @DisplayName("Pruebas unitarias para obtener posicion y mensaje de los satelites que guardaron su registro")
    class GetPositionAndMessage {

        @Test
        void getPositionAndMessageWithThreeMessageReceived(){

            givenMessageReceveid();
            giveGetUbicationShapeAndMessageSuccess();
            whenGetPositionAndMessage();
            thenValuesAreCorrect("este es un mensaje secreto", -487.28591249999994, 1557.0142249999997);

        }

        @Test
        void getPositionAndMessageWhenMessageReceivedIsNull(){

            givenMessageReceveidIsNull();
            thenShowNotInformationEnoughException();

        }

        @Test
        void getPositionAndMessageWhenMessageReceivedIsEmpty(){

            givenMessageReceveidIsEmpty();
            thenShowNotInformationEnoughException();

        }

        @Test
        void getPositionAndMessageWhenMessageReceivedInsufficientSize(){

            givenMessageReceveidHaveAnElement();
            thenShowNotInformationEnoughException();

        }

        @Test
        void getPositionAndMessageWhenFailed(){

            givenMessageReceveid();
            giveGetUbicationShapeAndMessageWithError();
            thenShowFireQuasarServiceException();

        }

        private void thenShowNotInformationEnoughException(){
            Assertions.assertThrows(FireQuasarService.NotInformationEnoughException.class, this::whenGetPositionAndMessage);
        }

        private void thenShowFireQuasarServiceException(){
            Assertions.assertThrows(FireQuasarService.FireQuasarServiceException.class, this::whenGetPositionAndMessage);
        }

        private void whenGetPositionAndMessage() {
            satelliteResponse = fireQuasarService.getPositionAndMessage();
        }

        private void givenMessageReceveid(){
            given(messageReceivedService.findAll()).willReturn(MessageReceivedMother.all());
        }

        private void givenMessageReceveidIsNull(){
            given(messageReceivedService.findAll()).willReturn(null);
        }

        private void givenMessageReceveidIsEmpty(){
            given(messageReceivedService.findAll()).willReturn(new ArrayList<>());
        }

        private void givenMessageReceveidHaveAnElement(){
            given(messageReceivedService.findAll()).willReturn(Arrays.asList(MessageReceivedMother.messageReceivedKenobi()));
        }
    }

    private void thenValuesAreCorrect(String message, double x, double y) {
        Assertions.assertNotNull(satelliteResponse);
        Assertions.assertEquals(message, satelliteResponse.getMessage());
        Assertions.assertEquals(x, satelliteResponse.getPosition().getX());
        Assertions.assertEquals(y, satelliteResponse.getPosition().getY());
    }

    private void giveGetUbicationShapeAndMessageSuccess(){
        given(satelliteOperatorService.getLocation(any()))
                .willReturn(PositionMother.positionOne());
        given(satelliteOperatorService.getMessage(anyList()))
                .willReturn("este es un mensaje secreto");
    }

    private void giveGetUbicationShapeAndMessageWithError(){
        given(satelliteOperatorService.getLocation(any()))
                .willThrow(SatelliteOperatorService.SatelliteOperatorServiceException.class);
    }

}
