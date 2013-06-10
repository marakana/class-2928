
package com.marakana.android.yamba;

import android.os.Bundle;
import android.util.Log;
import android.app.Activity;

public class StatusActivity extends Activity {
    private static final String TAG = "STATUS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (BuildConfig.DEBUG) { Log.d(TAG, "Status activity"); }
        setContentView(R.layout.activity_status);
    }

}
