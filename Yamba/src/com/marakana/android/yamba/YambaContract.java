package com.marakana.android.yamba;

import android.content.ContentResolver;
import android.net.Uri;


public class YambaContract {
    private YambaContract() { }

    public static final String AUTHORITY = "com.marakana.android.yamba.timeline";

    public static final Uri BASE_URI = new Uri.Builder()
        .scheme(ContentResolver.SCHEME_CONTENT)
        .authority(AUTHORITY)
        .build();


    public static class Timeline {
        private Timeline() { }

        public static final String TABLE = "timeline";

        public static final Uri URI = BASE_URI.buildUpon().appendPath(TABLE).build();

        public static class Columns {
            private Columns() { }
            public static final String ID = "_id";
            public static final String TIMESTAMP = "created_at";
            public static final String USER = "user";
            public static final String STATUS = "status";
        }
    }
}
