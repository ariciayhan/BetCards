package com.example.ayhan.myapplication.pos.sports;

import java.util.List;

public class BetSearchData {
    private int liveNowCount = 0;
    private int VideoCount;
    private GroupCounters filters;
    private List<? extends AbstractContent> mainData;
    private List<Event> highlights;


    

    public BetSearchData() {
        this.setFilters(new GroupCounters());
    }

    public int getLiveNowCount() {
        return liveNowCount;
    }

    public void setLiveNowCount(int liveNowCount) {
        this.liveNowCount = liveNowCount;
    }

    public GroupCounters getFilters() {
        return filters;
    }

    public void setFilters(GroupCounters filters) {
        this.filters = filters;
    }

    public List<? extends AbstractContent> getMainData() {
        return mainData;
    }

    public void setMainData(List<? extends AbstractContent> mainData) {
        this.mainData = mainData;

    }

    public List<Event> getHighlights() {
        return highlights;
    }

    public void setHighlights(List<Event> highlights) {
        this.highlights = highlights;
    }





    public int getVideoCount() {
        return VideoCount;
    }

    public void setVideoCount(int videoCount) {
        VideoCount = videoCount;
    }

    public static class GroupCounters {
        public int live;
        public int top;
        public int soon;
        public int today;
        public int all;

        public boolean isEmpty() {
            return live + top + soon + today + all <= 0;
        }
    }

}
