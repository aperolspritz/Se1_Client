package KI;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import DataOutput.NodeOutput;
import ExceptionFunctionCall.ClientNetworkExceptionFunctionCall;
import ExceptionFunctionCall.MapExceptionFunctionCall;
import Exceptions.ClientNetworkException;
import Exceptions.MapCheckedException;
import Exceptions.MoveExeption;
import messagesBase.messagesFromClient.EMove;
import messagesBase.messagesFromClient.ETerrain;
import messagesBase.messagesFromServer.EPlayerPositionState;
import messagesBase.messagesFromServer.FullMapNode;
import messagesBase.messagesFromServer.GameState;

public class MyBot {

	private FullMapNode currPos = new FullMapNode();
	public static FullMapNode prevPos = new FullMapNode();
	private GameState gameState = new GameState(" ");
	private List<FullMapNode> fullMap = new ArrayList<FullMapNode>();
	private boolean firstTarget = false;
	private EMove move;
	private FindTargetNode targetNode = new FindTargetNode();
	static public VisitedFields visitedFields = new VisitedFields();
	private final static Logger logger = LoggerFactory.getLogger(MyBot.class);

	private NextNode nextNode = new NextNode();

	public void setGameState(GameState gameState) {
		this.gameState = gameState;
	}

	public List<FullMapNode> getFullMap() {
		return this.fullMap;
	}

	public FullMapNode botPos() {
		return this.currPos;
	}

	public List<FullMapNode> getMap() {
		return this.fullMap;
	}

	public GameState getGameState() {
		return this.gameState;
	}

	private boolean updateCurrPos() {
		prevPos = this.currPos;
		logger.debug("[old Position]->" + NodeOutput.nodeToString(prevPos) + ", [new Position]->"
				+ NodeOutput.nodeToString(currPos));

		for (FullMapNode node : fullMap) {
			logger.trace(NodeOutput.nodeToString(node));
			if (node.getPlayerPositionState() == EPlayerPositionState.MyPlayerPosition) {
				this.currPos = node;
				break;
			}
		}
		return SmallCalculationClass.nodesAreEqual(prevPos, currPos);

	}

	private List<FullMapNode> getNodesFromGameState() {
		List<FullMapNode> sortedMap = new ArrayList<FullMapNode>();
		try {
			ClientNetworkExceptionFunctionCall.CheckGameStateIfNull(gameState);
			for (FullMapNode node : gameState.getMap().get().getMapNodes()) {
				sortedMap.add(node);
			}
		} catch (ClientNetworkException e) {
			logger.error("GameState is null" + e.getMessage());
		}

		return sortedMap;
	}

	public void transformMap() {

		this.fullMap = SmallCalculationClass.transformMap(getNodesFromGameState());

	}

	public FullMapNode getcurrPos() {
		return this.currPos;
	}

	private boolean reachedTarget(FullMapNode targetNode) {
		return currPos.equals(targetNode);
	}

	private void targetNodeOperation() {

		if (!firstTarget) {
			targetNode.addMountainFields(fullMap);
			targetNode.nextTarget(currPos);
			firstTarget = true;
			return;
		}

		if (reachedTarget(FindTargetNode.targetNode)) {
			visitedFields.getAllAdjacentNodes(fullMap, currPos);
			targetNode.addMountainFields(fullMap);
			targetNode.nextTarget(currPos);
			logger.info("new target Node is " + NodeOutput.nodeToString(FindTargetNode.targetNode));

		}
		checkFields();
	}

	private void checkFields() {

		if (visitedFields.enemyFortIsKnown() && VisitedFields.hasTreasure) {
			FindTargetNode.targetNode = VisitedFields.enemyFortPos;
			return;
		}

		if (VisitedFields.hasTreasure && !VisitedFields.enemyFortIsKnown) {
			targetNode.addMountainFields(fullMap);
			targetNode.nextTarget(currPos);
			return;
		}
		if (visitedFields.getMyTreasureisKnowButNotPickedUpYet()) {
			FindTargetNode.targetNode = visitedFields.getMyTreasurePos();

			return;
		}

	}

	private void checkIfOnMountain() {
		if (currPos.getTerrain() == ETerrain.Mountain) {
			visitedFields.getAllAdjacentNodes(fullMap, currPos);
		}
	}

	public EMove botMoveOperation() throws MoveExeption {

		try {
			transformMap();
			MapExceptionFunctionCall.mapTransFormationException(this.fullMap.size());
		} catch (MapCheckedException e) {
			MyBot.logger.error("Error in Transforming Map" + e.getMessage() + e.getCount());
		}

		if (!updateCurrPos()) {
			logger.info("Current Position was Updated" + NodeOutput.nodeToString(currPos));
			checkIfOnMountain();
			targetNodeOperation();
			checkFields();

			logger.info("Target Node is " + NodeOutput.nodeToString(FindTargetNode.targetNode));

			if (SmallCalculationClass.isInVisited(currPos))
				logger.warn("Player has been on this Position Before" + NodeOutput.nodeToString(currPos));
			if (VisitedFields.hasTreasure) {
				logger.info("Player has Treasure");
				VisitedFields.myTreasureisKnowButNotPickedUpYet = false;
			}

			if (VisitedFields.enemyFortIsKnown)
				logger.info("Player knows Enemy Fort Position");
			if (VisitedFields.myTreasureisKnowButNotPickedUpYet)
				logger.info("Player knows Treasure Position but has not picked it up yet");

			logger.debug("Fields that have been Visited" + VisitedFields.visitedFields.size());
			visitedFields.addToVisitedFields(this.currPos);

			this.move = nextNode.calcNextNode(this.currPos, this.fullMap);
			return this.move;

		}

		return this.move;
	}
}
