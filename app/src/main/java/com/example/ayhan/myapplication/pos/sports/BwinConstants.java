package com.example.ayhan.myapplication.pos.sports;

public interface BwinConstants {
	
    public static final String BET_SUBMITED = "bet_submited";
    public static final String FIRST_START_TUTORIAL = "first_start";
    
	
    public static final String COMMA = ",";

	public static final String STATE_CANCELED = "CANCELED";
	public static final String STATE_LOST = "LOST";
	public static final String STATE_OPEN = "OPEN";
	public static final String STATE_WON = "WON";
	public static final int COMBO_BET = 2;
	public static final int SINGLE_BET = 1;
	public static final int SYSTEM_BET = 3;
	
	public static final double INVALID_STAKE = 1.0;

    /**
     * User Settings
     */
    public static final int PREDEFINED_STAKES_COUNT = 5;

	/**
	 * Market groups.
	 */
	public static final Long UNDEFINED_ID = -3L;
	public static final Long CAPPTAIN_ID = -2L;
	public static final Long OTHER_ID = 10000L;
	public static final Long ALL_ID = -1L;

	/**
	 * Remove/add favorites error message. 
	 */
	public final static String REM_ADD_ERROR = 	"Technical Error while removing/adding\nyour favorite, please try again";
	
	public static final String UNDER_MINIMUM_COMPULSORY_ODDS_MULTY = "underMinimumCompulsoryOddsMulty";
    public static final String UNDER_MINIMUM_COMPULSORY_ODDS_SINGLE = "underMinimumCompulsoryOddsSingle";
    public static final String ACTIVITY_PASSED_URL = "com.bwinlabs.betdroid_lib.intent.extra.passed_url";
    
	public static final String BETSLIP_ID_KEY = "betslipID";
	
	public static final String RFC_MESSAGE_DIALOG_DATA = "rfcMessageDlgData";
	public static final String RFC_MESSAGE_DIALOG_DATA_INDEX = "rfcMessageDlgDataIndex";

	public static final String RFC_MESSAGE_TYPE_ON_START = "onStart";
	public static final String RFC_MESSAGE_TYPE_ON_LOGIN = "onLogin";
	public static final String RFC_MESSAGE_TYPE_ON_LOGOUT = "onLogout";
	public static final String RFC_MESSAGE_TYPE_ON_ACTIVE = "onActive";

	public static final String ERROR_BUTTON_TEXT = "error_button_text";

	public static final String ERROR_MESSAGE = "error_message";

	public static final String REDIRECT_MESSAGE = "redirect_message";

	public static final String TITLE_MESSAGE = "title_message";

	public static final String INFO_MESSAGE = "info_message";

	public static final String USER_NAME = "username";
	public static final String WORKFLOW_TYPE = "workflowType";
	public static final String SESSION_TOKEN = "sessionToken";
	public static final String USER_TOKEN = "userToken";
	
	public static final String PARAM_NAME_SESSION_KEY = "sessionKey";
	public static final String PARAM_NAME_LANG = "lang";
	public static final String PARAM_NAME_POKER = "poker";
	
	public static final String PARAM_LOGIN_WORKFLOW_TYPE = "loginWorkflowType";
	public static final String PARAM_LOGIN_SSO_TOKEN = "loginSsoToken";
	public static final String PARAM_LOGIN_WORKFLOW_14 = "workflow14";

    public static final String BALANCE_UPDATED = "balance_updated";
    public static final String MYBETS_UPDATED = "mybets_updated";

    public static final int LOGIN_MAX_TRIES = 2;
    public static final String KEY = "key";
    public static final String VALUE = "value";
    public static final String USER_SETTINGS = "UserSettings";

    public static final int MAX_QUICK_BETS = 1;
    public static final long ON_CLICK_ACTION_DELAY = 100L;
    public static final int FREEBETS_UPDATE_INTERVAL_MS = 1 * 60* 1000 /*1 minute*/;
    
    public static final String EVENT_ID_UNDEFINED  = null;
    public static final String LEAGUE_ID_UNDEFINED = null;
    
    public static final String STATS_PATH_EVENTS  = "Events/";
    public static final String STATS_PATH_LEAGUES = "Leagues/";
    
    public static final String BWIN_PROTOCOL = "bwin://";
    public static final String CASINO_WEBSETTINGS_SUFFIX = " Wrapper";
    public static final String CASINO_TRACK_PARAM_NAME = "trid";
    public static final String CASINO_TRACK_PARAM_VALUE = "in11072";
    
    public static final String BRAND_REPLACEMENT_REFERENCE = "-*LabelWWWRef*-";
    public static final String BRAND_REPLACEMENT_LABEL = "-*lable_www*-";

    public static final String CURRENT_CONTENT_FRAGMENT = "current_content_fragment";
    public static final String CURRENT_APPLICATION_FRAGMENT = "current_application_fragment";

    public static final int SLIDER_GAME_TYPE_ROULETTE = 20108;
    public static final int SLIDER_GAME_TYPE_BLACK_JACK = 20207;
    public static final int SLIDER_GAME_TYPE_BLACK_JACK_ES = 20208;
}
