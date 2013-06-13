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
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
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

    private static final int TIMELINE_ITEM_TYPE = 1;
    private static final int TIMELINE_DIR_TYPE = 2;
    private static final UriMatcher MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        MATCHER.addURI(YambaContract.AUTHORITY, YambaContract.Timeline.TABLE, TIMELINE_DIR_TYPE);
        MATCHER.addURI(YambaContract.AUTHORITY, YambaContract.Timeline.TABLE + "/#", TIMELINE_ITEM_TYPE);
    }

    private static final ColumnMap COL_MAP_TIMELINE = new ColumnMap.Builder()
        .addColumn(YambaContract.Timeline.Columns.ID, YambaDBHelper.COL_ID, ColumnMap.Type.LONG)
        .addColumn(YambaContract.Timeline.Columns.TIMESTAMP, YambaDBHelper.COL_TIMESTAMP, ColumnMap.Type.LONG)
        .addColumn(YambaContract.Timeline.Columns.USER, YambaDBHelper.COL_USER, ColumnMap.Type.STRING)
        .addColumn(YambaContract.Timeline.Columns.STATUS, YambaDBHelper.COL_STATUS, ColumnMap.Type.STRING)
        .build();

    private static final ProjectionMap PROJ_MAP_TIMELINE = new ProjectionMap.Builder()
        .addColumn(YambaContract.Timeline.Columns.ID, YambaDBHelper.COL_ID)
        .addColumn(YambaContract.Timeline.Columns.TIMESTAMP, YambaDBHelper.COL_TIMESTAMP)
        .addColumn(YambaContract.Timeline.Columns.USER, YambaDBHelper.COL_USER)
        .addColumn(YambaContract.Timeline.Columns.STATUS, YambaDBHelper.COL_STATUS)
        .addColumn(YambaContract.Timeline.Columns.MAX_TIMESTAMP, "max(" + YambaDBHelper.COL_TIMESTAMP + ")")
        .build();

    private YambaDBHelper helper;

    @Override
    public boolean onCreate() {
        helper = new YambaDBHelper(getContext());
        return null != helper;
    }

    @Override
    public String getType(Uri uri) {
        switch (MATCHER.match(uri)) {
            case TIMELINE_ITEM_TYPE:
                return YambaContract.Timeline.ITEM_TYPE;
            case TIMELINE_DIR_TYPE:
                return YambaContract.Timeline.DIR_TYPE;
            default:
                throw new IllegalArgumentException("Unrecognized URI: "  + uri);
        }
    }

    @SuppressWarnings("fallthrough")
    @Override
    public Cursor query(Uri uri, String[] proj, String sel, String[] selArgs, String sort) {

        long pk = -1;
        String table = null;
        switch (MATCHER.match(uri)) {
            case TIMELINE_ITEM_TYPE:
                pk = ContentUris.parseId(uri);
            case TIMELINE_DIR_TYPE:
                table = YambaDBHelper.TABLE;
                break;
            default:
                throw new IllegalArgumentException("Unrecognized URI: "  + uri);
        }

        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        qb.setTables(table);
        qb.setProjectionMap(PROJ_MAP_TIMELINE.getProjectionMap());

        if (0 < pk) { qb.appendWhere(YambaDBHelper.COL_ID + "=" + pk); }

        Cursor c = qb.query(getDb(), proj, sel, selArgs, null, null, sort);

        return c;
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
    public Uri insert(Uri arg0, ContentValues arg1) {
        throw new UnsupportedOperationException("insert not supported");
    }

    @Override
    public int update(Uri arg0, ContentValues vals, String sel, String[] selArgs) {
        throw new UnsupportedOperationException("update not supported");
    }

    @Override
    public int delete(Uri arg0, String arg1, String[] arg2) {
        throw new UnsupportedOperationException("delete not supported");
    }

    private SQLiteDatabase getDb() { return helper.getWritableDatabase(); }
}
