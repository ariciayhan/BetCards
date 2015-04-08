package com.example.ayhan.myapplication.pos.sports.jackson;

import android.content.Context;
import android.text.format.Time;
import android.util.SparseArray;

import com.example.ayhan.myapplication.pos.sports.*;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressWarnings("incomplete-switch")
public class ResponseParserEvents extends ResponseParserJackson {

	protected final List<Event> events = new ArrayList<Event>();
	protected final Time serverTime = newTimeObject();
	protected int mEeventsCount = -1;
	protected int mPageSize = -1;
	protected int mPageNumber = -1;
    private boolean mIsSingelEvent;
    
    private static boolean isFinishedEvent = false;

	public ResponseParserEvents(boolean isSingelEvent) {
	    mIsSingelEvent = isSingelEvent;
	}
	
	public ResponseParserEvents() {
	    mIsSingelEvent = true;
	}

	public int getEventsCount() {
		return mEeventsCount;
	}

	public int getPageSize() {
		return mPageSize;
	}

	public int getPageNumber() {
		return mPageNumber;
	}

	public List<Event> getEvents() { 
			return events;
	}

	@Override
	protected void onResponseItems(JsonParser jp) throws Exception {
		@SuppressWarnings("unused")
        JsonToken current;
		String fieldName;
		while (jp.nextToken() != JsonToken.END_OBJECT) {
			fieldName = jp.getCurrentName();
			current = jp.nextToken();
			if (fieldName.equals("events")) {
				while (jp.nextToken() != JsonToken.END_ARRAY) {
					Event event = fillEvent(jp, new Event(), mIsSingelEvent);
					event.setServerTime(serverTime);
					events.add(event);
					onEvent(event);
				}
			} else {
				jp.skipChildren();
			}
		}
	}

	protected void onEvent(Event event) {
	}

	@Override
	protected void onHeaderEventsCount(JsonParser jp) throws Exception {
		mEeventsCount = jp.getIntValue();
	}

	@Override
	protected void onHeaderPageSize(JsonParser jp) throws Exception {
		mPageSize = jp.getIntValue();
	}

	@Override
	protected void onHeaderPageNumber(JsonParser jp) throws Exception {
		mPageNumber = jp.getIntValue();
	}

	@Override
	protected void onHeaderServerTime(JsonParser jp) throws Exception {
		parseTime(serverTime, jp.getText());
	}

	@Override
	protected void onEndParse() {
	}



