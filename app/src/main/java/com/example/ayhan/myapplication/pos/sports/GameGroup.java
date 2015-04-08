package com.example.ayhan.myapplication.pos.sports;

import java.util.ArrayList;
import java.util.List;

public class GameGroup {

	private Long mGroupID = -3L;
	private String mGroupName = "";
	private int GameGroupCount;
	private boolean isMainGroup;
	private List<Game> games = new ArrayList<Game>();
	private boolean isLocked = false;

	public GameGroup() {}

	public GameGroup(String name, Long id) {
		this.mGroupName =  name;
		this.mGroupID = id;
	}

	public Long getGroupID() {
		return mGroupID;
	}

	public void setGroupID(Long id) {
		this.mGroupID = id;
	}
	
	public String getGroupName() {
		return mGroupName;
	}

	public void setGroupName(String name) {
		this.mGroupName = name;
	}

	public List<Game> getListGames() {
		return games;
	}

	public void addGame(Game game) {
		games.add(game);
	}

	public boolean isLocked() {
		return isLocked;
	}

	public void setLocked(boolean isBlocked) {
		this.isLocked = isBlocked;
	}

    public int getGameGroupCount() {
        return GameGroupCount;
    }

    public void setGameGroupCount(int gameGroupCount) {
        GameGroupCount = gameGroupCount;
    }
    
    public boolean isMainGroup() {
        return isMainGroup;
    }

    public void setIsMainGroup(boolean isMainGroup) {
        this.isMainGroup = isMainGroup;
    }

}
