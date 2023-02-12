package ExceptionFunctionCall;

import java.util.List;

import Exceptions.MapCheckedException;
import Exceptions.MapException;
import messagesBase.messagesFromClient.ETerrain;
import messagesBase.messagesFromClient.PlayerHalfMapNode;

public class MapExceptionFunctionCall {

	public void checkMapForSize(List<PlayerHalfMapNode> map) {
		if (map.size() != 50)
			throw new MapException("Incorrect Map size");
	}

	public void checkFortPlacement(PlayerHalfMapNode spawnPoint) {
		if (spawnPoint.getTerrain() != ETerrain.Grass) {
			throw new MapException("Incorrect Fort Placement");
		}

	}

	public static void checkWaterFieldCountOnMap(List<PlayerHalfMapNode> map) {

		int waterFieldCount = 0;

		for (PlayerHalfMapNode node : map) {
			if (node.getTerrain() == ETerrain.Water)
				waterFieldCount++;
		}
		if (waterFieldCount < 7)
			throw new MapException("Incorrect Water Fields on Map Count was" + waterFieldCount);
	}

	public static void checkMountainFieldCountOnMap(List<PlayerHalfMapNode> map) {
		int mountainFieldCount = 0;

		for (PlayerHalfMapNode node : map) {
			if (node.getTerrain() == ETerrain.Water)
				mountainFieldCount++;
		}
		if (mountainFieldCount < 5)
			throw new MapException("Incorrect Mountain Fields on Map Count was" + mountainFieldCount);
	}

	public void checkWaterFieldOnBoarderXAxis(List<PlayerHalfMapNode> map) throws MapCheckedException {
		int waterFieldCount = 0;

		for (PlayerHalfMapNode node : map) {
			if (node.getY() == 0)
				if (node.getTerrain() == ETerrain.Water)
					waterFieldCount++;
		}
		if (waterFieldCount > 4)
			throw new MapException("Incorrect Water Fields on Map boarder XAxis Count was" + waterFieldCount);
	}

	public static void FortPlaceException(PlayerHalfMapNode node) {

		if (node.getTerrain() == ETerrain.Mountain || node.getTerrain() == ETerrain.Water) {
			throw new MapException("Overwritten Field in placeFort", node);
		}
	}

	public static void mapTransFormationException(int length) throws MapCheckedException {
		if (length != 100) {
			throw new MapCheckedException("Error in transforming Map", length);
		}
	}

	public static void mountainPlaceException(PlayerHalfMapNode node) {

		if (node.getTerrain() == ETerrain.Mountain || node.getTerrain() == ETerrain.Water) {
			throw new MapException("WARNING ", node);
		}
	}

}