	public static Event fillEvent(JsonParser jp, Event event, boolean addTopGameOnly) throws Exception {
		if (jp.getCurrentToken() != JsonToken.START_OBJECT) {
			throw new org.json.JSONException("Error");// TODO:
		}
		ArrayList<Game> gamesList = new ArrayList<Game>();
		String fieldName;
		String fieldNameSub;
		boolean topGameAdded = false;
		@SuppressWarnings("unused")
        JsonToken currenToken;
		while (true) {
			switch (jp.nextToken()) {
			case FIELD_NAME:
				fieldName = jp.getCurrentName();
				jp.nextToken();
				if (fieldName.equals("eventids")) {
					setEventIds(jp, event);
				} else if (fieldName.equals("details")) {
					setDetails(jp, event);
				} else if (fieldName.equals("media")) {
				    setMedia(jp, event);
                }  else if(fieldName.equals("type")) {
					event.setLive("live".equals(jp.getText()));
				} else if (fieldName.equals("live") || fieldName.equals("non_live") || fieldName.equals("mglist")) {
				    if (!isFinishedEvent){
				        if (fieldName.equals("live") || fieldName.equals("non_live")){
				        
				            while (jp.nextToken() != JsonToken.END_OBJECT) {
				                fieldNameSub = jp.getCurrentName();
				                currenToken = jp.nextToken();
				                if (fieldNameSub.equals("games")) {
				                    while (jp.nextToken() != JsonToken.END_ARRAY) {
				                        Game game = makeGame(jp, event);
				                        gamesList.add(game);
				                    }
				                } else {
				                    jp.skipChildren();
				                }
				           }
                        }
				        else if (fieldName.equals("mglist")){
				            fillMarketGroupFilterData(jp, event);
				        }
                    }
				    else{
				        isFinishedEvent= false;
				        jp.skipChildren();
				    }
                } else {
					jp.skipChildren();
				}
				break;
			case END_OBJECT:
                ArrayList<GameGroup> gameGroupsList = event.getGameGroups();
                if (gameGroupsList.size() > 0) {
                    GameGroup allGameGroup = event.getGameGroupByID(BwinConstants.ALL_ID);
                    for (GameGroup gameGroup : event.getGameGroups()) {
                        for (Game game : gamesList) {
                            if (gameGroup.getGroupID().equals(game.getMarketGroupID())) {
                                allGameGroup.addGame(game);

                                    gameGroup.addGame(game);

                            }
                        }
                    }
                } else {
                    for (Game game : gamesList) {
                            event.addGame(game);
                                            }
                }
			    
				if (event.getParticipantsList() != null && event.getParticipantsList().size() >= 2) {
					String participants0 = event.getParticipantsList().get(0);
					String participants1 = event.getParticipantsList().get(1);
					for (Game game : event.getGames()) {
						for (GameResult gameResult : game.getResultsList()) {
							String name = gameResult.getName();
							name = name.replaceAll("Player 1", participants0);
							name = name.replaceAll("Team 1", participants0);
							name = name.replaceAll("Player 2", participants1);
							name = name.replaceAll("Team 2", participants1);
							gameResult.setName(name);
						}
					}
				}

				return event;
			}
		}
	}

	
    private static ArrayList<GameGroup> fillMarketGroupFilterData(JsonParser jp, Event event) throws Exception {
        String fieldName;
        @SuppressWarnings("unused")
        JsonToken currenToken;
        while (jp.nextToken() != JsonToken.END_ARRAY) {
            GameGroup gameGroup = new GameGroup();
            while (jp.nextToken() != JsonToken.END_OBJECT) {
                fieldName = jp.getCurrentName();
                currenToken = jp.nextToken();
                if (fieldName.equals("id")) {
                    Long id = jp.getLongValue();
                    gameGroup.setGroupID(id);
                    gameGroup.setIsMainGroup(id.equals(event.getMainMarketId()));
                } else if (fieldName.equals("name")) {
                    String name = jp.getText();
                    gameGroup.setGroupName(name);
                } else if (fieldName.equals("count")) {
                    int count = jp.getIntValue();
                    gameGroup.setGameGroupCount(count);
                } else {
                    jp.skipChildren();
                }
            }
            event.addGameGroup(gameGroup);
        }
        
        for (GameGroup group : event.getGameGroups()) {
            if (group.getGroupID().longValue() > 0) {
                event.setMainMarketId(group.getGroupID());
                group.setIsMainGroup(true);
                break;
            }
        }
        return event.getGameGroups();
    }

    private static void setMedia(JsonParser jp, Event event) throws Exception {
        String fieldName;
        @SuppressWarnings("unused")
        JsonToken currenToken;
        while (jp.nextToken() != JsonToken.END_OBJECT) {
            fieldName = jp.getCurrentName();
            currenToken = jp.nextToken();
            if (fieldName.equals("video")) {
                String id = jp.getText();
                StringBuilder idBuilder = new StringBuilder();
                Pattern pat = Pattern.compile("([0-9]+)?");
                Matcher matcher=pat.matcher(id);
                while (matcher.find()) {
                    idBuilder.append(matcher.group());
                };
                event.setVideoId(idBuilder.toString().trim());
            } else if (fieldName.equals("video_published")) {
                String videoPublished = jp.getText();
                try {
                    if ("null".equalsIgnoreCase(videoPublished)) {
                        event.setVideoPublished(null);
                    }else {
                        event.setVideoPublished(videoPublished);
                    }
                } catch (Exception e) {
                    event.setVideoPublished(null);
                }
            } else if (fieldName.equals("videoProvider")) {
                event.setVideoProvider(jp.getText());
            } else {
                jp.skipChildren();
            }
        }
    }

