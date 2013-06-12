package com.marakana.android.yamba;

import android.app.Application;

import com.marakana.android.yamba.clientlib.YambaClient;
import com.marakana.android.yamba.svc.YambaSvc;


public class YambaApp extends Application {

    private YambaClient client;

    @Override
    public void onCreate() {
        super.onCreate();
        YambaSvc.startPolling(this);
    }

    public synchronized YambaClient getClient() {
        if (null == client) {
            client = new YambaClient("student", "password", "http://yamba.marakana.com/api");
        }
        return client;
    }
}
