package com.example.ayhan.myapplication.pos;


import com.example.ayhan.myapplication.pos.sports.Event;
import com.example.ayhan.myapplication.pos.sports.jackson.ResponseParserEvents;
import com.example.ayhan.myapplication.util.HttpHelper;

import org.json.JSONException;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

/**
 * Created by aariciogullari on 08.04.2015.
 */
public class BetSearcher {
    public static final String BETSEARCHURL = "http://en.mobileapi.bwinlabs.com/api/iphone/v2/events/main?partnerid=test&winninggames=true&sportid=4&topsport=true&page_size=30";

    public List<Event> SearchBets(){

                HashMap headers = new HashMap<String, String>();

            headers.put("Accept-Language","en");
            headers.put("Accept", HttpHelper.HTTP_CONTENT_TYPE_JSON);
        ResponseParserEvents rp = new ResponseParserEvents();
        HttpHelper.instance(). performGet(BETSEARCHURL, headers,null, rp);
        return rp.getEvents();
    }
}