	private static Game makeGame(JsonParser jp, Event event) throws Exception {
		String fieldName;
		String fieldNameSub;
		@SuppressWarnings("unused")
        JsonToken currenToken;
		Game game = new Game();
		game.setEvent(event);
		while (jp.nextToken() != JsonToken.END_OBJECT) {
			fieldName = jp.getCurrentName();
			currenToken = jp.nextToken();
			if (fieldName.equals(ID)) {
				game.setId(jp.getLongValue());
			} else if (fieldName.equals(ONLINE)) {
				game.setOnline(jp.getBooleanValue());
			} else if (fieldName.equals(NAME)) {
				game.setName(jp.getText());
			} else if(fieldName.equals("23way")) {
			    game.set23Way(Boolean.valueOf(jp.getText()));
			} else if(fieldName.equals("winning")) {
			    game.setWinning(Boolean.valueOf(jp.getText()));
			} else if (fieldName.equals("market_group_id")) {
				game.setMarketGroupID(jp.getLongValue());
			} else if (fieldName.equals("market_group_name")) {
				game.setMarketGroupName(jp.getText());
			} else if (fieldName.equals("market_template_id")) {
				game.setMarketTemplateId(jp.getIntValue());
			} else if (fieldName.equals("extended_offer")) {
			    game.setExtendedOffer(Boolean.valueOf(jp.getText()));
			}  else if (fieldName.equals("results")) {
				while (jp.nextToken() != JsonToken.END_ARRAY) {
					GameResult gameResult = new GameResult();
					gameResult.setGame(game);
					while (jp.nextToken() != JsonToken.END_OBJECT) {
						fieldNameSub = jp.getCurrentName();
						currenToken = jp.nextToken();
						if (fieldNameSub.equals(ID)) {
							gameResult.setId(jp.getLongValue());
						} else if (fieldNameSub.equals(NAME)) {
							gameResult.setName(jp.getText());
						} else if (fieldNameSub.equals("odds")) {
							gameResult.setOdds(jp.getDoubleValue());
						} else if (fieldNameSub.equals("frodds")) {
							gameResult.setFractionalOdds(jp.getText());
						} else if (fieldNameSub.equals("usodds")) {
							gameResult.setUsOdds(jp.getText());
						} else if (fieldNameSub.equals(ONLINE)) {
							gameResult.setOnline(jp.getBooleanValue());
						} else if (fieldNameSub.equals("signature")) {
							gameResult.setSignature(jp.getText());
						} else {
							jp.skipChildren();
						}
					}
					game.addResult(gameResult);
				}
			} else {
				jp.skipChildren();
			}
		}
		return game;
	}

	private static void setEventIds(JsonParser jp, Event event) throws Exception {
		// // "eventids":
		// [{
		// "id": 2879246,
		// "vendor": "bwin"
		// }]
		if (jp.getCurrentToken() != JsonToken.START_ARRAY) {
			throw new org.json.JSONException("Error");// TODO:
		}
		if (jp.nextToken() != JsonToken.START_OBJECT) {
			throw new org.json.JSONException("Error");// TODO:
		}
		while (true) {
			switch (jp.nextToken()) {
			case FIELD_NAME:
				String fieldName = jp.getCurrentName();
				jp.nextToken();
				if (fieldName.equals(ID)) {
					event.setId(jp.getLongValue());
				} else if (fieldName.equals("vendor")) {
					event.setPartnerId(jp.getText());
				} else {
					jp.skipChildren();
				}
				break;
			case END_OBJECT:
				if (jp.nextToken() == JsonToken.END_ARRAY) {
					return;
				} else {
					jp.skipChildren();
				}
			}
		}
	}

