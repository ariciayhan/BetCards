package com.example.ayhan.myapplication.pos.sports;

import java.util.ArrayList;
import java.util.List;

public class BetSearchResponseData {


	private List<Event> highlightsEvents = new ArrayList<Event>();
	private int videoCount;
	private List<Sport> sports = new ArrayList<Sport>();
	private List<Region> regions = new ArrayList<Region>();
	private List<League> leagues = new ArrayList<League>();
	private List<League> topLeagues = new ArrayList<League>();
	private List<Event> events = new ArrayList<Event>();



	public List<Event> getHighlightsEvents() {
		return highlightsEvents;
	}

	public void setHighlightsEvents(List<Event> highlightsEvents) {
		this.highlightsEvents = highlightsEvents;
	}

	public List<Sport> getSports() {
		return sports;
	}

	public void setSports(List<Sport> sports) {
		this.sports = sports;
	}

	public List<Region> getRegions() {
		return regions;
	}

	public void setRegions(List<Region> regions) {
		this.regions = regions;
	}

	public List<League> getLeagues() {
		return leagues;
	}

	public void setLeagues(List<League> leagues) {
		this.leagues = leagues;
	}

	public List<Event> getEvents() {
		return events;
	}

	public void setEvents(List<Event> events) {
		this.events = events;
	}

    public List<League> getTopLeagues() {
        return topLeagues;
    }

    public void setTopLeagues(List<League> topLeagues) {
        this.topLeagues = topLeagues;
    }

    public int getVideoCount() {
        return videoCount;
    }

    public void setVideoCount(int videoCount) {
        this.videoCount = videoCount;
    }

}
