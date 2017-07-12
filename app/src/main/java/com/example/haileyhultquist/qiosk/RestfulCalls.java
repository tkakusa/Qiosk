package com.example.haileyhultquist.qiosk;

import android.content.Context;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import cz.msebera.android.httpclient.entity.StringEntity;

/**
 * Created by Takondwa Kakusa on 4/17/2017.
 */

public class RestfulCalls {

    //private static final String BASE_URL = "http://ec2-34-209-243-205.us-west-2.compute.amazonaws.com:8080/";
    private static final String BASE_URL = "http://10.73.172.61:8000/";
    //For testing purposes
    //private static SyncHttpClient client = new SyncHttpClient();

    //For normal use
    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void get(String url, String key, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        Log.d("URL", getAbsoluteUrl(url));
        client.addHeader("token", key);
        client.get(getAbsoluteUrl(url), params, responseHandler);
    }

    //public static void post(Context context, String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
    public static void post(Context context, String url, String key, StringEntity stringEntity, AsyncHttpResponseHandler responseHandler) {
        client.addHeader("token", key);
        client.post(null, getAbsoluteUrl(url), stringEntity, "application/json", responseHandler);
    }

    public static void put(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.put(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void delete(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.delete(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void getByUrl(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.get(url, params, responseHandler);
    }

    public static void postByUrl(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.post(url, params, responseHandler);
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }
}
