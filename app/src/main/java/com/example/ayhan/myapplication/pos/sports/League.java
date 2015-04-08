package com.example.ayhan.myapplication.pos.sports;



/** @author benjamin ferrari */
public class League implements Comparable<League>, AbstractContent {

	private Long id;
	private Long sportId;
	private String name;
	private String fontIcon;
	private String regionName;
	private Long regionId;
	private String sportName;
	private int numberOfEvents;
	private int videoCount = 0;
	private boolean isTop;
	private boolean isToday = false; 
	private boolean isLive = false;




	public void setRegionName(String region) {
		this.regionName = region;
	}

	public String getRegionName() {
		return regionName;
	}
	
	public Long getRegionId(){
		return regionId;
	}
	
	public void setRegionId(Long id){
		this.regionId = id;
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
	public int compareTo(League other) {
		return this.name.compareTo(other.name);
	}

	public String toString() {
		return this.name;
	}

	public void setSportId(Long sportId) {
		this.sportId = sportId;
	}

	public Long getSportId() {
		return this.sportId;
	}

	public void setSportName(String name) {
		sportName = name;
	}

	public String getSportName() {
		return sportName;
	}
	
	public void setNumberOfEvents(int numberOfEvents) {
		this.numberOfEvents = numberOfEvents;
	}

	public int getNumberOfEvents() {
		return numberOfEvents;
	}



	
	public void setTop(boolean isTop) {
		this.isTop = isTop;
	}

	public boolean isTop() {
		return isTop;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public Long getId() {
		return id;
	}

	public void setLive(boolean live) {
		this.isLive = live;
	}

	@Override
	public boolean isLive() {
		return isLive;
	}

	@Override
	public void setToday(boolean isToday) {
		this.isToday = isToday;
	}

	@Override
	public boolean isToday() {
		return isToday;
	}

    public int getVideoCount() {
        return videoCount;
    }

    public void setVideoCount(int videoCount) {
        this.videoCount = videoCount;
    }

    public boolean isContainEvents() {
        return numberOfEvents != 0;
    }

}
