package com.example.ayhan.myapplication.pos;

/**
 * Created by ayhan on 04/04/15.
 */

        import java.sql.Date;
        import java.text.SimpleDateFormat;

        import android.util.Log;

public class PosToken {

    private String value;
    private int validTillMinutes;
    private Date validTill;

    public PosToken(PosToken posToken){
        this.value = posToken.value;
        this.validTillMinutes = posToken.validTillMinutes;
        this.validTill = posToken.validTill;
    }

    public PosToken(String value, int validTill) {
        this.value = value;
        this.validTillMinutes = validTill;
        this.validTill = new Date(System.currentTimeMillis() + validTillMinutes
                * 60 * 1000);
    }

    private java.text.DateFormat sdf = SimpleDateFormat.getDateTimeInstance(
            SimpleDateFormat.LONG, SimpleDateFormat.LONG);

    public synchronized boolean isValid() {
        if (value != null && value.length() > 0 && validTill != null) {
            if (validTill.after(new Date(System.currentTimeMillis()))) {
                if (MODE.D) {
                    Log.d(PosToken.class.getName(), String.format(
                            "Returning true, date now is %s date till is %s",
                            sdf.format(new Date(System.currentTimeMillis())),
                            sdf.format(this.validTill)));
                }
                return true;
            } else {
                if (MODE.D) {
                    Log.d(PosToken.class.getName(),
                            "time has run out for this token");
                }
                return false;
            }
        } else {
            if (MODE.D) {
                Log.d(PosToken.class.getName(),
                        "Value is null or the length is <= 0");
            }
            return false;
        }
    }

    public String getValue() {
        return value;
    }

    public void invalidate() {
        this.value = null;
        this.validTill = null;
    }

    public void update(String value) {
        this.value = value;
        this.validTill = new Date(System.currentTimeMillis() + validTillMinutes
                * 60 * 1000);
    }

}
