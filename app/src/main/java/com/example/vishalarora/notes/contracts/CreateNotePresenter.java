package com.example.vishalarora.notes.contracts;

import io.reactivex.Observable;

/**
 * Created by vishalarora on 25/03/18.
 */

public interface CreateNotePresenter extends BasePresenter<CreateNoteView> {
    void saveNote(CharSequence title, CharSequence body);
}
