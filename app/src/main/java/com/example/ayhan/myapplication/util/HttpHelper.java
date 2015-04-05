package com.example.ayhan.myapplication.util;

import android.os.Build;

import com.example.ayhan.myapplication.parsers.ResponseParser;
import com.turbomanage.httpclient.BasicRequestHandler;
import com.turbomanage.httpclient.ConsoleRequestLogger;
import com.turbomanage.httpclient.HttpRequestException;
import com.turbomanage.httpclient.HttpResponse;
import com.turbomanage.httpclient.ParameterMap;
import com.turbomanage.httpclient.RequestHandler;
import com.turbomanage.httpclient.RequestLogger;

import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Set;
import java.util.zip.GZIPInputStream;

import javax.net.ssl.HttpsURLConnection;

import static com.example.ayhan.myapplication.pos.MODE.*;

public class HttpHelper
{

    public static final String PROTOCOL_HTTP = "http://";
    public static final String PROTOCOL_HTTPS = "https://";
    private static final String ACCEPT_ENCODING = "Accept-Encoding";
    //	private static final String MIME_FORM_ENCODED = "application/x-www-form-urlencoded";
    public static final String HTTP_CONTENT_TYPE = "Content-Type";
    public static final String HTTP_CONTENT_TYPE_JSON = "application/json";
    public static final String HTTP_CONTENT_TYPE_XML = "application/xml";
    private static final String GZIP = "gzip";
    private static final Integer CONNECTION_TIMEOUT = 20000;
    private static final Integer SO_TIMEOUT = 20000;
    private static HttpHelper instance = new HttpHelper();
    private static RequestLogger mLogger;

    static {
        setup();
    }

    public HttpHelper() {
    }

    public static HttpHelper instance() {
        return instance;
    }

    private static void setup() {
        HttpsURLConnection.setFollowRedirects(false); // for HTTPS requests
        HttpURLConnection.setFollowRedirects(false); // for HTTP requests
        disableConnectionReuseIfNecessary();

        if (TEST) {
            mLogger = new ConsoleRequestLogger();
        } else {
            mLogger = new ConsoleRequestLogger();
        }
    }

    @SuppressWarnings("deprecation")
    private static void disableConnectionReuseIfNecessary() {
        // HTTP connection reuse which was buggy pre-froyo
        if (Integer.parseInt(Build.VERSION.SDK) < Build.VERSION_CODES.FROYO) {
            System.setProperty("http.keepAlive", "false");
        }
    }

    private BwinHttpClient getHttpClient(Map<String, String> headers, ResponseParser responseParser) {
        RequestHandler requestHandler;
        if (null != responseParser) {
            requestHandler = new StreamRequestHandler(responseParser);
        } else {
            requestHandler = new GzipBasicRequestHandler();
        }

        BwinHttpClient client = new BwinHttpClient("", requestHandler);
        client.setConnectionTimeout(CONNECTION_TIMEOUT);
        client.setReadTimeout(SO_TIMEOUT);
        client.setRequestLogger(mLogger);
        if(headers != null && !headers.isEmpty()) {
            Set<String> keyHeaders = headers.keySet();
            for(String key : keyHeaders) {
                client.addHeader(key, headers.get(key));
            }
        }

        if (headers == null || !headers.containsKey(HttpHelper.ACCEPT_ENCODING)) {
            client.addHeader(HttpHelper.ACCEPT_ENCODING, HttpHelper.GZIP);
        }

        return client;
    }

    public HttpResponse performGet(String uri, Map<String, String> headers, ParameterMap params, ResponseParser responseParser) {
        BwinHttpClient client = getHttpClient(headers, responseParser);

        if(params == null) {
            params = new ParameterMap();
        }

        HttpResponse result = client.get(uri, params);
        if (result == null) {
            result = client.getHttpResponseError();
        }

        return result;
    }

    public HttpResponse performPost(String path, String contentType, byte[] data, Map<String, String> headers, ResponseParser responseParser) {
        BwinHttpClient client = getHttpClient(headers, responseParser);
        HttpResponse result = client.post(path, contentType, data);

        if (result == null) {
            result = client.getHttpResponseError();
        }

        return result;
    }

    //private static final PosUrls posUrls = EnvironmentManager.getPosUrls(BetdroidApplication.instance());



    public static String getUTF8EncodedURL(String source) throws UnsupportedEncodingException {
        return URLEncoder.encode(source, RequestHandler.UTF8).replace("+", "%20");
    }

    static class StreamRequestHandler extends GzipBasicRequestHandler {
        public static final byte[] STREAM_REQUEST_BODY = new byte[]{123, 125}; //"{}"

        private ResponseParser responseParser;

        public StreamRequestHandler(ResponseParser responseParser) {
            this.responseParser = responseParser;
        }

        @Override
        public byte[] readStream(InputStream in) throws IOException {
            in = decompressStream(in);
            try {
                responseParser.parse(in);
            } catch (Exception e) {
                throw new IOException("JSON Stream parse error", e);
            }
            return STREAM_REQUEST_BODY;
        }
    }

    static class GzipBasicRequestHandler extends KnownCertificateRequestHandler
    {

        private HttpRequestException httpRequestException;

        /**
         * Reads the input into a {@link String}. Override this method to handle
         * other content types.
         *
         * @see com.turbomanage.httpclient.BasicRequestHandler#readStream(java.io.InputStream)
         */
        @Override
        public byte[] readStream(InputStream in) throws IOException {

            in = decompressStream(in);

            return super.readStream(in);
        }

        protected InputStream decompressStream(InputStream input) throws IOException {

            byte[] signature = new byte[2];

            PushbackInputStream pb = new PushbackInputStream( input, 2 );
            pb.read(signature); //read the signature
            pb.unread(signature); //push back the signature to the stream

            byte first = (byte) (GZIPInputStream.GZIP_MAGIC); // (byte) 0x1f
            byte second = (byte) (GZIPInputStream.GZIP_MAGIC >> 8); // (byte) 0x8b

            if (signature[0] == first && signature[1] == second) { //check if matches standard gzip magic number
                return new GZIPInputStream( pb );
            } else {
                return pb;
            }
        }

        @Override
        public boolean onError(HttpRequestException e) {
            this.httpRequestException = e;
            return super.onError(e);
        }

        public HttpRequestException getHttpRequestException() {
            return httpRequestException;
        }

    }

    static class KnownCertificateRequestHandler extends BasicRequestHandler {
        @Override
        public HttpURLConnection openConnection(String urlString) throws IOException {
            URL url = new URL(urlString);
            if ("https".equalsIgnoreCase(url.getProtocol())) {
                return (HttpURLConnection)url.openConnection();
                //BwinHttps.openConnection(url);
            } else {
                return super.openConnection(urlString);
            }
        }
    }

}
