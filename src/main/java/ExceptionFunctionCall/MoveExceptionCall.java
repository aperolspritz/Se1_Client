package ExceptionFunctionCall;

import Exceptions.MoveExeption;
import KI.SmallCalculationClass;
import messagesBase.messagesFromClient.ETerrain;
import messagesBase.messagesFromServer.FullMapNode;

public class MoveExceptionCall {
	public static void checkNextField(FullMapNode node) {
		if (node.getTerrain() == ETerrain.Water) {
			throw new MoveExeption("next calculated Field would have been Water", node);
		}
	}

	public static void checkNextFieldVisited(FullMapNode node) {
		if (SmallCalculationClass.isInVisited(node)) {
			throw new MoveExeption("Calculated Field is in Visited", node);
		}
	}
}
