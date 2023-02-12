package ExceptionFunctionCall;

import Exceptions.ClientNetworkException;
import messagesBase.messagesFromServer.GameState;

public class ClientNetworkExceptionFunctionCall {

	public static void CheckGameStateIfNull(GameState gameState) throws ClientNetworkException {
		if (gameState == null)
			throw new ClientNetworkException("GameState is null");
	}

	public static void checkIfTwoPlayerAreAlreadyRegistered(int players) throws ClientNetworkException {
		if (players > 1) {
			throw new ClientNetworkException("Already two Player registered");
		}

	}
}
