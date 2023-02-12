package SmallCalculationClassTests;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThan;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import FullMapGeneratorForTest.FullMapGenerator;
import KI.SmallCalculationClass;
import MapOperations.BuildMap;
import messagesBase.messagesFromClient.ETerrain;
import messagesBase.messagesFromClient.PlayerHalfMapNode;
import messagesBase.messagesFromServer.EFortState;
import messagesBase.messagesFromServer.EPlayerPositionState;
import messagesBase.messagesFromServer.ETreasureState;
import messagesBase.messagesFromServer.FullMapNode;

public class SmallCalculationClassPositiveTest {

	@Test
	public void GivenUnsortedList_TransformInSortedOrder_ExpectCorrectTransformation() {

		FullMapGenerator fullMapGen = new FullMapGenerator();
		List<FullMapNode> map = fullMapGen.generateFullMapForTesting();

		List<FullMapNode> reverseMap = new ArrayList<FullMapNode>();

		for (int i = map.size() - 1; i >= 0; --i) {
			reverseMap.add(map.get(i));
		}

		reverseMap = SmallCalculationClass.transformMap(reverseMap);

		assertThat(reverseMap.size(), is(equalTo(100)));

	}

	@Test
	public void GivenHalfMap_CalculateAdjacents_ExpectMaxFourFieldsInList() {
		BuildMap buildMap = new BuildMap();
		List<PlayerHalfMapNode> map = buildMap.generateMap();
		PlayerHalfMapNode fortPos = new PlayerHalfMapNode();
		for (PlayerHalfMapNode node : map) {
			if (node.isFortPresent())
				fortPos = node;
		}
		List<PlayerHalfMapNode> adjacents = SmallCalculationClass.calculateAdjacentNodes(fortPos, map);

		assertThat(adjacents.size(), is(lessThan(5)));

	}

	@Test
	public void GivenFullMap_CalculateAdjacents_ExpectSizeFour() {
		FullMapGenerator fullMapGen = new FullMapGenerator();
		List<FullMapNode> selfGeneratedMapForTesting = fullMapGen.generateFullMapForTesting();
		FullMapNode testPos = new FullMapNode();

		for (FullMapNode node : selfGeneratedMapForTesting) {
			if (node.getPlayerPositionState() == EPlayerPositionState.MyPlayerPosition)
				testPos = node;
		}
		List<FullMapNode> adjacents = SmallCalculationClass.calculateAdjacentNodes(testPos, selfGeneratedMapForTesting);

		assertThat(adjacents.size(), is(equalTo(4)));

	}

	@Test
	public void GivenFullMap_CalculatingAdjacentsIfOnMountain_ExpectsSizeEight() {
		FullMapGenerator fullMapGen = new FullMapGenerator();
		List<FullMapNode> selfGeneratedMapForTesting = fullMapGen.generateFullMapForTesting();
		FullMapNode testPos = new FullMapNode();

		for (FullMapNode node : selfGeneratedMapForTesting) {
			if (node.getPlayerPositionState() == EPlayerPositionState.MyPlayerPosition)
				testPos = node;
		}
		List<FullMapNode> adjacents = SmallCalculationClass
				.calculateAdjacentsNodesIfOnMountain(selfGeneratedMapForTesting, testPos);

		assertThat(adjacents.size(), is(equalTo(8)));

	}

	@Test
	public void GivenTwoNodes_ComparingTheirCoordinates_ExpectTrue() {
		FullMapNode comp1 = new FullMapNode(ETerrain.Grass, EPlayerPositionState.NoPlayerPresent,
				ETreasureState.NoOrUnknownTreasureState, EFortState.NoOrUnknownFortState, 0, 2);
		FullMapNode comp2 = new FullMapNode(ETerrain.Grass, EPlayerPositionState.NoPlayerPresent,
				ETreasureState.NoOrUnknownTreasureState, EFortState.NoOrUnknownFortState, 0, 2);

		boolean compare = SmallCalculationClass.nodesAreEqual(comp1, comp2);
		assertThat(compare, is(equalTo(true)));

	}

}
