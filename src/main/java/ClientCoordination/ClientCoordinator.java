package ClientCoordination;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ClientNetwork.ClientNetwork;
import ExceptionFunctionCall.ClientNetworkExceptionFunctionCall;
import Exceptions.ClientNetworkException;
import GameData.ClientData;
import KI.MyBot;
import KI.VisitedFields;
import MapOperations.BuildMap;
import messagesBase.ResponseEnvelope;
import messagesBase.UniquePlayerIdentifier;
import messagesBase.messagesFromClient.EMove;
import messagesBase.messagesFromClient.ERequestState;
import messagesBase.messagesFromClient.PlayerHalfMapNode;
import messagesBase.messagesFromServer.EPlayerGameState;
import messagesBase.messagesFromServer.PlayerState;

public class ClientCoordinator {

	private ClientNetwork clientNetwork;
	private ClientData clientData = new ClientData();
	private BuildMap map = new BuildMap();
	private MyBot myBot = new MyBot();
	private final static Logger logger = LoggerFactory.getLogger(ClientCoordinator.class);

	public ClientCoordinator(ClientData data, ClientNetwork cN) {
		this.clientData = data;

		this.clientNetwork = cN;

	}

	private void registerClient(boolean c) {

		try {
			ClientNetworkExceptionFunctionCall
					.checkIfTwoPlayerAreAlreadyRegistered(myBot.getGameState().getPlayers().size());
		} catch (ClientNetworkException e) {
			logger.error("Error in ClientNetwork" + e.getMessage());
		}

		UniquePlayerIdentifier help = this.clientNetwork.registerClient();
		this.clientData.setPlayerID(help.getUniquePlayerID());
		this.clientData.setPlayerUniqueID(help);
		logger.info("Client Registered " + clientData.getPlayerID());
		// reqStateOperation();
		if (c) {
			reqStateOperation();
			mapOperation();
		}
	}

	public void gameStart(boolean c) {
		logger.info("Game Started");
		registerClient(c);
	}

	private void reqTimer() {
		logger.trace("starting Timer");
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			public void run() {
				timer.cancel();
			}
		}, 500, 400);
	}

	private void getEPlayerGameState() {

		for (PlayerState st : myBot.getGameState().getPlayers()) {
			if (st.getUniquePlayerID().equals(clientData.getPlayerID())) {
				logger.debug("Player state:" + st.getState());
				this.clientData.setEPlayGameState(st.getState());
				if (st.hasCollectedTreasure()) {
					VisitedFields.hasTreasure = true;

				}
			}
		}

	}

	public void reqStateOperation() {

		myBot.setGameState(this.clientNetwork.sendStateReq(this.clientData.getUniqueID()));
		getEPlayerGameState();
		logger.debug("First State Request was sent");

		while (this.clientData.getEPlayerGameState() == EPlayerGameState.MustWait) {
			logger.debug("Client Must Wait ...");
			reqTimer();
			myBot.setGameState(this.clientNetwork.sendStateReq(this.clientData.getUniqueID()));
			getEPlayerGameState();
		}

	}

	private void mapOperation() {

		logger.debug("Map generating");
		List<PlayerHalfMapNode> halfMap = map.generateMap();
		this.clientData.setHalfMap(halfMap);

		ResponseEnvelope<ERequestState> resultMap = this.clientNetwork.sendHalfMap(halfMap,
				this.clientData.getPlayerID());
		if (resultMap.getState() == ERequestState.Error) {
			logger.error("Sending Map " + resultMap.getExceptionMessage());
		} else {
			logger.debug("Sending Map:[" + resultMap.getState().toString() + "]");

			reqStateOperation();
			moveOperation();
		}
		;
	}

	public EMove botCallFunction() {
		this.clientData.setCurrPos(myBot.getcurrPos());
		this.clientData.setFullMap(myBot.getFullMap());
		EMove ret = myBot.botMoveOperation();
		return ret;
	}

	public int moveOperation() {

		logger.debug("Move Operations");

		while (clientData.getEPlayerGameState() != EPlayerGameState.Won
				&& clientData.getEPlayerGameState() != EPlayerGameState.Lost) {

			// reqStateOperation();

			EMove move = botCallFunction();

			if (clientData.getEPlayerGameState() == EPlayerGameState.MustAct) {
				ResponseEnvelope<ERequestState> resultMap = this.clientNetwork
						.sendMoveReq(this.clientData.getPlayerID(), move);

				if (resultMap.getState() == ERequestState.Error) {

					logger.error("Client error, errormessage:[" + resultMap.getExceptionMessage() + "]");

				} else {
					logger.debug("Move Action[" + move.toString() + "] was sent");
				}
			}
			reqStateOperation();

		}
		this.clientData.setCurrPos(myBot.getcurrPos());
		this.clientData.setFullMap(myBot.getFullMap());
		reqStateOperation();
		logger.info("Game Ended");
		return 1;

	}
}
