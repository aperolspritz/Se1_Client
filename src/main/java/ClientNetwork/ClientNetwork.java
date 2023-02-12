package ClientNetwork;

import java.util.List;

import org.springframework.http.HttpMethod;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import Converter.ClientConverter;
import messagesBase.ResponseEnvelope;
import messagesBase.UniquePlayerIdentifier;
import messagesBase.messagesFromClient.EMove;
import messagesBase.messagesFromClient.ERequestState;
import messagesBase.messagesFromClient.PlayerHalfMapNode;
import messagesBase.messagesFromClient.PlayerMove;
import messagesBase.messagesFromClient.PlayerRegistration;
import messagesBase.messagesFromServer.GameState;
import reactor.core.publisher.Mono;

public class ClientNetwork {

	private String gameID;
	private WebClient baseWebClient;
	private ClientConverter converter = new ClientConverter();

	public ClientNetwork(String gameID, WebClient baseWebClient) {
		this.baseWebClient = baseWebClient;
		this.gameID = gameID;
	}

	public GameState sendStateReq(UniquePlayerIdentifier uniqueID) {
		GameState gameState = this.converter.packStateReq(gameID, baseWebClient, uniqueID).getData().get();
		return gameState;
	}

	public UniquePlayerIdentifier registerClient() {
		PlayerRegistration playerReg = new PlayerRegistration("Johannes", "Raab", "raabj97");

		return converter.packData(playerReg, baseWebClient, this.gameID);

	}

	@SuppressWarnings("rawtypes")
	public ResponseEnvelope<ERequestState> sendHalfMap(List<PlayerHalfMapNode> halfMap, String playerID) {
		Mono<ResponseEnvelope> webAccess = baseWebClient.method(HttpMethod.POST).uri("/" + this.gameID + "/halfmaps")
				.body(BodyInserters.fromValue(converter.packDataMap(halfMap, playerID))).retrieve()
				.bodyToMono(ResponseEnvelope.class);

		@SuppressWarnings("unchecked")
		ResponseEnvelope<ERequestState> resultMap = webAccess.block();
		return resultMap;
	}

	@SuppressWarnings("unchecked")
	public ResponseEnvelope<ERequestState> sendMoveReq(String playerID, EMove move) {

		@SuppressWarnings("rawtypes")
		Mono<ResponseEnvelope> webAccess = baseWebClient.method(HttpMethod.POST).uri("/" + this.gameID + "/moves")
				.body(BodyInserters.fromValue(PlayerMove.of(playerID, move))).retrieve()
				.bodyToMono(ResponseEnvelope.class);
		return webAccess.block();

	}

}
