package FindTargetNodeTests;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import FullMapGeneratorForTest.FullMapGenerator;
import KI.FindTargetNode;
import messagesBase.messagesFromClient.ETerrain;
import messagesBase.messagesFromServer.EPlayerPositionState;
import messagesBase.messagesFromServer.FullMapNode;

public class FindTargetNodeDataDrivenTest {

	@ParameterizedTest
	@MethodSource("provideDataForParmaterizedTest")

	public void GivenListOfTargetNodes_IteratingThem_ExpectOnlyFieldsWithTypeMountain(List<FullMapNode> map,
			ETerrain expected) {

		FindTargetNode targetNode = new FindTargetNode();
		map = targetNode.addMountainFields(map);

		for (FullMapNode node : map) {
			assertThat(node.getTerrain(), is(equalTo(expected)));
		}

	}

	private static Stream<Arguments> provideDataForParmaterizedTest() {
		FullMapGenerator fullMapGen = new FullMapGenerator();
		List<FullMapNode> selfGeneratedMapForTesting1 = fullMapGen.generateFullMapForTesting();
		List<FullMapNode> selfGeneratedMapForTesting2 = fullMapGen.generateFullMapForTesting();
		List<FullMapNode> selfGeneratedMapForTesting3 = fullMapGen.generateFullMapForTesting();
		List<FullMapNode> selfGeneratedMapForTesting4 = fullMapGen.generateFullMapForTesting();

		return Stream.of(Arguments.of(selfGeneratedMapForTesting1, ETerrain.Mountain),
				Arguments.of(selfGeneratedMapForTesting2, ETerrain.Mountain),
				Arguments.of(selfGeneratedMapForTesting3, ETerrain.Mountain),
				Arguments.of(selfGeneratedMapForTesting4, ETerrain.Mountain));

	}

	@ParameterizedTest
	@MethodSource("provideDataForCalculatingTarget")
	public void GivenListofTargetNodes_CalculatingTarget_ExpectMountainFieldAsTarget(List<FullMapNode> fullMap,
			ETerrain expected) {

		FindTargetNode targetNode = new FindTargetNode();
		targetNode.addMountainFields(fullMap);
		FullMapNode currPos = new FullMapNode();

		for (FullMapNode node : fullMap) {
			if (node.getPlayerPositionState() == EPlayerPositionState.MyPlayerPosition) {
				currPos = node;
				break;
			}
		}

		FullMapNode calculatedTarget = targetNode.nextTarget(currPos);

		assertThat(calculatedTarget.getTerrain(), is(equalTo(expected)));

	}

	private static Stream<Arguments> provideDataForCalculatingTarget() {
		FullMapGenerator fullMapGen = new FullMapGenerator();
		List<FullMapNode> selfGeneratedMapForTesting1 = fullMapGen.generateFullMapForTesting();
		List<FullMapNode> selfGeneratedMapForTesting2 = fullMapGen.generateFullMapForTesting();
		List<FullMapNode> selfGeneratedMapForTesting3 = fullMapGen.generateFullMapForTesting();

		return Stream.of(Arguments.of(selfGeneratedMapForTesting1, ETerrain.Mountain),
				Arguments.of(selfGeneratedMapForTesting2, ETerrain.Mountain),
				Arguments.of(selfGeneratedMapForTesting3, ETerrain.Mountain));

	}

}
