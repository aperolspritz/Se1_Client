package MapOperations;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ExceptionFunctionCall.MapExceptionFunctionCall;
import Exceptions.MapException;
import messagesBase.messagesFromClient.ETerrain;
import messagesBase.messagesFromClient.PlayerHalfMapNode;

public class BuildMap {
	// TAKEN FROM <0>
	// Random Function from
	// Generating Random Numbers in a Range,
	// https://www.baeldung.com/java-generating-random-numbers-in-range,
	// Accessed 10.11.2022

	private final static Logger logger = LoggerFactory.getLogger(BuildMap.class);

	public int checkMapSize(List<PlayerHalfMapNode> halfMap) {
		return halfMap.size();
	}

	// TAKEN FROM START<0>

	public static int rndmNumber(int min, int max) {
		return (int) ((Math.random()) * (max - min) + min);
	}
	// TAKEN FROM END <0>

	public List<PlayerHalfMapNode> generateMap() {
		List<PlayerHalfMapNode> halfMap = new ArrayList<PlayerHalfMapNode>();
		for (int y = 0; y < 5; ++y) {
			for (int x = 0; x < 10; ++x) {
				PlayerHalfMapNode node = new PlayerHalfMapNode(x, y, false, ETerrain.Grass);
				halfMap.add(node);
			}
		}

		try {

			fillWater(halfMap);
			MapExceptionFunctionCall.checkWaterFieldCountOnMap(halfMap);
		} catch (MapException e) {
			logger.warn("Violation against Water Field amount on Map", e.getMessage(), e.getCount());
			halfMap.clear();
			generateMap();
		}

		try {
			fillMountain(halfMap);
			MapExceptionFunctionCall.checkMountainFieldCountOnMap(halfMap);
		} catch (MapException e) {
			logger.error("Violation against Mountain Fields amount on Map", e.getMessage(), e.getCount());
			halfMap.clear();
			generateMap();
		}

		placeFort(halfMap);

		return halfMap;
	}

	public PlayerHalfMapNode placeFort(List<PlayerHalfMapNode> halfMap) {

		PlayerHalfMapNode node = new PlayerHalfMapNode();
		int saveI = 0;
		for (int i = 12; i < 19; ++i) {
			if (halfMap.get(i).getTerrain() == ETerrain.Grass) {
				node = halfMap.get(i);
				saveI = i;
				break;
			}
		}

		PlayerHalfMapNode nodeHelp = new PlayerHalfMapNode(node.getX(), node.getY(), true, ETerrain.Grass);
		try {

			MapExceptionFunctionCall.FortPlaceException(nodeHelp);
		} catch (MapException e) {
			logger.debug("Water/Mountain Field was overwritten in placing Fort: " + e.getExceptionHalfMapNode());
		}

		halfMap.set(saveI, nodeHelp);
		return nodeHelp;

	}

	public void fillWater(List<PlayerHalfMapNode> halfMap) {
		for (int y = 0; y <= 40; y += 40) {
			for (int x = 0; x <= 9; x += 9) {
				PlayerHalfMapNode node = halfMap.get(y + x);
				PlayerHalfMapNode help = new PlayerHalfMapNode(node.getX(), node.getY(), ETerrain.Water);
				halfMap.set(x + y, help);
			}
		}

		for (int y = 10; y <= 40; y += 10) {
			int rndm = rndmNumber(2, 7);
			PlayerHalfMapNode node = halfMap.get(rndm + y);

			while (node.getTerrain() == ETerrain.Water) {
				rndm = rndmNumber(2, 7);
				node = halfMap.get(rndm + y);
			}
			PlayerHalfMapNode help = new PlayerHalfMapNode(node.getX(), node.getY(), ETerrain.Water);
			halfMap.set(rndm + y, help);
		}

	}

	public void fillMountain(List<PlayerHalfMapNode> halfMap) {
		int count = rndmNumber(3, 6);
		for (int i = 0; i < count; ++i) {
			int pos = rndmNumber(0, 49);
			PlayerHalfMapNode node = halfMap.get(pos);

			while (node.getTerrain() == ETerrain.Water) {
				pos = rndmNumber(0, 49);
				node = halfMap.get(pos);
			}
			try {
				MapExceptionFunctionCall.mountainPlaceException(node);
			} catch (MapException e) {
				logger.debug("Warning Mountain Field was placed on Mountain Field: " + e.getMessage());
			}

			PlayerHalfMapNode newNode = new PlayerHalfMapNode(node.getX(), node.getY(), ETerrain.Mountain);
			halfMap.set(pos, newNode);
		}

		int i = 0;
		for (PlayerHalfMapNode node : halfMap) {
			if (node.getTerrain() != ETerrain.Water) {
				if (node.getY() == 1 || node.getY() == 3) {
					if (node.getX() == 2 || node.getX() == 5 || node.getX() == 8) {
						PlayerHalfMapNode newNode = new PlayerHalfMapNode(node.getX(), node.getY(), ETerrain.Mountain);
						halfMap.set(i, newNode);
					}
				}
			}
			i++;
		}

	}

}
