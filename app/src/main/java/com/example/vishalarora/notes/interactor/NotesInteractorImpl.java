package com.example.vishalarora.notes.interactor;

import com.example.vishalarora.notes.data.Note;
import com.example.vishalarora.notes.db.contracts.NotesRepo;
import com.example.vishalarora.notes.db.contracts.Tables;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by vishalarora on 25/03/18.
 */

public class NotesInteractorImpl implements NotesInteractor {

    private NotesRepo notesRepo;

    public NotesInteractorImpl(NotesRepo notesRepo) {
        this.notesRepo = notesRepo;
    }

    @Override
    public Observable<List<Note>> fetchNotes() {
        return notesRepo.fetchNotes();
    }

    @Override
    public Observable<Boolean> addNote(Note note) {
        return notesRepo.addNote(note);
    }

    @Override
    public Observable<Boolean> removeNote(int noteId) {
        return notesRepo.removeNote(noteId);
    }

    @Override
    public Observable<List<Note>> fetchNotesOrderBy(@Tables.Notes.COLUMN String attribute, boolean reverse) {
        return notesRepo.fetchNotesOrderBy(attribute, reverse);
    }
}
