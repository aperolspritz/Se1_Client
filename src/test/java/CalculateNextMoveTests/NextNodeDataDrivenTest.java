package CalculateNextMoveTests;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import FullMapGeneratorForTest.FullMapGenerator;
import KI.NextNode;
import KI.SmallCalculationClass;
import KI.VisitedFields;
import messagesBase.messagesFromClient.ETerrain;
import messagesBase.messagesFromServer.FullMapNode;

public class NextNodeDataDrivenTest {

	@ParameterizedTest
	@MethodSource("provideDataForFilterList")
	public void AddGrasFieldsToList_AddSomeToVisitedFieldsANDFilterListFromVisited_ExpectOnlyUnvisited(
			List<FullMapNode> testFields, boolean expected) {

		NextNode nextNode = new NextNode();
		List<FullMapNode> helpList = new ArrayList<FullMapNode>();
		helpList = nextNode.filterVisited(testFields);

		for (FullMapNode node : helpList) {
			assertThat(expected, is(equalTo(SmallCalculationClass.isInVisited(node))));
		}

	}

	private static Stream<Arguments> provideDataForFilterList() {
		FullMapGenerator fullMapGen = new FullMapGenerator();
		List<FullMapNode> testFields1 = new ArrayList<FullMapNode>();
		List<FullMapNode> testFields2 = new ArrayList<FullMapNode>();
		List<FullMapNode> testFields3 = new ArrayList<FullMapNode>();
		List<FullMapNode> fullMapForTest = fullMapGen.generateFullMapForTesting();
		VisitedFields visitedFields = new VisitedFields();

		for (FullMapNode node : fullMapForTest) {
			if (node.getTerrain() == ETerrain.Grass && node.getX() % 2 == 0) {
				testFields1.add(node);

				if (node.getX() % 4 == 0) {
					visitedFields.addToVisitedFields(node);
				}
			}
		}
		for (FullMapNode node : fullMapForTest) {
			if (node.getTerrain() == ETerrain.Grass && node.getX() % 2 == 0) {
				testFields2.add(node);

				if (node.getX() % 8 == 0) {
					visitedFields.addToVisitedFields(node);
				}
			}
		}
		for (FullMapNode node : fullMapForTest) {
			if (node.getTerrain() == ETerrain.Grass && node.getX() % 2 == 0) {
				testFields3.add(node);

				if (node.getX() % 6 == 0) {
					visitedFields.addToVisitedFields(node);
				}
			}
		}

		return Stream.of(Arguments.of(testFields1, false), Arguments.of(testFields2, false),
				Arguments.of(testFields3, false));

	}
}
