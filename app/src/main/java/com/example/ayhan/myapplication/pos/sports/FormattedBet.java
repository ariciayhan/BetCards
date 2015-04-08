package com.example.ayhan.myapplication.pos.sports;

public interface FormattedBet extends Bet{
	
	public String getFormattedOdds();
	
	public String getFractionalOdds();
	
	public void setFractionalOdds(String fractionalOdds);
	
	public void setUsOdds(String usOdds);
	
	public String getUsOdds();
}
