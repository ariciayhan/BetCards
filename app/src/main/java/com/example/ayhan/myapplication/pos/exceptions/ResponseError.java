package com.example.ayhan.myapplication.pos.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpStatus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.turbomanage.httpclient.HttpResponse;

public class ResponseError extends Exception {

    private static final int  ERROR_CODE_INVALID_SESSION_SECURITY_TOKEN_RANGE_START = 201;
    private static final int  ERROR_CODE_INVALID_SESSION_SECURETY_TOKEN_RANGE_END = 210;
    private static final long serialVersionUID = -6503603462933114180L;

    public final int statusCode;
    public final int errorCode;
    public final String errorMessage;

    private Map<String, String> errorValues;

    public ResponseError()
    {
        this.statusCode = HttpStatus.SC_OK;
        this.errorCode = 0;
        this.errorMessage = "";
        this.errorValues = new HashMap<String, String>();
    }

    public ResponseError(int statusCode, int errorCode, String errorMessage, String errorValues)
    {
        this.statusCode   = statusCode;
        this.errorCode    = errorCode;
        this.errorMessage = errorMessage;
        this.errorValues  = parseErrorValues(errorValues);
    }

    public ResponseError(HttpResponse response){
        int errorCode       = -1;
        String errorMessage = "";
        String errorValuesJson  = "";
        try {
            JSONObject jsonObj = new JSONObject(response.getBodyAsString());
            errorCode        = jsonObj.has("code")    ? jsonObj.getInt("code") : errorCode;
            errorMessage     = jsonObj.has("message") ? jsonObj.getString("message") : errorMessage;
            errorValuesJson  = jsonObj.has("values")  ? jsonObj.getString("values") : errorValuesJson;
        } catch (JSONException e) { }

        this.statusCode   = response.getStatus();
        this.errorCode    = errorCode;
        this.errorMessage = errorMessage;
        this.errorValues  = (errorValuesJson == null) ? null : parseErrorValues(errorValuesJson);
    }

    public boolean isCausedByInvalidSecurityToken(){
        return (errorCode >= ERROR_CODE_INVALID_SESSION_SECURITY_TOKEN_RANGE_START)
                && (errorCode <= ERROR_CODE_INVALID_SESSION_SECURETY_TOKEN_RANGE_END);
    }

    private Map<String, String> parseErrorValues(final String values)
    {
        Map<String, String> map = new HashMap<String, String>();

        try
        {
            final JSONArray jsonArray = new JSONArray(values);
            for (int i =0; i < jsonArray.length(); i++) {
                JSONObject pair = jsonArray.getJSONObject(i);
                String key = pair.getString("Key");
                String value = pair.getString("Value");
                map.put(key, value);
            }
        } catch (JSONException e) {
            return map;
        }

        return map;
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append(statusCode);
        sb.append(" - ");
        sb.append(errorCode);
        sb.append(" - ");
        sb.append(errorMessage);
        sb.append(" - ");
        sb.append(getErrorValues());
        return sb.toString();
    }

    public boolean isOK()
    {
        return statusCode != HttpStatus.SC_OK;
    }

    public Map<String, String> getErrorValues()
    {
        return errorValues;
    }

}
