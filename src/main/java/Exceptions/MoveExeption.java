package Exceptions;

import messagesBase.messagesFromServer.FullMapNode;

public class MoveExeption extends RuntimeException {
	private FullMapNode checkNode;
	// private final String userID = "Target Node is not Mountain: ";

	public MoveExeption(String message, FullMapNode node) {
		super(message);
		this.checkNode = node;
	}

	public String getExceptionNode() {
		return Integer.toString(checkNode.getX()) + "," + Integer.toString(checkNode.getY()) + ","
				+ checkNode.getTerrain().toString();
	}

}
