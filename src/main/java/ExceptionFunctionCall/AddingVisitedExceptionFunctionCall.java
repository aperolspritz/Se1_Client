package ExceptionFunctionCall;

import java.util.List;

import Exceptions.MoveExeption;
import Exceptions.VisitedFieldsException;
import KI.SmallCalculationClass;
import KI.VisitedFields;
import messagesBase.messagesFromServer.FullMapNode;

public class AddingVisitedExceptionFunctionCall {

	public static void checkVisitedFields(List<FullMapNode> visitedFields, FullMapNode checkNode) {
		boolean isInVisited = false;

		for (FullMapNode node : visitedFields) {
			isInVisited = SmallCalculationClass.isInVisited(node);
		}

		if (!isInVisited)
			throw new VisitedFieldsException("Node was not in Visited Fields but should be");
	}

	public static void currentPositionIsVisitedException(FullMapNode currPos) {

		for (FullMapNode curr : VisitedFields.visitedFields) {
			if (SmallCalculationClass.nodesAreEqual(currPos, curr)) {
				throw new MoveExeption("Field Already visited ", currPos);
			}
		}
	}

}
