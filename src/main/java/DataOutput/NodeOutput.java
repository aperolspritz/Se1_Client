package DataOutput;

import messagesBase.messagesFromServer.FullMapNode;

public class NodeOutput {

	public static String nodeToString(FullMapNode node) {

		String ret = "";
		if (node.getTerrain() != null) {
			return ("[" + Integer.toString(node.getX()) + "," + Integer.toString(node.getY()) + " "
					+ node.getTerrain().toString()) + "]";
		}
		return ret;
	}

}
