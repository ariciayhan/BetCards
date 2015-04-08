package com.example.ayhan.myapplication.pos.sports;

import java.util.ArrayList;
import java.util.List;

/** @author benjamin ferrari */
public class Game {

	private Long id;
	private String name;
	private boolean isOnline;
	private List<GameResult> results = new ArrayList<GameResult>();
	private GameResult[] EMPTY_RESULT = new GameResult[] {};
	private Event event;

	private Long marketGroupID;
	private String marketGroupName;
	private int marketTemplateId;
	private boolean isExtendedOffer;

	private boolean is23Way = false;
	private boolean isWinning = false;

	public void addResult(GameResult result) {
		results.add(result);
		if (!result.getGame().equals(this)) {
			result.setGame(this);
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Game other = (Game) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public GameResult[] getResults() {
		return results.toArray(EMPTY_RESULT);
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setOnline(boolean isOnline) {
		this.isOnline = isOnline;
	}

	public boolean isOnline() {
		return isOnline;
	}

	public String toString() {
		return name;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	public Event getEvent() {
		return this.event;
	}



	public Long getMarketGroupID() {
		return marketGroupID;
	}

	public void setMarketGroupID(Long marketGroupID) {
		this.marketGroupID = marketGroupID;
	}

	public String getMarketGroupName() {
		return marketGroupName;
	}

	public void setMarketGroupName(String marketGroupName) {
		this.marketGroupName = marketGroupName;
	}

	public int getMarketTemplateId() {
		return marketTemplateId;
	}

	public void setMarketTemplateId(int marketTemplateId) {
		this.marketTemplateId = marketTemplateId;
	}
	
	public List<GameResult> getResultsList() {
		return results;
	}
	
	public void set23Way(boolean is23Way){
	    this.is23Way = is23Way;
	}
	
	public boolean is23Way(){
	    return is23Way;
	}

	public boolean isWinning() {
		return isWinning;
	}

	public void setWinning(boolean isWinning) {
		this.isWinning = isWinning;
	}
	
	public boolean isAllResultsLocked(){
		for (GameResult result: results){
			if (result.isOnline()){
				return false;
			}
		}
		return true;
	}


    public boolean isExtendedOffer() {
        return isExtendedOffer;
    }

    public void setExtendedOffer(boolean isExtendedOffer) {
        this.isExtendedOffer = isExtendedOffer;
    }
}
