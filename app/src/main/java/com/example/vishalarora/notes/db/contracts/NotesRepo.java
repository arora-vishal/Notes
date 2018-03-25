package com.example.vishalarora.notes.db.contracts;

import com.example.vishalarora.notes.data.Note;

import java.util.List;

import io.reactivex.Observable;


/**
 * Created by vishalarora on 25/03/18.
 */

public interface NotesRepo {

    Observable<List<Note>> fetchNotes();

    Observable<Boolean> addNote(Note note);

    Observable<Boolean> removeNote(int noteId);

    Observable<List<Note>> fetchNotesOrderBy(@Tables.Notes.COLUMN String attribute, boolean reverse);
}
