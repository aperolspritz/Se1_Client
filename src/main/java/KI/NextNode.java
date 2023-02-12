package KI;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import DataOutput.NodeOutput;
import messagesBase.messagesFromClient.EMove;
import messagesBase.messagesFromClient.ETerrain;
import messagesBase.messagesFromServer.FullMapNode;

public class NextNode {
	private final static Logger logger = LoggerFactory.getLogger(MyBot.class);

	public List<FullMapNode> filterVisited(List<FullMapNode> adjacents) {

		if ((VisitedFields.hasTreasure && VisitedFields.enemyFortIsKnown)) {
			return adjacents;
		}

		List<FullMapNode> help = new ArrayList<FullMapNode>();
		List<FullMapNode> mountain = new ArrayList<FullMapNode>();

		for (FullMapNode node : adjacents) {
			if (SmallCalculationClass.isInVisited(node) == false) {
				help.add(node);
			}
		}

		for (FullMapNode node : help) {
			if (node.getTerrain() == ETerrain.Grass && SmallCalculationClass.isInVisited(node) == false) {
				mountain.add(node);
			}
		}

		if (mountain.size() > 0) {

			return mountain;
		}

		else if (!help.isEmpty()) {

			return help;

		} else {

			Collections.shuffle(adjacents);
			logger.debug("Adjacent Size: " + adjacents.size());
			return adjacents;
		}

	}

	public FullMapNode calcShortestDistanceToTargetFromAdj(List<FullMapNode> adjacents) {

		FullMapNode help = new FullMapNode();
		int helpx = 0;
		int helpy = 0;
		int distanceHelpToTarget = 0;
		boolean first = true;
		FullMapNode target = FindTargetNode.targetNode;

		for (FullMapNode node : adjacents) {
			if (first) {
				first = false;
				help = node;
				helpx = node.getX() - target.getX();
				helpy = node.getY() - target.getY();
				if (helpx < 0)
					helpx *= -1;
				if (helpy < 0)
					helpy *= -1;
				distanceHelpToTarget = helpx + helpy;

			} else {

				int nodex = node.getX() - target.getX();
				int nodey = node.getY() - target.getY();
				if (nodex < 0)
					nodex *= -1;
				if (nodey < 0)
					nodey *= -1;

				int newDistanceToTarget = nodex + nodey;

				if (newDistanceToTarget < distanceHelpToTarget)
					help = node;

			}

		}

		logger.debug("Chosen Node from Adjacents: " + NodeOutput.nodeToString(help));
		return help;

	}

	public EMove evaluateMoveFromNode(FullMapNode currPos, FullMapNode nextNode) {
		EMove ret = EMove.Right;

		if (nextNode.getX() > currPos.getX()) {
			ret = EMove.Right;
			return ret;
		}
		if (nextNode.getX() < currPos.getX()) {
			ret = EMove.Left;
			return ret;
		}
		if (nextNode.getY() > currPos.getY()) {
			ret = EMove.Down;
			return ret;
		}
		if (nextNode.getY() < currPos.getY()) {
			ret = EMove.Up;
			return ret;
		}
		return ret;
	}

	public EMove calcNextNode(FullMapNode currPos, List<FullMapNode> map) {

		List<FullMapNode> adjacents = SmallCalculationClass.calculateAdjacentNodes(currPos, map);
		List<FullMapNode> adjacentswithoutWater = new ArrayList<FullMapNode>();
		List<FullMapNode> filteredAdj = new ArrayList<FullMapNode>();
		FullMapNode target = FindTargetNode.targetNode;

		for (FullMapNode n : adjacents) {
			if (n.getX() == target.getX() && n.getY() == target.getY() && n.getTerrain() == target.getTerrain())
				return evaluateMoveFromNode(currPos, n);
		}

		for (FullMapNode node : adjacents) {
			if (node.getTerrain() != ETerrain.Water) {
				adjacentswithoutWater.add(node);
			}

		}
		filteredAdj = filterVisited(adjacentswithoutWater);
		Collections.shuffle(filteredAdj);
		FullMapNode chosenNode = calcShortestDistanceToTargetFromAdj(filteredAdj);
		return evaluateMoveFromNode(currPos, chosenNode);

	}
}
