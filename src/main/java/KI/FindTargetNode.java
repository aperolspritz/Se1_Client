package KI;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import messagesBase.messagesFromClient.ETerrain;
import messagesBase.messagesFromServer.FullMapNode;

public class FindTargetNode {

	public static List<FullMapNode> mountainFieldsOnMap = new ArrayList<FullMapNode>();
	public static FullMapNode targetNode = new FullMapNode();
	private final static Logger logger = LoggerFactory.getLogger(FindTargetNode.class);

	public List<FullMapNode> addMountainFields(List<FullMapNode> fullMap) {
		logger.debug("adding unvisited mountains");
		mountainFieldsOnMap.clear();
		for (FullMapNode node : fullMap) {
			if (!SmallCalculationClass.isInVisited(node) && node.getTerrain() == ETerrain.Mountain && node.getX() != 0
					&& node.getY() != 0)
				mountainFieldsOnMap.add(node);
		}
		return mountainFieldsOnMap;
	}

	public FullMapNode nextTarget(FullMapNode currPos) {

		boolean help = false;

		// ollections.shuffle(mountainFieldsOnMap);

		for (FullMapNode node : mountainFieldsOnMap) {
			if (!help) {
				help = true;
				targetNode = node;
			}

			else {

				int diffChosenx = targetNode.getX() - currPos.getX();
				int diffChoseny = targetNode.getY() - currPos.getY();
				int diffCurrNodex = node.getX() - currPos.getX();
				int diffCurrNodey = node.getY() - currPos.getY();
				if (diffChosenx < 0)
					diffChosenx *= -1;
				if (diffChoseny < 0)
					diffChoseny *= -1;
				if (diffCurrNodex < 0)
					diffCurrNodex *= -1;
				if (diffCurrNodey < 0)
					diffCurrNodey *= -1;

				if (VisitedFields.hasTreasure || VisitedFields.enemyFortIsKnown) {
					if ((diffChosenx + diffChoseny) < (diffCurrNodex + diffCurrNodey)) {
						targetNode = node;
					}
				} else {
					// > shortest, <farest
					if ((diffChosenx + diffChoseny) > (diffCurrNodex + diffCurrNodey)) {
						targetNode = node;
					}

				}
			}
		}

		return targetNode;
	}
}
