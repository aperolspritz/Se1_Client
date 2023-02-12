package ExceptionFunctionCall;

import java.util.List;

import Exceptions.MoveExeption;
import Exceptions.TargetNodeException;
import messagesBase.messagesFromClient.ETerrain;
import messagesBase.messagesFromServer.FullMapNode;

public class TargetNodeExceptionFunctionCall {

	public void checkMountainFieldList(List<FullMapNode> mountainList) {
		for (FullMapNode node : mountainList) {
			if (node.getTerrain() != ETerrain.Mountain) {
				throw new TargetNodeException("List of Mountain Fields was was not full of Mountain Fields");
			}
		}

	}

	public static void targetNodeException(FullMapNode targetNode) {

		if (targetNode.getTerrain() != ETerrain.Mountain) {
			throw new MoveExeption("TargetNode is not Mountain", targetNode);
		}
	}

}