	private static void setDetails(JsonParser jp, Event event) throws Exception {
		// "details": {
		// "location": {
		// //"region": "Europe",
		// //"id": 7
		// },
		// "name": "Dynamo Kiev - FC Porto",
		// "sports": {
		// //"id": 4,
		// //"name": "Football"
		// },
		// "starts_at": "2012-11-06T19:45:00",
		// "league": {
		// //"id": 13914,
		// //"name": "Champions League - Group A"
		// },
		// "short_name": "Dynamo Kiev - FC Porto",
		// "games_count": 2,
		// "participants": [
		// //{
		// ////"name": "Dynamo Kiev"
		// //},
		// //{
		// ////"name": "FC Porto"
		// //}
		// ]
		// },
		if (jp.getCurrentToken() != JsonToken.START_OBJECT) {
			throw new org.json.JSONException("Error");// TODO:
		}
		String fieldName;
		String fieldNameSub;
		while (true) {
			switch (jp.nextToken()) {
			case FIELD_NAME:
				fieldName = jp.getCurrentName();
				jp.nextToken();
				if (fieldName.equals(NAME)) {
					event.setName(jp.getText());
				} else if (fieldName.equals("starts_at")) {
					event.setStartTime(getEventStartedDate(jp.getText()));
				} else if(fieldName.equals("gtop")) {
					event.setGlobalTop(Boolean.valueOf(jp.getText()));
				} else if(fieldName.equals("games_count")){
				    event.setMarketsCount(Integer.parseInt(jp.getText()));
				}else if (fieldName.equals("short_name")) {
					event.setEventShortName(jp.getText());
				} else if (fieldName.equals(SPORTS)) {
					while (jp.nextToken() != JsonToken.END_OBJECT) {
						fieldNameSub = jp.getCurrentName();
						jp.nextToken();
						if (fieldNameSub.equals(ID)) {
							event.setSportId(jp.getLongValue());
						} else if (fieldNameSub.equals(NAME)) {
							event.setSportName(jp.getText());
						} else {
							jp.skipChildren();
						}
					}
				} else if (fieldName.equals("league")) {
					while (jp.nextToken() != JsonToken.END_OBJECT) {
						fieldNameSub = jp.getCurrentName();
						jp.nextToken();
						if (fieldNameSub.equals(ID)) {
							event.setLeagueId(jp.getLongValue());
						} else if (fieldNameSub.equals(NAME)) {
							event.setLeagueName(jp.getText());
						} else {
							jp.skipChildren();
						}
					}
				} else if (fieldName.equals("participants")) {
					while (jp.nextToken() != JsonToken.END_ARRAY) {
						while (jp.nextToken() != JsonToken.END_OBJECT) {
							fieldNameSub = jp.getCurrentName();
							jp.nextToken();
							if (fieldNameSub.equals(NAME)) {
								event.addParticipant(jp.getText());
							} else {
								jp.skipChildren();
							}
						}
					}
				} else if (fieldName.equals("location")) {
					while (jp.nextToken() != JsonToken.END_OBJECT) {
						fieldNameSub = jp.getCurrentName();
						jp.nextToken();
						if (fieldNameSub.equals(ID)) {
							event.setReagionId(jp.getLongValue());
						}else if (fieldNameSub.equals("name")) {
                            event.setRegionName(jp.getText());
                        }else if (fieldNameSub.equals("region")) {
							event.setRegionName(jp.getText());
						} else {
							jp.skipChildren();
						}
					}
				} else {
					jp.skipChildren();
				}
				break;
			case END_OBJECT:
				return;
			}
		}
	}

	static Time getEventStartedDate(String startsAtStr) {
		return parseTime(newTimeObject(), startsAtStr);
	}

}
