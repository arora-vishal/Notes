package com.example.vishalarora.notes.interactor;

import com.example.vishalarora.notes.data.Note;
import com.example.vishalarora.notes.db.contracts.NotesRepo;

import io.reactivex.Observable;

/**
 * Created by vishalarora on 25/03/18.
 */

public class CreateNoteInteractorImpl implements CreateNoteInteractor{

    NotesRepo notesRepo;

    public CreateNoteInteractorImpl(NotesRepo notesRepo) {
        this.notesRepo = notesRepo;
    }

    @Override
    public Observable<Boolean> createNote(Note note) {
        return notesRepo.addNote(note);
    }
}
