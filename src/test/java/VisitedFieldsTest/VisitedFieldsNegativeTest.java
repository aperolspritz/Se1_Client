package VisitedFieldsTest;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import ExceptionFunctionCall.AddingVisitedExceptionFunctionCall;
import Exceptions.VisitedFieldsException;
import KI.VisitedFields;
import messagesBase.messagesFromClient.ETerrain;
import messagesBase.messagesFromServer.EFortState;
import messagesBase.messagesFromServer.EPlayerPositionState;
import messagesBase.messagesFromServer.ETreasureState;
import messagesBase.messagesFromServer.FullMapNode;

public class VisitedFieldsNegativeTest {
	@Test
	public void GivenAnEmptyFullMapList_AddingNoNodeToVisitedFields_ExpectVisitedFieldsException() {

		FullMapNode node = new FullMapNode(ETerrain.Grass, EPlayerPositionState.NoPlayerPresent,
				ETreasureState.NoOrUnknownTreasureState, EFortState.NoOrUnknownFortState, 1, 2);

		AddingVisitedExceptionFunctionCall addingVisException = new AddingVisitedExceptionFunctionCall();
		org.junit.jupiter.api.function.Executable testCode = () -> {
			addingVisException.checkVisitedFields(VisitedFields.visitedFields, node);
			;
		};
		Assertions.assertThrows(VisitedFieldsException.class, testCode);

	}

}
