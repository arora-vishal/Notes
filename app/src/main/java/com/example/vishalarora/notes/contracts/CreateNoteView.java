package com.example.vishalarora.notes.contracts;

/**
 * Created by vishalarora on 25/03/18.
 */

public interface CreateNoteView {
    void onNoteSaved();

    void showTitleEmptyError();

    void showBodyEmptyError();

    void setProgressView(boolean enabled);
}
