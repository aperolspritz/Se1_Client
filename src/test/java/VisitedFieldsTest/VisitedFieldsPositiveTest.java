package VisitedFieldsTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import java.util.List;

import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;

import FullMapGeneratorForTest.FullMapGenerator;
import KI.VisitedFields;
import messagesBase.messagesFromClient.ETerrain;
import messagesBase.messagesFromServer.EFortState;
import messagesBase.messagesFromServer.EPlayerPositionState;
import messagesBase.messagesFromServer.ETreasureState;
import messagesBase.messagesFromServer.FullMapNode;

public class VisitedFieldsPositiveTest {

	@Test
	@BeforeAll
	public void GivenAnEmptyFullMapList_AddingNode_ExpectNodeAddedToList() {
		VisitedFields visitedFields = new VisitedFields();
		FullMapNode node = new FullMapNode(ETerrain.Grass, EPlayerPositionState.NoPlayerPresent,
				ETreasureState.NoOrUnknownTreasureState, EFortState.NoOrUnknownFortState, 1, 2);
		VisitedFields.visitedFields.clear();

		boolean testValue = visitedFields.addToVisitedFields(node);
		assertThat(testValue, is(equalTo(true)));

	}

	@Test

	public void GivenMap_MapForShape_ExpectA10x10Map() {

		FullMapGenerator fullMapGen = new FullMapGenerator();
		List<FullMapNode> map = fullMapGen.generateFullMapForTesting();

		boolean is20x5 = VisitedFields.checkMapForShape(map);

		assertThat(is20x5, is(equalTo(false)));

	}
}
