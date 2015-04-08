package com.example.ayhan.myapplication.pos.sports.jackson;

import android.text.format.Time;
import android.util.Log;

import com.example.ayhan.myapplication.pos.sports.*;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

import org.json.JSONException;

import java.io.InputStream;

public class ResponseParserJackson implements ResponseParser {
	private static final String TAG = ResponseParserJackson.class.getName();
	static final String RESPONSE = "response";
	static final String HEADER = "header";
	static final String ITEMS = "items";
	static final String EVENTS_COUNT = "events_count";
	static final String SERVER_TIME = "server_time";

	static final String OVERVIEW = "overview";
	static final String EVENTS = "events";
	static final String SPORTS = "sports";
	static final String ID = "id";
	static final String NAME = "name";
	static final String REGION = "region";
	static final String TIME = "time";
	static final String PERIOD = "period";
	static final String ONLINE = "online";
	static final String NUMBER_OF_EVENTS = "number_of_events";
	static final String LOCATION = "location";
	static final String FAVORITES = "favorites";
	static final String FAVORITE = "favorite";
	static final String LIVE = "live";
	static final String DATA = "data";
	static final String LIST = "list";

	static Time newTimeObject() {
		return new Time("UTC");
	}

	static boolean isTimeObjectEmpty(Time time) {
		return time.year == 1970;
	}

	static Time parseTime(Time time, String startsAtStr) {
		time.parse3339(startsAtStr + "Z");
		return time;
	}

	public void parse(InputStream is) throws JSONException {
		try {
			JsonFactory factory = new JsonFactory();
			JsonParser jp = factory.createJsonParser(is);
			JsonToken current;

			current = jp.nextToken();

			if (current != JsonToken.START_OBJECT) {
				throw new JSONException("Error: root should be object.");
			}
			String fieldName;
			while (jp.nextToken() != JsonToken.END_OBJECT) {
				fieldName = jp.getCurrentName();
				current = jp.nextToken();
				if (fieldName.equals(RESPONSE)) {
					while (jp.nextToken() != JsonToken.END_OBJECT) {
						fieldName = jp.getCurrentName();
						current = jp.nextToken();
						if (fieldName.equals(ITEMS)) {
							onResponseItems(jp);
						} else if (fieldName.equals(HEADER)) {
							while (jp.nextToken() != JsonToken.END_OBJECT) {
								fieldName = jp.getCurrentName();
								current = jp.nextToken();
								if (fieldName.equals(EVENTS_COUNT)) {
									onHeaderEventsCount(jp);
								} else if (fieldName.equals("page_size")) {
									onHeaderPageSize(jp);
								} else if (fieldName.equals("page_number")) {
									onHeaderPageNumber(jp);
								} else if (fieldName.equals(SERVER_TIME)) {
									onHeaderServerTime(jp);
								} else {
									jp.skipChildren();
								}
							}
						} else {
							jp.skipChildren();
						}
					}
				} else {
					jp.skipChildren();
				}
			}
			onEndParse();
		} catch (Exception e) {

				Log.d(TAG, e.getMessage(), e);

			throw new JSONException("Error:" + e);
		} finally {
			try {
				is.close();
			} catch (Exception ex) {
			}
		}
	}

	protected void onResponseItems(JsonParser jp) throws Exception {
		jp.skipChildren();
	}

	protected void onHeaderServerTime(JsonParser jp) throws Exception {
	}

	protected void onHeaderEventsCount(JsonParser jp) throws Exception {
	}

	protected void onHeaderPageSize(JsonParser jp) throws Exception {
	}

	protected void onHeaderPageNumber(JsonParser jp) throws Exception {
	}

	protected void onEndParse() {
	}



}
