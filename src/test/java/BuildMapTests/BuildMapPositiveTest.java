package BuildMapTests;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThanOrEqualTo;

import java.util.List;

import org.junit.Test;

import MapOperations.BuildMap;
import messagesBase.messagesFromClient.ETerrain;
import messagesBase.messagesFromClient.PlayerHalfMapNode;

public class BuildMapPositiveTest {
	@Test
	public void GivenNothing_CreatingNewHalfMap_ExpectCorrectMapSize() {

		BuildMap map = new BuildMap();
		int mapSize = map.generateMap().size();
		assertThat(mapSize, is(equalTo(50)));
	}

	@Test
	public void GivenHalfMap_PlacingFort_ExpectCorrectTerrain() {

		BuildMap buildMap = new BuildMap();
		List<PlayerHalfMapNode> map = buildMap.generateMap();
		ETerrain fortTerrain = buildMap.placeFort(map).getTerrain();
		assertThat(fortTerrain, is(equalTo(ETerrain.Grass)));

	}

	@Test
	public void GivenHalfMap_WaterFieldCount_ExpectCorrectWaterFieldCount() {
		BuildMap buildMap = new BuildMap();
		List<PlayerHalfMapNode> map = buildMap.generateMap();

		int waterFieldCount = 0;
		for (PlayerHalfMapNode node : map) {
			if (node.getTerrain() == ETerrain.Water)
				waterFieldCount++;
		}
		assertThat(waterFieldCount, is(greaterThanOrEqualTo(7)));

	}

	@Test
	public void GivenHalfMap_MountainFieldCount_ExpectCorrectMountainFieldCount() {
		BuildMap buildMap = new BuildMap();
		List<PlayerHalfMapNode> map = buildMap.generateMap();
		int mountainFieldCount = 0;
		for (PlayerHalfMapNode node : map) {
			if (node.getTerrain() == ETerrain.Mountain)
				mountainFieldCount++;
		}
		assertThat(mountainFieldCount, is(greaterThanOrEqualTo(5)));
	}

	@Test
	public void GivenHalfMap_CheckWaterFieldsOnXAxisBoarders_ExpectCorrectWaterFieldPlacementXAxisWithYCoordinateIs0() {
		BuildMap buildMap = new BuildMap();
		List<PlayerHalfMapNode> map = buildMap.generateMap();
		int waterFieldCountBoarderXAxis = 0;

		for (PlayerHalfMapNode node : map) {

			if (node.getY() == 0) {
				if (node.getTerrain() == ETerrain.Water) {
					waterFieldCountBoarderXAxis++;
				}
			}
		}
		assertThat(waterFieldCountBoarderXAxis, is(lessThanOrEqualTo(4)));
	}

	@Test
	public void GivenHalfMap_CheckWaterFieldsOnXAxisBoarders_ExpectCorrectWaterFieldPlacementXAxisWithYCoordinateIs4() {
		BuildMap buildMap = new BuildMap();
		List<PlayerHalfMapNode> map = buildMap.generateMap();
		int waterFieldCountBoarderXAxis = 0;

		for (PlayerHalfMapNode node : map) {

			if (node.getY() == 4) {
				if (node.getTerrain() == ETerrain.Water) {
					waterFieldCountBoarderXAxis++;
				}
			}
		}
		assertThat(waterFieldCountBoarderXAxis, is(lessThanOrEqualTo(4)));
	}

	@Test
	public void GivenHalfMap_CheckWaterFieldsOnYAxisBoarders_ExpectCorrectWaterFieldPlacementYAxisWithXCoordinateIs0() {
		BuildMap buildMap = new BuildMap();
		List<PlayerHalfMapNode> map = buildMap.generateMap();
		int waterFieldCountBoarderXAxis = 0;

		for (PlayerHalfMapNode node : map) {

			if (node.getX() == 0) {
				if (node.getTerrain() == ETerrain.Water) {
					waterFieldCountBoarderXAxis++;
				}
			}
		}
		assertThat(waterFieldCountBoarderXAxis, is(lessThanOrEqualTo(2)));
	}

	@Test
	public void GivenHalfMap_CheckWaterFieldsOnYAxisBoarders_ExpectCorrectWaterFieldPlacementYAxisWithXCoordinateIs9() {
		BuildMap buildMap = new BuildMap();
		List<PlayerHalfMapNode> map = buildMap.generateMap();
		int waterFieldCountBoarderXAxis = 0;

		for (PlayerHalfMapNode node : map) {

			if (node.getX() == 9) {
				if (node.getTerrain() == ETerrain.Water) {
					waterFieldCountBoarderXAxis++;
				}
			}
		}
		assertThat(waterFieldCountBoarderXAxis, is(lessThanOrEqualTo(2)));
	}

}
