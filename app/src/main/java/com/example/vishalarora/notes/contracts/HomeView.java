package com.example.vishalarora.notes.contracts;

import com.example.vishalarora.notes.data.Note;

import java.util.List;

/**
 * Created by vishalarora on 25/03/18.
 */

public interface HomeView {

    void showDefault();

    void showNotes(List<Note> notes);

    void setProgressView(boolean enabled);

    void removeItem(Note note);

    void showError();
}
