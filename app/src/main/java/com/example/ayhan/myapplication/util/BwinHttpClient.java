package com.example.ayhan.myapplication.util;

import com.turbomanage.httpclient.BasicHttpClient;
import com.turbomanage.httpclient.HttpMethod;
import com.turbomanage.httpclient.HttpRequestException;
import com.turbomanage.httpclient.HttpResponse;
import com.turbomanage.httpclient.ParameterMap;
import com.turbomanage.httpclient.RequestHandler;

public class BwinHttpClient extends BasicHttpClient
{
    /**
     * Constructs a client with baseUrl and custom {@link RequestHandler}.
     *
     * @param baseUrl
     * @param requestHandler
     */
    public BwinHttpClient(String baseUrl, RequestHandler requestHandler) {
        super(baseUrl, requestHandler);
    }

    /**
     * Execute a GET request and return the response.
     *
     * The supplied parameters are URL encoded and sent as the query string.
     *
     * @param path
     * @param params
     * @return Response object
     */
    @Override
    public HttpResponse get(String path, ParameterMap params) {
        String url = path;
        if (params != null) {
            String queryString = params.urlEncode();
            if (null != queryString && !queryString.isEmpty()) {
                char paramsDivider = '?';
                if (url.indexOf(paramsDivider) >= 0) {
                    paramsDivider = '&';
                }
                url += paramsDivider + queryString;
            }
        }
        return super.get(url, null);
    }

    public HttpResponse getHttpResponseError() {
        if (requestHandler instanceof HttpHelper.GzipBasicRequestHandler) {
            try {
                return ((HttpHelper.GzipBasicRequestHandler) requestHandler).getHttpRequestException().getHttpResponse();
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }

    @Override
    protected HttpResponse doHttpMethod(String path, HttpMethod httpMethod, String contentType, byte[] content) throws HttpRequestException {
        try {
            return super.doHttpMethod(path, httpMethod, contentType, content);
        } catch (HttpRequestException e) {
            throw e;
        } catch (Exception e) {
            return null;
        }
    }
}
