package client.main;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import ClientCoordination.ClientCoordinator;
import ClientNetwork.ClientNetwork;
import DataOutput.DataOutput;
import GameData.ClientData;

public class MainClient {

	public static void main(String[] args) {

		// parse the parameters, otherwise the automatic evaluation will not work on
		// http://swe1.wst.univie.ac.at
		// args[0] = "TR";
		// String serverBaseUrl = args[1];
		// String gameId = args[2];

		// String serverBaseUrl = args[1];
		// String gameId = args[2];
		String serverBaseUrl = "http://swe1.wst.univie.ac.at/";
		String gameId = "Ndtrf";

		WebClient baseWebClient = WebClient.builder().baseUrl(serverBaseUrl + "/games")
				.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_XML_VALUE) // the network protocol uses
																							// XML
				.defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_XML_VALUE).build();

		ClientData data = new ClientData();
		ClientNetwork cN = new ClientNetwork(gameId, baseWebClient);
		ClientCoordinator coordinator = new ClientCoordinator(data, cN);
		DataOutput view = new DataOutput(data, coordinator);

		coordinator.gameStart(true);
		System.exit(0);
	}

}
