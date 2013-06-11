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
import android.content.Intent;


/**
 *
 * @version $Revision: $
 * @author <a href="mailto:blake.meike@gmail.com">G. Blake Meike</a>
 */
public class YambaSvc extends IntentService {
    private static final String TAG = "SVC";

    public static void post(String status) {

    }

    public YambaSvc() { super(TAG); }

    /// RUN ON DAEMON THREAD
    @Override
    protected void onHandleIntent(Intent arg0) {
        // TODO Auto-generated method stub

    }
}
