package com.example.ayhan.myapplication.pos;

import android.util.Log;

import com.example.ayhan.myapplication.pos.exceptions.ResponseError;
import com.example.ayhan.myapplication.util.HttpHelper;
import com.turbomanage.httpclient.HttpResponse;
import com.turbomanage.httpclient.ParameterMap;

import org.apache.http.HttpStatus;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.net.URISyntaxException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;

/**
 * Created by ayhan on 04/04/15.
 */
public class PosApi {
    private static final String TAG = PosApi.class.getName();

    public static final String ACCESS_ID = "YjA0MmZkZGEtODVlYi00NmE2LWE1MGMtZWM4YTJkZGQ5YmM1";
    public static final String API_URL = "https://api.bwin.com/v3/";
    //public static final String API_URL = "http://www.google.com";

    PosSession session;

    public void login(String username, String password){
        String loginXML;
        String language = "en";

        loginXML = String.format("<Login username=\"%s\" password=\"%s\" language=\"%s\"/>", username, password, language);

        //LoginResponse loginResponse = null;
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Accept", HttpHelper.HTTP_CONTENT_TYPE_JSON);
        headers.put("X-Bwin-AccessId", ACCESS_ID);
        headers.put("Accept-Language", language);
        //headers.put("User-Agent", userAgent);
        headers.put(HttpHelper.HTTP_CONTENT_TYPE, HttpHelper.HTTP_CONTENT_TYPE_XML);
        headers.put("Accept-Encoding", "gzip");
        try {

            String jsonResponse = requestJSON("Authentication.svc/Login", loginXML, API_URL, headers);
            Log.d(TAG, jsonResponse);

            //loginResponse = PosImpl_V3.makeLoginResponse(jsonResponse);

            // TODO: need to be improved
            //loginResponse.getUserData().setUserName(username);
        } catch (ResponseError e) {
            //loginResponse = PosImpl_V3.makeErrorLoginResponse(e);
        }  catch (Exception e) {
            printLogMessage(e);

        } finally {
            //return loginResponse;
        }
    }


    private static String requestJSON(String endpoint, String data, String apiURI, Map<String, String> headers) throws ResponseError {
        try {
            URI fullURI = getURIWithParams(apiURI + endpoint, new ParameterMap());

            printLogMessage("sending login request: " + data);

            HttpResponse response = HttpHelper.instance().performPost(fullURI.toString(), HttpHelper.HTTP_CONTENT_TYPE_XML, data.getBytes(), headers, null);

            if (response == null || response.getStatus() == 0) {
                throw new Exception("Network error!");
            }

            printLogMessage(String.format("Statuscode is: %s", response.getStatus()));

            if (response.getStatus() != HttpStatus.SC_OK) {
                final JSONObject jsonObj = new JSONObject(response.getBodyAsString());
                String values = jsonObj.has("values") ? jsonObj.getString("values") : "";
                throw new ResponseError(response.getStatus(), jsonObj.getInt("code"), jsonObj.getString("message"), values);
            } else {
                Map<?, ?> responseHeaders = response.getHeaders();
                String tokenKey = "x-bwin-session-token";
                if (responseHeaders.containsKey(tokenKey)) {

                    String tokenValue = responseHeaders.get(tokenKey).toString();
                    tokenValue = tokenValue.substring(1, tokenValue.length() - 1);
                }

                return response.getBodyAsString();
            }
        } catch (ResponseError e) {
            printLogMessage(e);
            throw e;
        } catch (Exception e) {
            printLogMessage(e);
            return "";
        }
    }


    private static void printLogMessage(Exception e) {
        if (MODE.D) {
            Log.e(TAG, e.toString(), e);
        }
    }

    private static void printLogMessage(String message) {
        if (MODE.D) {
            Log.d(TAG, message);
        }
    }

    public static URI getURIWithParams(String uri, ParameterMap params) throws URISyntaxException {
        if (!params.isEmpty() && !uri.isEmpty()) {
            StringBuilder sb = new StringBuilder(uri);
            sb.append("?");

            int counter = 0;
            int lastParameter = params.size() -1;
            Set<String> keySet = params.keySet();
            for (String paramName : keySet) {
                String valueName = params.get(paramName);
                sb.append(paramName).append("=").append(valueName);
                if (counter < lastParameter) {
                    sb.append("&");
                    counter ++;
                }
            }
            return new URI(sb.toString());
        }
        return new URI(uri);
    }
}
