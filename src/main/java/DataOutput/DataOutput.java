package DataOutput;

import java.beans.PropertyChangeListener;

import ClientCoordination.ClientCoordinator;
import GameData.ClientData;
import KI.FindTargetNode;
import KI.SmallCalculationClass;
import KI.VisitedFields;
import messagesBase.messagesFromClient.ETerrain;
import messagesBase.messagesFromClient.PlayerHalfMapNode;
import messagesBase.messagesFromServer.EFortState;
import messagesBase.messagesFromServer.EPlayerGameState;
import messagesBase.messagesFromServer.FullMapNode;

public class DataOutput {

	// private ClientCoordinator coordinator;

	public DataOutput(ClientData data, ClientCoordinator coodinator) {
		super();
		// this.coordinator = coordinator;
		data.addPropertyChangeListener(clientDataChangedListener);
		data.addPropertyChangeListenerMap(mapDataChangedListener);
		data.addPropertyChangeListenerPlayerState(PlayerStateDataChangedListener);
		data.addPropertyChangeListenerPlayerFullMap(fullMapStateDataChangedListener);

	}

	final PropertyChangeListener clientDataChangedListener = event -> {

		Object data = event.getSource();
		// Object newValue = event.getNewValue();

		if (data instanceof ClientData) {
			ClientData castedData = (ClientData) data;
			System.out.println(castedData.getPlayerID());
			System.out.println(" --- Player Registered ---");
		}
	};

	final PropertyChangeListener mapDataChangedListener = event -> {
		Object data = event.getSource();
		// Object newValue = event.getNewValue();

		if (data instanceof ClientData) {
			ClientData castedData = (ClientData) data;

			for (PlayerHalfMapNode node : castedData.getHalfMap()) {
				if (node.isFortPresent())
					System.out.print("[" + "  @FORT  " + "]");
				else {
					System.out.print("[" + node.getX() + "," + node.getY() + "|" + type(node.getTerrain()) + "]");

				}
				if (node.getX() == 9)
					System.out.println();
			}
		}
		System.out.println();
		System.out.println("HalfMap: Sent");

	};

	private String type(ETerrain t) {
		String ret = "";
		if (t == ETerrain.Grass)
			ret = "GRASS";
		if (t == ETerrain.Mountain)
			ret = "MOUNT";
		if (t == ETerrain.Water)
			ret = "WATER";
		return ret;
	}

	private String type20x5(ETerrain t) {
		String ret = "";
		if (t == ETerrain.Grass)
			ret = "G";
		if (t == ETerrain.Mountain)
			ret = "M";
		if (t == ETerrain.Water)
			ret = "W";
		return ret;
	}

	final PropertyChangeListener PlayerStateDataChangedListener = event -> {
		Object data = event.getSource();

		if (data instanceof ClientData) {
			ClientData castedData = (ClientData) data;
			if (castedData.getEPlayerGameState() == EPlayerGameState.Lost
					|| castedData.getEPlayerGameState() == EPlayerGameState.Won) {
				System.out.println("--------- CLIENT " + castedData.getEPlayerGameState().toString() + "---------");
			} else
				System.out.println(castedData.getEPlayerGameState().toString());
		}
	};

	private String x(int x) {
		if (x > 9)
			return Integer.toString(x);

		return Integer.toString(x) + " ";
	}

	private void map10x10(FullMapNode node) {
		switch (node.getPlayerPositionState()) {
		case BothPlayerPosition:
			System.out.print("[  @BOTH   ] ");
			break;
		case MyPlayerPosition:
			System.out.print("[   @ME    ] ");
			break;
		case EnemyPlayerPosition:
			System.out.print("[  @ENEMY  ] ");
			break;
		case NoPlayerPresent:
			System.out.print("[" + x(node.getX()) + "," + node.getY() + "|" + type(node.getTerrain()) + "] ");
			break;

		}

	}

	private void map20x5(FullMapNode node) {
		switch (node.getPlayerPositionState()) {
		case BothPlayerPosition:
			System.out.print("[@BOTH ]");
			break;
		case MyPlayerPosition:
			System.out.print("[ @ME  ]");
			break;
		case EnemyPlayerPosition:
			System.out.print("[@ENEMY]");
			break;
		case NoPlayerPresent:
			System.out.print("[" + x(node.getX()) + "," + node.getY() + "|" + type20x5(node.getTerrain()) + "]");
			break;

		}

	}

	final PropertyChangeListener fullMapStateDataChangedListener = event -> {
		Object data = event.getSource();
		// Object newValue = event.getNewValue();

		if (data instanceof ClientData) {
			ClientData castedData = (ClientData) data;

			int i = 0;
			if (!VisitedFields.is20x5) {

				for (FullMapNode node : castedData.getFullMap()) {
					if (VisitedFields.enemyFortIsKnown
							&& SmallCalculationClass.nodesAreEqual(node, VisitedFields.enemyFortPos)) {
						System.out.print("[@ENEMYFORT] ");
					} else if (VisitedFields.myTreasureisKnowButNotPickedUpYet
							&& SmallCalculationClass.nodesAreEqual(node, VisitedFields.myTreasurePos)) {
						System.out.print("[@TREASURE ] ");
					} else if (node.getFortState() == EFortState.MyFortPresent) {
						System.out.print("[  @FORT   ] ");
					} else if (SmallCalculationClass.nodesAreEqual(node, FindTargetNode.targetNode)) {
						System.out.print("[ @Target  ] ");
					} else {
						map10x10(node);
					}
					if (i == 9) {
						i = 0;
						System.out.println();
					} else
						i++;
				}
				System.out.println();

			}

			else {

				for (FullMapNode node : castedData.getFullMap()) {
					if (VisitedFields.enemyFortIsKnown
							&& SmallCalculationClass.nodesAreEqual(node, VisitedFields.enemyFortPos)) {
						System.out.print("[@EFORT]");
					} else if (VisitedFields.myTreasureisKnowButNotPickedUpYet
							&& SmallCalculationClass.nodesAreEqual(node, VisitedFields.myTreasurePos)) {
						System.out.print("[@MONEY]");
					} else if (node.getFortState() == EFortState.MyFortPresent) {
						System.out.print("[@FORT ]");
					} else if (SmallCalculationClass.nodesAreEqual(node, FindTargetNode.targetNode)) {
						System.out.print("[@Targe]");
					} else {
						map20x5(node);
					}

					if (i == 19) {
						i = 0;
						System.out.println();
					} else
						i++;
				}
				System.out.println();

			}

		}
	};

}
