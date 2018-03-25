package com.example.vishalarora.notes.db;

import android.content.ContentValues;
import android.database.Cursor;

import com.example.vishalarora.notes.data.Note;
import com.example.vishalarora.notes.db.contracts.NotesRepo;
import com.example.vishalarora.notes.db.contracts.Tables;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;


/**
 * Created by vishalarora on 25/03/18.
 */

public class NotesRepoImpl implements NotesRepo {

    private DbHelper dbHelper;

    public NotesRepoImpl(DbHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    @Override
    public Observable<List<Note>> fetchNotes() {
        return fetchNotesOrderBy(null, false);
    }

    @Override
    public Observable<Boolean> addNote(Note note) {
        return Observable.create(e -> {
            long insertResult = dbHelper.getWritableDatabase().insert(Tables.Notes.TABLE_NAME, null, valuesFrom(note));
            e.onNext(insertResult == -1);
            e.onComplete();
        });
    }

    @Override
    public Observable<Boolean> removeNote(int noteId) {
        return Observable.create(e -> {
            String whereClause = Tables.Notes.ID + " = ? ";
            String[] whereArgs = new String[]{String.valueOf(noteId)};
            long removeResult = dbHelper.getWritableDatabase().delete(Tables.Notes.TABLE_NAME, whereClause, whereArgs);
            e.onNext(removeResult > 0);
            e.onComplete();
        });
    }


    @Override
    public Observable<List<Note>> fetchNotesOrderBy(@Tables.Notes.COLUMN String attribute, boolean reverse) {
        return Observable.create(e -> {
            Cursor cursor = dbHelper.getReadableDatabase().query(Tables.Notes.TABLE_NAME, null, null, null, null, null, attribute + (reverse ? " DESC" : "") );
            List<Note> notes = new ArrayList<>();
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndex(Tables.Notes.ID));
                String title = cursor.getString(cursor.getColumnIndex(Tables.Notes.TITLE));
                String body = cursor.getString(cursor.getColumnIndex(Tables.Notes.BODY));
                long createdTimeStamp = cursor.getLong(cursor.getColumnIndex(Tables.Notes.CREATED_TIMESTAMP));
                long modifiedTimeStamp = cursor.getLong(cursor.getColumnIndex(Tables.Notes.MODIFIED_TIMESTAMP));

                notes.add(new Note.Builder()
                        .withId(id)
                        .withBody(body)
                        .withTitle(title)
                        .withCreatedTimeStamp(createdTimeStamp)
                        .withModifiedTimeStamp(modifiedTimeStamp).build());
            }

            cursor.close();
            e.onNext(notes);
            e.onComplete();
        });
    }

    private ContentValues valuesFrom(Note note) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(Tables.Notes.ID, getAvailableId());
        contentValues.put(Tables.Notes.TITLE, note.getTitle());
        contentValues.put(Tables.Notes.BODY, note.getBody());
        contentValues.put(Tables.Notes.CREATED_TIMESTAMP, note.getCreatedTimeStamp());
        contentValues.put(Tables.Notes.MODIFIED_TIMESTAMP, note.getModifiedTimeStamp());
        return contentValues;
    }

    private int getAvailableId() {
        return dbHelper.getReadableDatabase().query(Tables.Notes.TABLE_NAME, null, null, null, null, null, null).getCount() + 1;
    }
}
