package com.example.vishalarora.notes.interactor;

import com.example.vishalarora.notes.data.Note;

import io.reactivex.Observable;

/**
 * Created by vishalarora on 25/03/18.
 */

public interface CreateNoteInteractor {
    Observable<Boolean> createNote(Note note);
}
