/* $Id: $
   Copyright 2013, G. Blake Meike

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/
package com.marakana.android.yamba.svc;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.marakana.android.yamba.R;
import com.marakana.android.yamba.YambaApp;
import com.marakana.android.yamba.clientlib.YambaClientException;


/**
 *
 * @version $Revision: $
 * @author <a href="mailto:blake.meike@gmail.com">G. Blake Meike</a>
 */
public class YambaSvc extends IntentService {
    private static final String TAG = "SVC";

    private static final String PARAM_STATUS = "YambaSvc.STATUS";
    private static final int OP_POST_COMPLETE = -1;

    public static void post(Context ctxt, String status) {
        Intent i = new Intent(ctxt, YambaSvc.class);
        i.putExtra(PARAM_STATUS, status);
        ctxt.startService(i);
    }

    private static class Hdlr extends Handler {
        private final  YambaSvc svc;

        public Hdlr(YambaSvc svc) { this.svc = svc; }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case OP_POST_COMPLETE:
                    svc.postToast(msg.arg1);
                    break;
            }
        }
    }

    private Handler hdlr;

    public YambaSvc() { super(TAG); }

    @Override
    public void onCreate() {
        super.onCreate();
        hdlr = new Hdlr(this);
    }

    @Override
    protected void onHandleIntent(Intent i) {
        int succ = R.string.fail;
        try {
            ((YambaApp) getApplication()).getClient()
                .postStatus(i.getStringExtra(PARAM_STATUS));
            succ = R.string.succeed;
        }
        catch (YambaClientException e) {
            Log.w(TAG, "post failed", e);
        }

        Message.obtain(hdlr, OP_POST_COMPLETE, succ, 0).sendToTarget();
    }

    void postToast(int succ) {
        Toast.makeText(this, succ, Toast.LENGTH_LONG).show();
    }
}
