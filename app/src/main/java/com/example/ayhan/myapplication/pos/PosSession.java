package com.example.ayhan.myapplication.pos;

import android.util.Log;

public class PosSession  implements IPosSession{
    private static final String TAG = "PosSession";

    private PosToken sessionToken;
    private PosToken userToken;
    private String ssoToken;

    public PosSession clone(){
        return new PosSession(this);
    }

    @Override
    public PosToken getSessionToken() {
        return sessionToken;
    }

    @Override
    public PosToken getUserToken() {
        return userToken;
    }

    public void setSsoTokenString(String ssoToken) {
        this.ssoToken = ssoToken;
    }

    public String getSsoTokenString() {
        if (null == this.ssoToken) {
            return "";
        }
        return this.ssoToken;
    }

    public boolean isValid() {
        if (sessionToken != null && userToken != null) {
            return (sessionToken.isValid() && userToken.isValid());
        } else {
            return false;
        }
    }

    @Override
    public boolean isValidSessionToken() {
        return sessionToken != null && sessionToken.isValid();
    }

    @Override
    public boolean isValidUserToken() {
        return userToken != null && userToken.isValid();
    }

    public void updateTokens(PosToken userToken) {
        this.userToken = userToken;
    }

    public void updateTokens(String sessionTokenString, String userTokenString) {
        int validityTime = 10;
        this.sessionToken = new PosToken(sessionTokenString, validityTime);
        this.userToken = new PosToken(userTokenString, 24 * 60);

        if (MODE.D) {
            Log.d(TAG, String.format("The session token: %s", sessionToken.getValue()));
            Log.d(TAG, String.format("The user token: %s", userToken.getValue()));
        }
    }

    @Override
    public void updateSessionToken(String tokenValue) {
        if (this.sessionToken != null) {
            this.sessionToken.update(tokenValue);
        } else {
            int validityTime = 10;
            this.sessionToken = new PosToken(tokenValue, validityTime);
        }
    }

    public synchronized void clear() {
        if (sessionToken != null) {
            sessionToken.invalidate();
        }
        if (userToken != null) {
            userToken.invalidate();
        }
        ssoToken = null;
    }

    public PosSession(){
    }

    public PosSession(PosSession posSession){
        this.sessionToken = new PosToken(posSession.sessionToken);
        this.userToken = new PosToken(posSession.userToken);
        this.ssoToken = posSession.ssoToken;
    }
}
