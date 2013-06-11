package com.marakana.android.yamba;

import android.app.Application;

import com.marakana.android.yamba.clientlib.YambaClient;


public class YambaApp extends Application {

    private YambaClient client;

    public synchronized YambaClient getClient() {
        if (null == client) {
            client = new YambaClient("student", "password", "http://yamba.marakana.com/api");
        }
        return client;
    }
}
