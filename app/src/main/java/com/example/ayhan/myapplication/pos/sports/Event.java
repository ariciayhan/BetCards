package com.example.ayhan.myapplication.pos.sports;

import android.text.format.DateUtils;
import android.text.format.Time;


import com.example.ayhan.myapplication.pos.sports.AbstractContent;
import com.example.ayhan.myapplication.pos.sports.BetSearchEntity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Event  {

    private Long sportId;
    private Long leagueId;
    private String leagueName;
    private Long reagionId;
    private String regionName;
    private ArrayList<GameGroup> mGroups = new ArrayList<GameGroup>();

    private Game mTopGame;
    private Long mMainMarketId;
    private boolean mBShowMarketNameOnly = false;

    private int marketsCount = 0;

    private Long id;
    private String partnerId;
    private boolean isToday = false;
    private boolean isGlobalTop = false;

    private String sportName;
    private String errorState;


    private String name;
    private String shortName;
    private Time startTime;
    private Time serverTimeNow;
    private Boolean started = null;
    private boolean isLive;

    private String videoId;
    private boolean isVideoAvailable = false;
    private boolean isTop = false;
    private boolean isFinished = false;
    private String videoPublished = null;
    private String videoProvider = null;
    private boolean isVideoStartPlayAutomatically;
    private boolean containsEventIdOnly;
    private int mNumberInSection;
    private Sport mSport;

    private static int MAX_RESULT_NAME = 17;
    private static int MAX_MARKET_NAME = 60;
    private static int MAX_MARKET_COUNT = 3;

    private String noOddsAvailableText;

    public Event() {}

    public Event(long id, String parthnerId){
        this();
        this.id = id;
        this.partnerId = parthnerId;
    }

    private List<String> participants = new ArrayList<String>();

    public void addParticipant(String participant) {
        this.participants.add(participant);
    }

    public boolean isMBShowMakenNameOnly(){
        return mBShowMarketNameOnly;
    }



    public List<String> getParticipantsList() {
        return this.participants;
    }

    public String[] getParticipants() {
        return this.participants.toArray(new String[] {});
    }

    public void addGame(Game game) {
        GameGroup gg = getGameGroup(game);
        gg.addGame(game);
    }



    private GameGroup getGameGroup(Game game) {
        Long id = game.getMarketGroupID();
        GameGroup gameGroup = getGameGroupByID(id);
        if (gameGroup == null) {
            String name = game.getMarketGroupName();

            gameGroup = new GameGroup(name, id);
            gameGroup.setIsMainGroup(id.equals(game.getEvent().getMainMarketId()));
            addGameGroup(gameGroup);
        }
        return gameGroup;
    }


    public void addGameGroup(GameGroup gameGroup) {
        mGroups.add(gameGroup);
    }

    public GameGroup getMainGroup() {
        for (GameGroup gameGroup : getGameGroups()) {
            if (gameGroup.isMainGroup()) {
                return gameGroup;
            }
        }
        return getGameGroups().isEmpty() ? null : getGameGroups().get(0);
    }



    public Collection<Game> getGames() {
        ArrayList<Game> games = new ArrayList<Game>();
        for (GameGroup group : mGroups) {
            games.addAll(group.getListGames());
        }
        return games;
    }

    public ArrayList<GameGroup> getGameGroups() {
        return mGroups;
    }

    public GameGroup getGameGroupByID(Long id) {
        for (GameGroup gameGroup : mGroups) {
            if (gameGroup.getGroupID().equals(id)) {
                return gameGroup;
            }
        }
        return null;
    }



    public void setId(Long id) {
        this.id = id;
    }


    public Long getId() {
        return id;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public String getPartnerId() {
        return partnerId;
    }


    public void setSportId(Long sportId) {
        this.sportId = sportId;
    }

    public Long getSportId() {
        return sportId;
    }

    public void setLeagueId(Long leagueId) {
        this.leagueId = leagueId;
    }

    public Long getLeagueId() {
        return leagueId;
    }

    public void setReagionId(Long reagionId) {
        this.reagionId = reagionId;
    }

    public Long getReagionId() {
        return reagionId;
    }


    public void setName(String name) {
        this.name = name;
    }


    public String getName() {
        return name;
    }

    public void setEventShortName(String name) {
        this.shortName = name;
    }

    public String getEventShortName() {
        return shortName;
    }

    public Time getStartTime() {
        return this.startTime;
    }

    public void setStartTime(Time eventStartedTime) {
        this.startTime = eventStartedTime;
    }

    public Time getServerTime() {
        return serverTimeNow;
    }

    public void setServerTime(Time serverTime) {
        this.serverTimeNow = serverTime;
    }

    public void setErrorState(String errorState) {
        this.errorState = errorState;
    }

    public String getErrorState() {
        return errorState;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }

    public boolean isStarted() {
        if(startTime != null && serverTimeNow != null) {
            return startTime.before(serverTimeNow);
        }
        return started != null ? started : false;
    }

    public String toString() {
        return this.getEventShortName();
    }

    public void setRegionName(String region) {
        this.regionName = region;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setSportName(String sport) {
        this.sportName = sport;
    }

    public String getSportName() {
        return sportName;
    }

    public void setLive(boolean isLive) {
        this.isLive = isLive;
    }


    public boolean isLive() {
        return isLive;
    }

    public void setLeagueName(String leagueName) {
        this.leagueName = leagueName;
    }

    public String getLeagueName() {
        return leagueName;
    }


    public void setToday(boolean isToday) {
        this.isToday = isToday;
    }


    public boolean isToday() {
        return isToday;
    }

    public boolean isEventStartsToday() {
        return DateUtils.isToday(getStartTime().toMillis(false));
    }


    public boolean isGlobalTop() {
        return isGlobalTop;
    }

    public void setGlobalTop(boolean isGlobalTop) {
        this.isGlobalTop = isGlobalTop;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public boolean isTop() {
        return isTop;
    }

    public void setTop(boolean isTop) {
        this.isTop = isTop;
    }

    public boolean isFinished(){
        return isFinished;
    }

    public void setFinished(boolean isFinished){
        this.isFinished = isFinished;
    }

    public boolean isVideoAvailable() {
        return isVideoAvailable;
    }

    public void setVideoAvailable(boolean isVideoAvailable) {
        this.isVideoAvailable = isVideoAvailable;
    }

    public Game getTopGame() {
        return mTopGame;
    }


    public int getMarketsCount() {
        return marketsCount;
    }

    public void setMarketsCount(int marketsCount) {
        this.marketsCount = marketsCount;
    }

    public String getVideoPublished() {
        return videoPublished;
    }

    public void setVideoPublished(String videoPublished) {
        this.videoPublished = videoPublished;
    }

    public String getVideoProvider() {
        return videoProvider;
    }

    public void setVideoProvider(String videoProvider) {
        this.videoProvider = videoProvider;
    }

    public boolean containsEventIdOnly(){
        return containsEventIdOnly;
    }

    public void setContainsEventIdOnly(boolean containsEventIdOnly){
        this.containsEventIdOnly = containsEventIdOnly;
    }


    public String getNoOddsAvailableText() {
        return noOddsAvailableText;
    }

    public void setNoOddsAvailableText(String noOddsAvailableText) {
        this.noOddsAvailableText = noOddsAvailableText;
    }

    public int getNumberInSection() {
        return mNumberInSection;
    }

    public void setNumberInSection(int mNumberInSection) {
        this.mNumberInSection = mNumberInSection;
    }

    public Long getMainMarketId() {
        return mMainMarketId;
    }

    public void setMainMarketId(Long mainMarketId) {
        this.mMainMarketId = mainMarketId;
    }


    public Sport getSport() {
        return mSport;
    }

    public void setSport(Sport sport) {
        this.mSport = sport;
    }

    public boolean isVideoStartPlayAutomatically() {
        return isVideoStartPlayAutomatically;
    }

    public void setVideoStartPlayAutomatically(boolean isVideoStartPlayAutomatically) {
        this.isVideoStartPlayAutomatically = isVideoStartPlayAutomatically;
    }

}
