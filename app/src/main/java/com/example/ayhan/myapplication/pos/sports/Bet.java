package com.example.ayhan.myapplication.pos.sports;



import java.util.UUID;

public interface Bet extends Comparable<Bet> {

	public Long getId();

	public Long getEventID();

	public String getEventName();

	public Long getLeagueID();

	public String getLeagueName();

	public Long getMarketID();

	public String getMarketName();

	public Double getOdds();

	public Long getOptionID();

	public String getOptionName();

	public String getOutcome();

	public Long getSportID();

	public String getState();

	public Double getBetStake();

	public Boolean isLive();

	public void setOdds(Double odds);


	public String getResultSignature();

	public Event getEvent();
	
	public UUID getRequestID();
	
	public void setRequestID(UUID requestID);

	public boolean isOnline();

    public boolean isFinished();

	public void setOnline(boolean isOnline);

	public boolean isFromFavourite();

	public void setFromFavourite(boolean isFromFavourite);

	public void setBetStake(Double stake);

    public boolean isCustomStake();

    public void setCustomStake(boolean isCustomStake);
}
