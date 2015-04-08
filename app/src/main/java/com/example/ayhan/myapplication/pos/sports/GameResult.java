package com.example.ayhan.myapplication.pos.sports;



import java.text.NumberFormat;
import java.util.UUID;

/** @author benjamin ferrari */
public class GameResult implements FormattedBet {

	private Game game;
	private Long id;
	private String name;
	private Double odds;
	private Double stake = 0D;
	private String fractionalOdds;
	private String usOdds;
	private String signature;
	private boolean online;
	private UUID requestID;
	private boolean isFromFav;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}

		if (!(obj instanceof Bet)) {
			return false;
		}
		Bet other = (Bet) obj;
		if (id == null) {
			if (other.getId() != null) {
				return false;
			}
		} else if (!id.equals(other.getId())) {
			return false;
		}
		return true;
	}

	public String getUsOdds() {
		return usOdds;
	}

	public void setUsOdds(String usodds) {
		this.usOdds = usodds;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public Game getGame() {
		return game;
	}

	public String toString() {
		return name;
	}

	public void setOdds(Double odds) {
		this.odds = odds;
	}

	public Double getOdds() {
		return odds;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setFractionalOdds(String frodds) {
		this.fractionalOdds = frodds;
	}

	public String getFractionalOdds() {
		return this.fractionalOdds;
	}

	@Override
	public Long getEventID() {
		return getGame().getEvent().getId();
	}

	@Override
	public String getEventName() {
		return getGame().getEvent().getName();
	}

	@Override
	public Long getLeagueID() {
		return getGame().getEvent().getLeagueId();
	}

	@Override
	public String getLeagueName() {
		return getGame().getEvent().getLeagueName();
	}

	@Override
	public Long getMarketID() {
		return getGame().getId();
	}

	@Override
	public String getMarketName() {
		return getGame().getName();
	}

	@Override
	public Long getOptionID() {
		return this.getId();
	}

	@Override
	public String getOptionName() {
		return this.getName();
	}

	@Override
	public String getOutcome() {
		return null;
	}

	@Override
	public Long getSportID() {
		return this.getGame().getEvent().getSportId();
	}

	@Override
	public String getState() {
		return null;
	}

	@Override
	public int compareTo(Bet other) {
		return this.getOptionName().compareTo(other.getOptionName());
	}

	@Override
	public Boolean isLive() {
		return game.getEvent().isLive();
	}


	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getSignature() {
		return this.signature;
	}

	@Override
	public String getResultSignature() {
		return this.signature;
	}

	@Override
	public Event getEvent() {
		return this.getGame().getEvent();
	}

	public void setOnline(boolean online) {
		this.online = online;
	}

	public boolean isOnline() {
		return this.online && this.getGame().isOnline();
	}
	
	@Override
    public boolean isFinished() {
        return false;
    }

    @Override
	public UUID getRequestID() {
		return requestID;
	}

	@Override
	public void setRequestID(UUID requestID) {
		this.requestID = requestID;
	}
	
	public String getFormattedOdds() {

		return getOdds().toString();
	}

	@Override
	public boolean isFromFavourite() {
		return isFromFav;
	}

	@Override
	public void setFromFavourite(boolean isFromFav) {
		this.isFromFav = isFromFav;
	}

	@Override
	public Double getBetStake() {
		return stake;
	}

	@Override
	public void setBetStake(Double stake) {
		this.stake = stake;
	}

    @Override
    public boolean isCustomStake() {
        return false;
    }

    @Override
    public void setCustomStake(boolean isCustomStake) {
        //gag
    }

}
