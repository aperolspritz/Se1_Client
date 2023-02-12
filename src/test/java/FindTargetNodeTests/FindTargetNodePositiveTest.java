package FindTargetNodeTests;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import java.util.List;

import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;

import FullMapGeneratorForTest.FullMapGenerator;
import KI.FindTargetNode;
import messagesBase.messagesFromClient.ETerrain;
import messagesBase.messagesFromServer.EPlayerPositionState;
import messagesBase.messagesFromServer.FullMapNode;

public class FindTargetNodePositiveTest {
	@BeforeAll
	@Test
	public void GivenListOfTargetNodes_IteratingThem_ExpectOnlyFieldsWithTypeMountain() {
		FullMapGenerator fullMapGen = new FullMapGenerator();
		List<FullMapNode> selfGeneratedMapForTesting = fullMapGen.generateFullMapForTesting();
		FindTargetNode targetNode = new FindTargetNode();
		List<FullMapNode> targetNodeList = targetNode.addMountainFields(selfGeneratedMapForTesting);
		boolean onlyMountains = true;

		for (FullMapNode node : targetNodeList) {
			if (node.getTerrain() != ETerrain.Mountain)
				onlyMountains = false;
		}
		assertThat(onlyMountains, is(equalTo(true)));
	}

	@Test
	public void GivenListofTargetNodes_CalculatingTarget_ExpectMountainFieldAsTarget() {
		FullMapGenerator fullMapGen = new FullMapGenerator();
		List<FullMapNode> selfGeneratedMapForTesting = fullMapGen.generateFullMapForTesting();

		FindTargetNode targetNode = new FindTargetNode();
		List<FullMapNode> targetNodeList = targetNode.addMountainFields(selfGeneratedMapForTesting);
		FindTargetNode.mountainFieldsOnMap = targetNodeList;

		FullMapNode testPos = new FullMapNode();
		for (FullMapNode node : targetNodeList) {
			if (node.getPlayerPositionState() == EPlayerPositionState.MyPlayerPosition)
				testPos = node;
		}
		FullMapNode valueFromNextTarget = targetNode.nextTarget(testPos);
		assertThat(valueFromNextTarget.getTerrain(), is(equalTo(ETerrain.Mountain)));
	}
}
