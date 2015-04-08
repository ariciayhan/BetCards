package com.example.ayhan.myapplication.pos.sports;

public class Region implements AbstractContent {

	private Sport mSport;
	private Long id;
	private String name;
	private int eventsCount;
    private boolean isTop;


	public Region(Sport sport) {
		mSport = sport;
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
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public boolean isLive() {
		return mSport.isLive();
	}

	@Override
	public boolean isToday() {
		return mSport.isToday();
	}

	public int getEventsCount() {
		return eventsCount;
	}

	public void setEventsCount(int eventsCount) {
		this.eventsCount = eventsCount;
	}

	public Long getSportID() {
		return mSport.getId();
	}

	public String getSportName() {
		return mSport.getName();
	}

	public boolean isTop() {
		return isTop;
	}

    public void setTop(boolean top) {
        this.isTop = top;
    }
    

	@Override
	public void setLive(boolean isLive) {
	}

	@Override
	public void setToday(boolean isToday) {
	}
}
