package CalculateNextMoveTests;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.jupiter.api.Disabled;

import FullMapGeneratorForTest.FullMapGenerator;
import KI.NextNode;
import KI.SmallCalculationClass;
import KI.VisitedFields;
import messagesBase.messagesFromClient.EMove;
import messagesBase.messagesFromClient.ETerrain;
import messagesBase.messagesFromServer.EFortState;
import messagesBase.messagesFromServer.EPlayerPositionState;
import messagesBase.messagesFromServer.ETreasureState;
import messagesBase.messagesFromServer.FullMapNode;

public class NextNodePositiveTest {

	@Test
	public void GivenPlayerPositonAndNextNodeToJumpOn_CalculateMoveToNextAdjacentNode_ExpectCorrectMove() {

		FullMapNode currPos = new FullMapNode(ETerrain.Grass, EPlayerPositionState.NoPlayerPresent,
				ETreasureState.NoOrUnknownTreasureState, EFortState.NoOrUnknownFortState, 5, 8);
		FullMapNode targetNode = new FullMapNode(ETerrain.Grass, EPlayerPositionState.NoPlayerPresent,
				ETreasureState.NoOrUnknownTreasureState, EFortState.NoOrUnknownFortState, 4, 8);

		NextNode nextNode = new NextNode();
		EMove test = nextNode.evaluateMoveFromNode(currPos, targetNode);

		assertThat(test, is(equalTo(EMove.Left)));

	}

	@Test
	public void CreateList_CalculateNearestTarget_ExpectNearesFieldAsTarget() {

		List<FullMapNode> pseudoAdjacents = new ArrayList<FullMapNode>();
		FullMapNode node1 = new FullMapNode(ETerrain.Grass, EPlayerPositionState.NoPlayerPresent,
				ETreasureState.NoOrUnknownTreasureState, EFortState.NoOrUnknownFortState, 5, 8);
		pseudoAdjacents.add(node1);
		FullMapNode node2 = new FullMapNode(ETerrain.Grass, EPlayerPositionState.NoPlayerPresent,
				ETreasureState.NoOrUnknownTreasureState, EFortState.NoOrUnknownFortState, 3, 10);
		pseudoAdjacents.add(node2);
		FullMapNode node3 = new FullMapNode(ETerrain.Grass, EPlayerPositionState.NoPlayerPresent,
				ETreasureState.NoOrUnknownTreasureState, EFortState.NoOrUnknownFortState, 3, 3);
		pseudoAdjacents.add(node3);
		FullMapNode node4 = new FullMapNode(ETerrain.Grass, EPlayerPositionState.NoPlayerPresent,
				ETreasureState.NoOrUnknownTreasureState, EFortState.NoOrUnknownFortState, 8, 8);
		pseudoAdjacents.add(node4);

		NextNode nextNode = new NextNode();

		FullMapNode checkNode = nextNode.calcShortestDistanceToTargetFromAdj(pseudoAdjacents);

		assertThat(checkNode, is(equalTo(node3)));
	}

	@Test
	public void AddGrasFieldsToList_AddSomeToVisitedFieldsANDFilterListFromVisited_ExpectOnlyUnvisited() {

		FullMapGenerator fullMapGen = new FullMapGenerator();
		List<FullMapNode> testFields = new ArrayList<FullMapNode>();
		List<FullMapNode> fullMapForTest = fullMapGen.generateFullMapForTesting();
		VisitedFields visitedFields = new VisitedFields();
		NextNode nextNode = new NextNode();

		for (FullMapNode node : fullMapForTest) {
			if (node.getTerrain() == ETerrain.Grass && node.getX() % 2 == 0) {
				testFields.add(node);
				if (node.getX() % 4 == 0) {
					visitedFields.addToVisitedFields(node);
				}
			}
		}

		fullMapForTest = nextNode.filterVisited(testFields);

		for (FullMapNode node : fullMapForTest) {
			assertThat(false, is(equalTo(SmallCalculationClass.isInVisited(node))));
		}
	}

	@Disabled
	public void AddGrasAndMountainToNewList_AddSomeToVisitedFields_ExpectOnlyUnvisitedMountain() {
		FullMapGenerator fullMapGen = new FullMapGenerator();
		List<FullMapNode> testFields = new ArrayList<FullMapNode>();
		List<FullMapNode> fullMapForTest = fullMapGen.generateFullMapForTesting();
		VisitedFields visitedFields = new VisitedFields();
		NextNode nextNode = new NextNode();

		for (FullMapNode node : fullMapForTest) {
			if ((node.getTerrain() == ETerrain.Grass || node.getTerrain() == ETerrain.Grass) && node.getX() % 2 == 0) {
				testFields.add(node);
				if (node.getX() % 4 == 0) {
					visitedFields.addToVisitedFields(node);
				}
			}
		}

		FullMapNode toBeSafeNode = new FullMapNode(ETerrain.Mountain, EPlayerPositionState.NoPlayerPresent,
				ETreasureState.NoOrUnknownTreasureState, EFortState.NoOrUnknownFortState, 4, 5);
		testFields.add(toBeSafeNode);
		fullMapForTest = nextNode.filterVisited(testFields);

		for (FullMapNode node : fullMapForTest) {
			assertThat(node.getTerrain(), is(equalTo(ETerrain.Mountain)));
		}

	}

}
