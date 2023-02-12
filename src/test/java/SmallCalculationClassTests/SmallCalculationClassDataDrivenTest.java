package SmallCalculationClassTests;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThanOrEqualTo;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import FullMapGeneratorForTest.FullMapGenerator;
import KI.SmallCalculationClass;
import messagesBase.messagesFromServer.EPlayerPositionState;
import messagesBase.messagesFromServer.FullMapNode;

public class SmallCalculationClassDataDrivenTest {

	@ParameterizedTest
	@MethodSource("provideDataForAdjacentsFullMap")

	public void GivenHalfMap_CalculateAdjacents_ExpectSizeFour(List<FullMapNode> map, int expected) {

		FullMapNode testPos = new FullMapNode();
		List<FullMapNode> adjacents = new ArrayList<FullMapNode>();

		for (FullMapNode node : map) {
			if (node.getPlayerPositionState() == EPlayerPositionState.MyPlayerPosition)
				testPos = node;
		}

		adjacents = SmallCalculationClass.calculateAdjacentNodes(testPos, map);
		assertThat(adjacents.size(), is(lessThanOrEqualTo(expected)));

	}

	private static Stream<Arguments> provideDataForAdjacentsFullMap() {

		FullMapGenerator fullMapGen1 = new FullMapGenerator();
		List<FullMapNode> selfGeneratedMapForTesting1 = fullMapGen1.generateFullMapForTesting();

		FullMapGenerator fullMapGen2 = new FullMapGenerator();
		List<FullMapNode> selfGeneratedMapForTesting2 = fullMapGen2.generateFullMapForTesting();

		FullMapGenerator fullMapGen3 = new FullMapGenerator();
		List<FullMapNode> selfGeneratedMapForTesting3 = fullMapGen3.generateFullMapForTesting();

		return Stream.of(Arguments.of(selfGeneratedMapForTesting1, 4), Arguments.of(selfGeneratedMapForTesting2, 4),
				Arguments.of(selfGeneratedMapForTesting3, 4));

	}

	@ParameterizedTest
	@MethodSource("provideDataForAdjacentsIfOnMountain")
	public void GivenFullMap_CalculatingAdjacentsIfOnMountain_ExpectsSizeEight(List<FullMapNode> map, int expected) {

		FullMapNode testPos = new FullMapNode();
		List<FullMapNode> adjacents = new ArrayList<FullMapNode>();

		for (FullMapNode node : map) {
			if (node.getPlayerPositionState() == EPlayerPositionState.MyPlayerPosition)
				testPos = node;
		}

		adjacents = SmallCalculationClass.calculateAdjacentNodes(testPos, map);
		assertThat(adjacents.size(), is(lessThanOrEqualTo(expected)));

	}

	private static Stream<Arguments> provideDataForAdjacentsIfOnMountain() {
		FullMapGenerator fullMapGen1 = new FullMapGenerator();
		List<FullMapNode> selfGeneratedMapForTesting1 = fullMapGen1.generateFullMapForTesting();

		FullMapGenerator fullMapGen2 = new FullMapGenerator();
		List<FullMapNode> selfGeneratedMapForTesting2 = fullMapGen2.generateFullMapForTesting();

		FullMapGenerator fullMapGen3 = new FullMapGenerator();
		List<FullMapNode> selfGeneratedMapForTesting3 = fullMapGen3.generateFullMapForTesting();

		return Stream.of(Arguments.of(selfGeneratedMapForTesting1, 8), Arguments.of(selfGeneratedMapForTesting2, 8),
				Arguments.of(selfGeneratedMapForTesting3, 8));

	}

}
