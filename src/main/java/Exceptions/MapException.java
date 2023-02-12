package Exceptions;

import messagesBase.messagesFromClient.PlayerHalfMapNode;
import messagesBase.messagesFromServer.FullMapNode;

public class MapException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private FullMapNode checkNode = new FullMapNode();
	private PlayerHalfMapNode checkedHalfMapNode = new PlayerHalfMapNode();
	int fieldCount = 0;

	public MapException(String message, FullMapNode checkNode) {
		super(message);
		this.checkNode = checkNode;
	}

	public MapException(String message, PlayerHalfMapNode halfMapNode) {
		super(message);
		checkedHalfMapNode = halfMapNode;

	}

	public MapException(String message, int fieldCount) {
		super(message);
		this.fieldCount = fieldCount;

	}

	public int getCount() {
		return this.getCount();
	}

	public MapException(String message) {
		super(message);
	}

	public String getExceptionHalfMapNode() {
		return Integer.toString(checkedHalfMapNode.getX()) + "," + Integer.toString(checkedHalfMapNode.getY()) + ","
				+ checkedHalfMapNode.getTerrain().toString();
	}

	public String getExceptionNode() {
		return Integer.toString(checkNode.getX()) + "," + Integer.toString(checkNode.getY());

	}
}