package com.example.vishalarora.notes.db.contracts;

import android.support.annotation.StringDef;

/**
 * Created by vishalarora on 25/03/18.
 */

public interface Tables {

    interface Notes {
        String TABLE_NAME = "notes";
        String ID = "id";
        String TITLE = "title";
        String BODY = "body";
        String CREATED_TIMESTAMP = "created_timestamp";
        String MODIFIED_TIMESTAMP = "modified_timestamp";

        String CREATE_TABLE_QUERY = "CREATE TABLE " +
                TABLE_NAME + "(" +
                ID + " INTEGER PRIMARY KEY," +
                TITLE + " TEXT," +
                BODY + " TEXT, " +
                CREATED_TIMESTAMP + " TEXT, " +
                MODIFIED_TIMESTAMP + " TEXT" +
                ")";

        @StringDef({ID, TITLE, BODY, CREATED_TIMESTAMP, MODIFIED_TIMESTAMP})
        @interface COLUMN {

        }

    }

}
