package com.example.vishalarora.notes.interactor;

import com.example.vishalarora.notes.data.Note;
import com.example.vishalarora.notes.db.contracts.Tables;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by vishalarora on 25/03/18.
 */

public interface NotesInteractor {

    Observable<List<Note>> fetchNotes();

    Observable<Boolean> addNote(Note note);

    Observable<Boolean> removeNote(int noteid);

    Observable<List<Note>> fetchNotesOrderBy(@Tables.Notes.COLUMN String attribute, boolean reverse);
}
