package Converter;

import java.util.List;

import org.springframework.http.HttpMethod;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import messagesBase.ResponseEnvelope;
import messagesBase.UniquePlayerIdentifier;
import messagesBase.messagesFromClient.PlayerHalfMap;
import messagesBase.messagesFromClient.PlayerHalfMapNode;
import messagesBase.messagesFromClient.PlayerRegistration;
import messagesBase.messagesFromServer.GameState;
import reactor.core.publisher.Mono;

public class ClientConverter {

	public UniquePlayerIdentifier packData(PlayerRegistration playerReg, WebClient baseWebClient, String gameID) {

		Mono<ResponseEnvelope> webAccess = baseWebClient.method(HttpMethod.POST).uri("/" + gameID + "/players")
				.body(BodyInserters.fromValue(playerReg)).retrieve().bodyToMono(ResponseEnvelope.class);

		return unpackData(webAccess);
	}

	public UniquePlayerIdentifier unpackData(Mono<ResponseEnvelope> webAccess) {

		ResponseEnvelope<UniquePlayerIdentifier> resultReg = webAccess.block();

		return resultReg.getData().get();
	}

	public ResponseEnvelope<GameState> packStateReq(String gameID, WebClient baseWebClient,
			UniquePlayerIdentifier uniqueID) {

		Mono<ResponseEnvelope> webAccess = baseWebClient.method(HttpMethod.GET)
				.uri("/" + gameID + "/states/" + uniqueID.getUniquePlayerID()).retrieve()
				.bodyToMono(ResponseEnvelope.class);
		ResponseEnvelope<GameState> requestResult = webAccess.block();
		return requestResult;
	}

	public PlayerHalfMap packDataMap(List<PlayerHalfMapNode> halfMap, String playerID) {
		return new PlayerHalfMap(playerID, halfMap);
	}

}
