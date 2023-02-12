package BuildMapTests;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import ExceptionFunctionCall.MapExceptionFunctionCall;
import Exceptions.MapException;
import MapOperations.BuildMap;
import messagesBase.messagesFromClient.ETerrain;
import messagesBase.messagesFromClient.PlayerHalfMapNode;

public class BuildMapNegativeTest {

	@Test
	public void GivenHalfMap_CreatingNewHalfMap_ExpectMapException() {

		BuildMap buildMap = new BuildMap();
		List<PlayerHalfMapNode> map = buildMap.generateMap();
		map.remove(0);
		MapExceptionFunctionCall mapException = new MapExceptionFunctionCall();

		org.junit.jupiter.api.function.Executable testCode = () -> {
			mapException.checkMapForSize(map);
		};
		Assertions.assertThrows(MapException.class, testCode);
	}

	@Test
	public void GivenHalfMap_PlacingFortOnWrongTerrain_ExpectMapException() {
		List<PlayerHalfMapNode> halfMap = new ArrayList<PlayerHalfMapNode>();
		for (int y = 0; y < 5; ++y) {
			for (int x = 0; x < 10; ++x) {
				PlayerHalfMapNode node = new PlayerHalfMapNode(x, y, false, ETerrain.Grass);
				halfMap.add(node);
			}
		}
		PlayerHalfMapNode fortPlace = halfMap.get(25);
		PlayerHalfMapNode nodeHelp = new PlayerHalfMapNode(fortPlace.getX(), fortPlace.getY(), true, ETerrain.Mountain);

		MapExceptionFunctionCall mapException = new MapExceptionFunctionCall();
		org.junit.jupiter.api.function.Executable testCode = () -> {
			mapException.checkFortPlacement(nodeHelp);
		};
		Assertions.assertThrows(MapException.class, testCode);
	}

	@Test
	public void GivenHalfMap_PlaceFalseAmountWaterFieldsOnMap_ExpectMapException() {

		List<PlayerHalfMapNode> halfMap = new ArrayList<PlayerHalfMapNode>();

		for (int y = 0; y < 5; ++y) {
			for (int x = 0; x < 10; ++x) {
				PlayerHalfMapNode node = new PlayerHalfMapNode(x, y, false, ETerrain.Grass);
				halfMap.add(node);
			}
		}

		for (int y = 0; y <= 40; y += 40) {
			for (int x = 0; x <= 9; x += 9) {
				PlayerHalfMapNode node = halfMap.get(y + x);
				PlayerHalfMapNode help = new PlayerHalfMapNode(node.getX(), node.getY(), ETerrain.Water);
				halfMap.set(x + y, help);

			}
		}

		for (int y = 1; y < 40; y += 20) {
			int rndm = BuildMap.rndmNumber(2, 7);
			PlayerHalfMapNode node = halfMap.get(rndm + y);

			PlayerHalfMapNode help = new PlayerHalfMapNode(node.getX(), node.getY(), ETerrain.Water);
			halfMap.set(rndm + y, help);
		}

		org.junit.jupiter.api.function.Executable testCode = () -> {
			MapExceptionFunctionCall.checkWaterFieldCountOnMap(halfMap);
		};
		Assertions.assertThrows(MapException.class, testCode);
	}

	@Test
	public void GivenHalfMap_FalseAmountMountainFieldsOnMap_ExpectMapException() {

		List<PlayerHalfMapNode> halfMap = new ArrayList<PlayerHalfMapNode>();

		for (int y = 0; y < 5; ++y) {
			for (int x = 0; x < 10; ++x) {
				PlayerHalfMapNode node = new PlayerHalfMapNode(x, y, false, ETerrain.Grass);
				halfMap.add(node);
			}
		}

		int count = BuildMap.rndmNumber(0, 4);
		for (int i = 0; i < count; ++i) {
			int pos = BuildMap.rndmNumber(0, 49);
			PlayerHalfMapNode node = halfMap.get(pos);

			PlayerHalfMapNode newNode = new PlayerHalfMapNode(node.getX(), node.getY(), ETerrain.Mountain);
			halfMap.set(pos, newNode);
		}

		org.junit.jupiter.api.function.Executable testCode = () -> {
			MapExceptionFunctionCall.checkWaterFieldCountOnMap(halfMap);
		};
		Assertions.assertThrows(MapException.class, testCode);
	}

	@Test
	public void GivenHalfMap_PlaceIncorrectAmountWaterFieldsOnBoarderXAxis_ExpectMapException() {
		MapExceptionFunctionCall mapException = new MapExceptionFunctionCall();
		List<PlayerHalfMapNode> halfMap = new ArrayList<PlayerHalfMapNode>();
		for (int y = 0; y < 5; ++y) {
			for (int x = 0; x < 10; ++x) {
				PlayerHalfMapNode node = new PlayerHalfMapNode(x, y, false, ETerrain.Grass);
				halfMap.add(node);
			}
		}

		for (int y = 0; y <= 40; y += 40) {
			for (int x = 0; x <= 9; x += 1) {
				PlayerHalfMapNode node = halfMap.get(y + x);
				PlayerHalfMapNode help = new PlayerHalfMapNode(node.getX(), node.getY(), ETerrain.Water);
				halfMap.set(x + y, help);
			}
		}

		org.junit.jupiter.api.function.Executable testCode = () -> {
			mapException.checkWaterFieldOnBoarderXAxis(halfMap);
		};
		Assertions.assertThrows(MapException.class, testCode);
	}

}
