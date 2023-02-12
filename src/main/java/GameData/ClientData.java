package GameData;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

import messagesBase.UniquePlayerIdentifier;
import messagesBase.messagesFromClient.PlayerHalfMapNode;
import messagesBase.messagesFromServer.EPlayerGameState;
import messagesBase.messagesFromServer.FullMapNode;

public class ClientData {
	private final PropertyChangeSupport clientDataChanges = new PropertyChangeSupport(this);
	private final PropertyChangeSupport mapDataChanges = new PropertyChangeSupport(this);
	private final PropertyChangeSupport ePlayerStateDataChanges = new PropertyChangeSupport(this);
	private final PropertyChangeSupport currPosStateDataChanges = new PropertyChangeSupport(this);
	private final PropertyChangeSupport fullMapChanges = new PropertyChangeSupport(this);
	// private ClientCoordinator coordinator;
	UniquePlayerIdentifier uniqueID = new UniquePlayerIdentifier();
	static public String playerID = "";
	List<PlayerHalfMapNode> halfMap = new ArrayList<PlayerHalfMapNode>();
	List<FullMapNode> fullMap = new ArrayList<FullMapNode>();
	EPlayerGameState ePlayerGameState = EPlayerGameState.MustWait;
	FullMapNode currPos = new FullMapNode();

	public List<FullMapNode> getFullMap() {
		return this.fullMap;
	}

	public FullMapNode getCurrPos() {
		return this.currPos;
	}

	public EPlayerGameState getEPlayerGameState() {
		return this.ePlayerGameState;
	}

	public void setFullMap(List<FullMapNode> fullMap) {
		this.fullMap = fullMap;
	}

	public void setEPlayGameState(EPlayerGameState ePlayerGameState) {
		EPlayerGameState oldVal = this.ePlayerGameState;
		this.ePlayerGameState = ePlayerGameState;
		ePlayerStateDataChanges.firePropertyChange("halfMsap", oldVal, this.ePlayerGameState);
	}

	public String getPlayerID() {
		return playerID;
	}

	public UniquePlayerIdentifier getUniqueID() {
		return this.uniqueID;

	}

	public void setHalfMap(List<PlayerHalfMapNode> halfMap) {
		List<PlayerHalfMapNode> oldVal = this.halfMap;
		this.halfMap = halfMap;
		mapDataChanges.firePropertyChange("halfMap", oldVal, this.halfMap);
	}

	public List<PlayerHalfMapNode> getHalfMap() {
		return this.halfMap;
	}

	public void setPlayerID(String playerID) {
		String oldValue = playerID;
		ClientData.playerID = playerID;
		clientDataChanges.firePropertyChange(playerID, oldValue, ClientData.playerID);
	}

	public void setCurrPos(FullMapNode currPos) {

		FullMapNode oldPos = this.currPos;
		this.currPos = currPos;
		fullMapChanges.firePropertyChange(playerID, oldPos, this.currPos);
	}

	public void setPlayerUniqueID(UniquePlayerIdentifier playerID) {

		this.uniqueID = playerID;
	}

	/////////////////////////////////////////////
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		clientDataChanges.addPropertyChangeListener(listener);

	}

	public void addPropertyChangeListenerMap(PropertyChangeListener listener) {
		mapDataChanges.addPropertyChangeListener(listener);
	}

	public void addPropertyChangeListenerPlayerState(PropertyChangeListener listener) {

		ePlayerStateDataChanges.addPropertyChangeListener(listener);
	}

	public void addPropertyChangeListenerPlayerFullMap(PropertyChangeListener listener) {
		fullMapChanges.addPropertyChangeListener(listener);
	}

	public void addPropertyChangeListenerCurrPos(PropertyChangeListener listener) {
		currPosStateDataChanges.addPropertyChangeListener(listener);

	}
}
