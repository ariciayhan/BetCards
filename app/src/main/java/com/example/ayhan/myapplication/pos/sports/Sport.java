package com.example.ayhan.myapplication.pos.sports;


import java.util.ArrayList;
import java.util.List;

/** @author benjamin ferrari */
public class Sport implements Comparable<Sport>, AbstractContent {
	protected Long id;
	protected String name;
	protected Integer numEvents;
	private List<League> leagues = new ArrayList<League>();
	private List<Region> regions = new ArrayList<Region>();
	
	private boolean isTop;
	private boolean isLive;
	private boolean isToday = false;


	/**
     * need for showing live now events for region
     */
    private String regionId;

	public Sport(Long id, String name, boolean isLive) {
		this(id, name);
		this.isLive = isLive;
	}

	public Sport(Long id, String name, int numEvents) {
		this(id, name);
		this.numEvents = numEvents;
	}

	public Sport(Long id, String name) {
		this.name = name;
		this.id = id;
	}

	public String toString() {
		return new StringBuilder(name).append(" (").append(numEvents).append(")").toString();
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public Long getId() {
		return id;
	}
	
	@Override
	public boolean isToday(){
		return isToday;
	}
	
	public void setToday(boolean value){
		isToday = value;
	}
	


	public Integer getNumEvents() {
		return this.numEvents;
	}

	@Override
	public int compareTo(Sport other) {
		return this.id.compareTo(other.id);
	}

	public void setLeagues(List<League> leagues) {
		this.leagues = leagues;
	}

	public List<League> getLeagues() {
		return leagues;
	}

	public void setTop(boolean isTop) {
		this.isTop = isTop;
	}

	public boolean isTop() {
		return isTop;
	}

	public void setLive(boolean isLive) {
		this.isLive = isLive;
	}

	@Override
	public boolean isLive() {
		return isLive;
	}



    public List<Region> getRegions() {
        return regions;
    }

    public void setRegions(List<Region> regions) {
        this.regions = regions;
    }

    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }
}
