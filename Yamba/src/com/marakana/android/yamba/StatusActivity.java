
package com.marakana.android.yamba;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.app.Activity;

public class StatusActivity extends Activity {
    private static final String TAG = "STATUS";

    private static final int MAX_CHARS = 140;
    private static final int WARN_CHARS = 10;
    private static final int ERROR_CHARS = 0;

    private int colorOk;
    private int colorWarn;
    private int colorError;

    private TextView count;
    private EditText status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (BuildConfig.DEBUG) { Log.d(TAG, "Status activity"); }

        colorOk = getResources().getColor(R.color.ok);
        colorWarn = getResources().getColor(R.color.warn);
        colorError = getResources().getColor(R.color.error);

        setContentView(R.layout.activity_status);

        count = (TextView) findViewById(R.id.status_count);
        status = (EditText) findViewById(R.id.status_status);
        status.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable arg0) { updateCount(); }

            @Override
            public void beforeTextChanged(CharSequence s, int a1, int a2, int a3) { }

            @Override
            public void onTextChanged(CharSequence s, int a1, int a2, int a3) { }
        });
    }

    private void updateCount() {
        int n = MAX_CHARS - status.getText().length();
        int color = colorOk;
        if (ERROR_CHARS > n) { color = colorError; }
        else if (WARN_CHARS > n) { color = colorWarn; }
        count.setText(String.valueOf(n));
        count.setTextColor(color);
    }

}
