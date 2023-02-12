package FindTargetNodeTests;

import java.util.List;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import ExceptionFunctionCall.TargetNodeExceptionFunctionCall;
import Exceptions.TargetNodeException;
import FullMapGeneratorForTest.FullMapGenerator;
import KI.FindTargetNode;
import messagesBase.messagesFromClient.ETerrain;
import messagesBase.messagesFromServer.EFortState;
import messagesBase.messagesFromServer.EPlayerPositionState;
import messagesBase.messagesFromServer.ETreasureState;
import messagesBase.messagesFromServer.FullMapNode;

public class FindTargetNodeNegativeTest {

	@Test
	public void GivenListOfTargetNodes_IteratingThem_ExpectTargetNodeException() {
		FullMapGenerator fullMapGen = new FullMapGenerator();
		List<FullMapNode> selfGeneratedMapForTesting = fullMapGen.generateFullMapForTesting();
		FindTargetNode targetNode = new FindTargetNode();
		List<FullMapNode> targetNodeList = targetNode.addMountainFields(selfGeneratedMapForTesting);

		TargetNodeExceptionFunctionCall targetNodeExceptionFunctionCall = new TargetNodeExceptionFunctionCall();
		FullMapNode help = targetNodeList.get(0);
		FullMapNode wrongTerrain = new FullMapNode(ETerrain.Grass, EPlayerPositionState.NoPlayerPresent,
				ETreasureState.NoOrUnknownTreasureState, EFortState.NoOrUnknownFortState, help.getX(), help.getY());

		targetNodeList.add(wrongTerrain);
		org.junit.jupiter.api.function.Executable testCode = () -> {
			targetNodeExceptionFunctionCall.checkMountainFieldList(targetNodeList);
		};
		Assertions.assertThrows(TargetNodeException.class, testCode);

	}
}
