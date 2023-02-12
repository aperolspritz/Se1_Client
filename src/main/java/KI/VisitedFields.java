package KI;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import DataOutput.NodeOutput;
import messagesBase.messagesFromServer.EFortState;
import messagesBase.messagesFromServer.ETreasureState;
import messagesBase.messagesFromServer.FullMapNode;

public class VisitedFields {

	public static List<FullMapNode> visitedFields = new ArrayList<FullMapNode>();
	public static FullMapNode enemyFortPos = new FullMapNode();
	public static FullMapNode myTreasurePos = new FullMapNode();
	public static boolean myTreasureisKnowButNotPickedUpYet = false;
	public static boolean enemyFortIsKnown = false;
	public static boolean hasTreasure = false;
	private final static Logger logger = LoggerFactory.getLogger(VisitedFields.class);
	public static FullMapNode enemyPos = new FullMapNode();

	static public boolean is20x5 = false;

	public static boolean checkMapForShape(List<FullMapNode> fullMap) {

		for (FullMapNode node : fullMap) {
			if (node.getX() > 9) {
				is20x5 = true;
				return true;
			}
		}
		return false;
	}

	public boolean getMyTreasureisKnowButNotPickedUpYet() {
		return myTreasureisKnowButNotPickedUpYet;
	}

	public FullMapNode getMyTreasurePos() {

		return myTreasurePos;
	}

	private void checkAdjForEfortState(List<FullMapNode> adj) {
		for (FullMapNode node : adj) {

			if (node.getFortState() == EFortState.EnemyFortPresent) {
				enemyFortIsKnown = true;
				enemyFortPos = node;
			}
		}
	}

	private void checkAdjForTreasureState(List<FullMapNode> adj) {

		for (FullMapNode node : adj) {

			if (node.getTreasureState() == ETreasureState.MyTreasureIsPresent) {
				if (!hasTreasure) {
					myTreasureisKnowButNotPickedUpYet = true;
					myTreasurePos = node;
				}
			}

		}
	}

	public void getAllAdjacentNodes(List<FullMapNode> fullMap, FullMapNode currPos) {

		List<FullMapNode> adj = SmallCalculationClass.calculateAdjacentsNodesIfOnMountain(fullMap, currPos);
		checkAdjForEfortState(adj);
		checkAdjForTreasureState(adj);
	}

	private void checkEnemyFortState(FullMapNode node) {
		if (node.getFortState() == EFortState.EnemyFortPresent) {
			enemyFortIsKnown = true;
			enemyFortPos = node;
			logger.info("EnemyFort was found on " + NodeOutput.nodeToString(node));
		}
	}

	private void checkTreasureState(FullMapNode node) {
		if (node.getTreasureState() == ETreasureState.MyTreasureIsPresent) {
			logger.info("Treasure was picked up on " + NodeOutput.nodeToString(node));
			hasTreasure = true;
			myTreasureisKnowButNotPickedUpYet = false;
		}
	}

	public boolean addToVisitedFields(FullMapNode node) {

		boolean ret = false;
		if (!visitedFields.contains(node)) {
			visitedFields.add(node);
			ret = true;
			logger.debug("new Field was added to visited Fields " + NodeOutput.nodeToString(node));
			checkEnemyFortState(node);
			checkTreasureState(node);
		}
		return ret;
	}

	public boolean getHasTreasure() {
		return hasTreasure;
	}

	public FullMapNode getEnemyFort() {
		return enemyFortPos;

	}

	public boolean enemyFortIsKnown() {
		return enemyFortIsKnown;
	}

	public void setEnemyFortPos(FullMapNode enemyFortPos) {
		VisitedFields.enemyFortPos = enemyFortPos;

	}

}
