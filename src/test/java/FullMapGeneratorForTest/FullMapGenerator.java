package FullMapGeneratorForTest;

import java.util.ArrayList;
import java.util.List;

import MapOperations.BuildMap;
import messagesBase.messagesFromClient.ETerrain;
import messagesBase.messagesFromServer.EFortState;
import messagesBase.messagesFromServer.EPlayerPositionState;
import messagesBase.messagesFromServer.ETreasureState;
import messagesBase.messagesFromServer.FullMapNode;

public class FullMapGenerator {

	public List<FullMapNode> generateFullMapForTesting() {
		List<FullMapNode> map = new ArrayList<FullMapNode>();

		for (int y = 0; y < 10; ++y) {
			for (int x = 0; x < 10; ++x) {

				if (x == 5 && y == 5) {
					FullMapNode myPlayerPos = new FullMapNode(ETerrain.Grass, EPlayerPositionState.MyPlayerPosition,
							ETreasureState.NoOrUnknownTreasureState, EFortState.NoOrUnknownFortState, x, y);
					map.add(myPlayerPos);
				} else if (x == 5 && y == 4) {
					FullMapNode treasurePos = new FullMapNode(ETerrain.Grass, EPlayerPositionState.NoPlayerPresent,
							ETreasureState.MyTreasureIsPresent, EFortState.NoOrUnknownFortState, x, y);
					map.add(treasurePos);
				} else if (x == 6 && y == 5) {
					FullMapNode fortPos = new FullMapNode(ETerrain.Grass, EPlayerPositionState.NoPlayerPresent,
							ETreasureState.NoOrUnknownTreasureState, EFortState.MyFortPresent, x, y);
					map.add(fortPos);

				} else if (x == 4 && y == 5) {
					FullMapNode newNode = new FullMapNode(ETerrain.Mountain, EPlayerPositionState.NoPlayerPresent,
							ETreasureState.NoOrUnknownTreasureState, EFortState.NoOrUnknownFortState, 4, 5);
					map.add(newNode);

				} else {
					FullMapNode node = new FullMapNode(ETerrain.Grass, EPlayerPositionState.NoPlayerPresent,
							ETreasureState.NoOrUnknownTreasureState, EFortState.NoOrUnknownFortState, x, y);
					map.add(node);
				}
			}
		}

		setMountain(map);
		setWater(map);

		return map;

	}

	private void setMountain(List<FullMapNode> map) {
		int count = BuildMap.rndmNumber(15, 25);
		for (int i = 0; i < count; ++i) {
			int pos = BuildMap.rndmNumber(0, 99);
			FullMapNode node = map.get(pos);
			if (node.getFortState() != EFortState.MyFortPresent
					&& node.getPlayerPositionState() != EPlayerPositionState.MyPlayerPosition
					&& node.getTreasureState() != ETreasureState.MyTreasureIsPresent
					&& (node.getX() != 4 && node.getY() != 5) && (node.getX() != 5 && node.getY() != 6)) {
				FullMapNode newNode = new FullMapNode(ETerrain.Mountain, EPlayerPositionState.NoPlayerPresent,
						ETreasureState.NoOrUnknownTreasureState, EFortState.NoOrUnknownFortState, node.getX(),
						node.getY());
				map.set(pos, newNode);
			}
		}
	}

	private void setWater(List<FullMapNode> map) {
		int count = BuildMap.rndmNumber(14, 18);
		for (int i = 0; i < count; ++i) {
			int pos = BuildMap.rndmNumber(0, 99);
			FullMapNode node = map.get(pos);
			if (node.getFortState() != EFortState.MyFortPresent
					&& node.getPlayerPositionState() != EPlayerPositionState.MyPlayerPosition
					&& node.getTreasureState() != ETreasureState.MyTreasureIsPresent
					&& (node.getX() != 4 && node.getY() != 5) && (node.getX() != 5 && node.getY() != 6)) {
				FullMapNode newNode = new FullMapNode(ETerrain.Water, EPlayerPositionState.NoPlayerPresent,
						ETreasureState.NoOrUnknownTreasureState, EFortState.NoOrUnknownFortState, node.getX(),
						node.getY());
				map.set(pos, newNode);
			}
		}
	}

}
