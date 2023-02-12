package BuildMapTests;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.is;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import MapOperations.BuildMap;
import messagesBase.messagesFromClient.ETerrain;
import messagesBase.messagesFromClient.PlayerHalfMapNode;

public class BuildMapDataDrivenTest {
	@ParameterizedTest
	@MethodSource("provideDataForPlacingFort")
	public void GivenHalfMap_PlacingFort_ExpectCorrectTerrain(List<PlayerHalfMapNode> map, ETerrain expectedTerrain) {
		BuildMap buildMap = new BuildMap();
		ETerrain fortTerrain = buildMap.placeFort(map).getTerrain();
		assertThat(fortTerrain, is(equalTo(expectedTerrain)));

	}

	private static Stream<Arguments> provideDataForPlacingFort() {
		BuildMap buildMap = new BuildMap();
		List<PlayerHalfMapNode> map1 = buildMap.generateMap();
		List<PlayerHalfMapNode> map2 = buildMap.generateMap();
		List<PlayerHalfMapNode> map3 = buildMap.generateMap();

		return Stream.of(Arguments.of(map1, ETerrain.Grass), Arguments.of(map2, ETerrain.Grass),
				Arguments.of(map3, ETerrain.Grass));
	}

	@ParameterizedTest
	@MethodSource("provideDataForCheckingWaterFieldsOnHalfMap")
	public void GivenListOfWaterFieldsOnMap_CountingList_ExpectCorrectAmountOfWaterFieldsOnMap(
			List<PlayerHalfMapNode> map, int expected) {

		BuildMap buildMap = new BuildMap();
		int waterFieldCount = buildMap.checkMapSize(map);

		assertThat(waterFieldCount, is(greaterThanOrEqualTo(expected)));

	}

	private static List<PlayerHalfMapNode> addingWaterFields(List<PlayerHalfMapNode> map) {
		List<PlayerHalfMapNode> waterFields = new ArrayList<PlayerHalfMapNode>();
		for (PlayerHalfMapNode node : map) {
			if (node.getTerrain() == ETerrain.Water)
				waterFields.add(node);
		}
		return waterFields;
	}

	private static List<PlayerHalfMapNode> addingMountainFields(List<PlayerHalfMapNode> map) {
		List<PlayerHalfMapNode> waterFields = new ArrayList<PlayerHalfMapNode>();
		for (PlayerHalfMapNode node : map) {
			if (node.getTerrain() == ETerrain.Water)
				waterFields.add(node);
		}
		return waterFields;
	}

	private static Stream<Arguments> provideDataForCheckingWaterFieldsOnHalfMap() {
		BuildMap buildMap = new BuildMap();
		List<PlayerHalfMapNode> map1 = buildMap.generateMap();
		map1 = addingWaterFields(map1);

		List<PlayerHalfMapNode> map2 = buildMap.generateMap();
		map2 = addingWaterFields(map2);

		List<PlayerHalfMapNode> map3 = buildMap.generateMap();
		map3 = addingWaterFields(map3);

		return Stream.of(Arguments.of(map1, 7), Arguments.of(map2, 7), Arguments.of(map3, 7));

	}

	@ParameterizedTest
	@MethodSource("provideDataforCheckingMountainFieldsOnHalfMap")
	public void GivenListOfMountainFieldsOnMap_CountingList_ExpectCorrectAmountOfMountainFieldsOnMap(
			List<PlayerHalfMapNode> map, int expected) {
		BuildMap buildMap = new BuildMap();
		int mountainFieldCount = buildMap.checkMapSize(map);

		assertThat(mountainFieldCount, is(greaterThanOrEqualTo(expected)));

	}

	private static Stream<Arguments> provideDataforCheckingMountainFieldsOnHalfMap() {

		BuildMap buildMap = new BuildMap();
		List<PlayerHalfMapNode> map1 = buildMap.generateMap();
		map1 = addingMountainFields(map1);
		List<PlayerHalfMapNode> map2 = buildMap.generateMap();
		map2 = addingMountainFields(map2);
		List<PlayerHalfMapNode> map3 = buildMap.generateMap();
		map3 = addingMountainFields(map3);
		return Stream.of(Arguments.of(map1, 7), Arguments.of(map2, 7), Arguments.of(map3, 7));
	}

}
