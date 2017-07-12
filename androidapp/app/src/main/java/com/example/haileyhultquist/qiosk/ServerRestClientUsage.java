package com.example.haileyhultquist.qiosk;

import android.content.Context;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.message.BasicHeader;
import cz.msebera.android.httpclient.protocol.HTTP;


/**
 * Created by Takondwa Kakusa on 4/29/2017.
 */

public class ServerRestClientUsage {

    int fullLength = 0;

    public interface Callback<T> {
        void onResponse(T t) throws JSONException;
    }

    public void loginEmployer(Context context, String username, String password, final Callback<String> callback) {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("username", username);
            jsonObject.put("password", password);
            String url = "loginEmployer/";
            StringEntity entity = new StringEntity(jsonObject.toString());
            entity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
            RestfulCalls.post(context, url, "", entity, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    if (callback != null) {
                        try {
                            callback.onResponse(new String(responseBody, "UTF-8"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    if (callback != null) {
                        try {
                            callback.onResponse(new String(responseBody, "UTF-8"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public void loginWorker(Context context, String username, String password, final Callback<String> callback) {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("username", username);
            jsonObject.put("password", password);
            String url = "loginUser/";
            StringEntity entity = new StringEntity(jsonObject.toString());
            entity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
            RestfulCalls.post(context, url, "", entity, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    if (callback != null) {
                        try {
                            callback.onResponse(new String(responseBody, "UTF-8"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    if (callback != null) {
                        try {
                            callback.onResponse(new String(responseBody, "UTF-8"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public void getUserData(String key, final Callback<String> callback) {
        String url = "profile/";

        RestfulCalls.get(url, key, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                if (callback != null) {
                    try {
                        callback.onResponse(response.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String s, Throwable t) {
                if (callback != null) {
                    try {
                        callback.onResponse(s);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public void postJob(Context context, String key, String title, String description, String pay, final Callback<String> callback) {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("title", title);
            jsonObject.put("description", description);
            jsonObject.put("address", "my house");
            jsonObject.put("payment", pay);
            String url = "list/";
            StringEntity entity = new StringEntity(jsonObject.toString());
            entity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
            RestfulCalls.post(context, url, key, entity, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    if (callback != null) {
                        try {
                            callback.onResponse(new String(responseBody, "UTF-8"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    if (callback != null) {
                        try {
                            callback.onResponse(new String(responseBody, "UTF-8"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public void getJobs(String key, final Callback<String> callback) {
        String url = "list/";

        RestfulCalls.get(url, key, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray responses) {
                if (callback != null) {
                    try {
                        callback.onResponse(responses.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String s, Throwable t) {
                if (callback != null) {
                    try {
                        callback.onResponse(s);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }


    public void getAJob(String key, String PK, final Callback<String> callback) {
        String url = "list/pk=" + PK + "/";

        RestfulCalls.get(url, key, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                if (callback != null) {
                    try {
                        callback.onResponse(response.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String s, Throwable t) {
                if (callback != null) {
                    try {
                        callback.onResponse(s);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public void MarkComplete(Context context, String key, String PK, final Callback<String> callback) {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("pk", PK);
            String url = "closeJob/";
            StringEntity entity = new StringEntity(jsonObject.toString());
            entity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
            RestfulCalls.post(context, url, key, entity, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    if (callback != null) {
                        try {
                            callback.onResponse(new String(responseBody, "UTF-8"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    if (callback != null) {
                        try {
                            callback.onResponse(new String(responseBody, "UTF-8"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
