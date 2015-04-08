package com.example.ayhan.myapplication.pos.sports;


public class CountriesContent implements AbstractContent {

    private static String mName;
    private static Long mID;

    static {

        mID = Long.valueOf(-1L);
    }

    @Override
    public void setId(Long id) {
    }

    @Override
    public Long getId() {
        return mID;
    }

    @Override
    public void setName(String name) {
    }

    @Override
    public String getName() {
        return mName;
    }

    @Override
    public void setLive(boolean isLive) {
    }

    @Override
    public boolean isLive() {
        return false;
    }

    @Override
    public void setToday(boolean isToday) {
    }

    @Override
    public boolean isToday() {
        return false;
    }


}

