package FullMapGeneratorForTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import java.util.List;

import org.junit.Test;

import messagesBase.messagesFromServer.FullMapNode;

public class FullMapGeneratorPositiveTest {

	@Test
	public void GenerateFullMap_ExpectCorrectMapSize() {

		FullMapGenerator fullMapGen = new FullMapGenerator();
		List<FullMapNode> map = fullMapGen.generateFullMapForTesting();

		assertThat(map.size(), is(equalTo(100)));
	}
}
