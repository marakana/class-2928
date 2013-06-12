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
package com.marakana.android.yamba.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import com.marakana.android.yamba.YambaContract;


/**
 *
 * @version $Revision: $
 * @author <a href="mailto:blake.meike@gmail.com">G. Blake Meike</a>
 */
public class YambaProvider extends ContentProvider {
    public static final String TAG = "CP";

    private static final ColumnMap COL_MAP_TIMELINE = new ColumnMap.Builder()
        .addColumn(YambaContract.Timeline.Columns.ID, YambaDBHelper.COL_ID, ColumnMap.Type.LONG)
        .addColumn(YambaContract.Timeline.Columns.TIMESTAMP, YambaDBHelper.COL_TIMESTAMP, ColumnMap.Type.LONG)
        .addColumn(YambaContract.Timeline.Columns.USER, YambaDBHelper.COL_USER, ColumnMap.Type.STRING)
        .addColumn(YambaContract.Timeline.Columns.STATUS, YambaDBHelper.COL_STATUS, ColumnMap.Type.STRING)
        .build();


    private YambaDBHelper helper;

    @Override
    public boolean onCreate() {
        helper = new YambaDBHelper(getContext());
        return null != helper;
    }

    @Override
    public String getType(Uri arg0) {
        return null;
    }

    @Override
    public Cursor query(Uri arg0, String[] arg1, String arg2, String[] arg3, String arg4) {
        return getDb().query(
                YambaDBHelper.TABLE,
                new String[] { "max(" + YambaDBHelper.COL_TIMESTAMP + ")" },
                null,
                null,
                null,
                null,
                null);
    }

    @Override
    public Uri insert(Uri arg0, ContentValues arg1) {
        throw new UnsupportedOperationException("insert not supported");
    }

    @Override
    public int bulkInsert(Uri arg0, ContentValues[] vals) {
        int count = 0;

        SQLiteDatabase db = getDb();
        try {
            db.beginTransaction();
            for (ContentValues row: vals) {
                if (0 < db.insert(YambaDBHelper.TABLE, null, COL_MAP_TIMELINE.translateCols(row))) {
                    count++;
                }
            }
            db.setTransactionSuccessful();
        }
        finally {
            db.endTransaction();
        }

        Log.d(TAG, "Bulk insert: " + count);
        return count;
    }

    @Override
    public int update(Uri arg0, ContentValues arg1, String arg2, String[] arg3) {
        throw new UnsupportedOperationException("update not supported");
    }

    @Override
    public int delete(Uri arg0, String arg1, String[] arg2) {
        throw new UnsupportedOperationException("delete not supported");
    }

    private SQLiteDatabase getDb() { return helper.getWritableDatabase(); }
}
