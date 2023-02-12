package KI;

import java.util.ArrayList;
import java.util.List;

import messagesBase.messagesFromClient.PlayerHalfMapNode;
import messagesBase.messagesFromServer.FullMapNode;

public class SmallCalculationClass {

	public static Boolean nodesAreEqual(FullMapNode comp1, FullMapNode comp2) {

		// return comp1.equals(comp2);
		return (comp1.getX() == comp2.getX() && comp1.getY() == comp2.getY()
				&& comp1.getTerrain() == comp2.getTerrain());

	}

	public static boolean isInVisited(FullMapNode node) {

		List<FullMapNode> vis = VisitedFields.visitedFields;
		boolean ret = false;

		for (FullMapNode n : vis) {
			if (nodesAreEqual(n, node))
				ret = true;
		}
		return ret;
		// return VisitedFields.visitedFields.contains(node);
	}

	public static List<FullMapNode> transformMap(List<FullMapNode> sortedMap/* GameState gameState */) {

		List<FullMapNode> sortedMapRet = new ArrayList<FullMapNode>();
		////////////////////////

		if (VisitedFields.checkMapForShape(sortedMap)) {
			for (int y = 0; y < 5; ++y) {
				for (int x = 0; x < 20; ++x) {
					for (FullMapNode node : sortedMap) {
						if (node.getX() == x && node.getY() == y) {
							sortedMapRet.add(node);
						}
					}

				}

			}

		} else {
			for (int y = 0; y < 10; ++y) {
				for (int x = 0; x < 10; ++x) {
					for (FullMapNode node : sortedMap) {
						if (node.getX() == x && node.getY() == y) {
							sortedMapRet.add(node);
						}
					}

				}

			}
		}
		return sortedMapRet;

	}

	public static List<FullMapNode> calculateAdjacentNodes(FullMapNode currPos, List<FullMapNode> map) {
		List<FullMapNode> tempList = new ArrayList<FullMapNode>();
		int x = currPos.getX();
		int xLeft = x - 1;
		int xRight = x + 1;
		int y = currPos.getY();
		int yUp = y - 1;
		int yDown = y + 1;

		for (FullMapNode node : map) {

			if (node.getX() == xLeft && node.getY() == y) {
				tempList.add(node);
			} else if (node.getX() == xRight && node.getY() == y) {
				tempList.add(node);
			} else if (node.getY() == yDown && node.getX() == x) {
				tempList.add(node);
			} else if (node.getY() == yUp && node.getX() == x) {
				tempList.add(node);
			}
		}

		return tempList;

	}

	public static List<PlayerHalfMapNode> calculateAdjacentNodes(PlayerHalfMapNode currPos,
			List<PlayerHalfMapNode> map) {
		List<PlayerHalfMapNode> tempList = new ArrayList<PlayerHalfMapNode>();
		int x = currPos.getX();
		int xLeft = x - 1;
		int xRight = x + 1;
		int y = currPos.getY();
		int yUp = y - 1;
		int yDown = y + 1;

		for (PlayerHalfMapNode node : map) {
			if (node.getX() == xLeft && node.getY() == y) {
				tempList.add(node);
			}
			if (node.getX() == xRight && node.getY() == y) {
				tempList.add(node);
			}
			if (node.getY() == yDown && node.getX() == x) {
				tempList.add(node);
			}
			if (node.getY() == yUp && node.getX() == x) {
				tempList.add(node);
			}
		}
		return tempList;

	}

	public static List<FullMapNode> calculateAdjacentsNodesIfOnMountain(List<FullMapNode> fullMap,
			FullMapNode currPos) {

		List<FullMapNode> adj = new ArrayList<FullMapNode>();
		int xUp = currPos.getX();
		int yUp = currPos.getY() - 1;

		int xDown = currPos.getX();
		int yDown = currPos.getY() + 1;

		int xUpLeft = currPos.getX() - 1;
		int yUpLeft = currPos.getY() - 1;

		int xUpRight = currPos.getX() + 1;
		int yUpRight = currPos.getY() - 1;

		int xLeft = currPos.getX() - 1;
		int yLeft = currPos.getY();

		int xRight = currPos.getX() + 1;
		int yRight = currPos.getY();

		int xDownLeft = currPos.getX() - 1;
		int yDownLeft = currPos.getY() + 1;

		int xDownRight = currPos.getX() + 1;
		int yDownRight = currPos.getY() + 1;

		for (FullMapNode node : fullMap) {

			if (node.getX() == xUp && node.getY() == yUp) {
				adj.add(node);
			}

			if (node.getX() == xDown && node.getY() == yDown) {
				adj.add(node);
			}

			if (node.getX() == xUpLeft && node.getY() == yUpLeft) {
				adj.add(node);
			}
			if (node.getX() == xUpRight && node.getY() == yUpRight) {
				adj.add(node);
			}

			if (node.getX() == xRight && node.getY() == yRight) {
				adj.add(node);
			}

			if (node.getX() == xDownLeft && node.getY() == yDownLeft) {
				adj.add(node);
			}
			if (node.getX() == xDownRight && node.getY() == yDownRight) {
				adj.add(node);
			}
			if (node.getX() == xLeft && node.getY() == yLeft) {
				adj.add(node);
			}
		}
		return adj;
	}

}
