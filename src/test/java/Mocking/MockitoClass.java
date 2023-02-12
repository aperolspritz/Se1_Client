package Mocking;

import org.junit.Test;
import org.mockito.Mockito;

import ClientCoordination.ClientCoordinator;
import ClientNetwork.ClientNetwork;
import GameData.ClientData;
import KI.MyBot;
import messagesBase.UniquePlayerIdentifier;

public class MockitoClass {

	@Test

	public void GivenFakeClientNetwork_MockingPlayerRegistration_ExpectCallOnce() {
		MyBot bot = Mockito.mock(MyBot.class);
		ClientNetwork fakeNetwork = Mockito.mock(ClientNetwork.class);
		ClientData fakeData = Mockito.mock(ClientData.class);

		ClientCoordinator coordinator = new ClientCoordinator(fakeData, fakeNetwork);

		UniquePlayerIdentifier playerID = new UniquePlayerIdentifier();
		Mockito.when(fakeNetwork.registerClient()).thenReturn(playerID);
		coordinator.gameStart(false);

		Mockito.verify(fakeNetwork, Mockito.atLeast(1)).registerClient();

	}
}
