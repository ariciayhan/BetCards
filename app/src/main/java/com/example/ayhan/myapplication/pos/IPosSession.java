package com.example.ayhan.myapplication.pos;


public interface IPosSession {

    public PosToken getSessionToken();

    public PosToken getUserToken();

    public boolean isValid();

    public boolean isValidSessionToken();

    public boolean isValidUserToken();

    public void updateSessionToken(String tokenValue);
}
