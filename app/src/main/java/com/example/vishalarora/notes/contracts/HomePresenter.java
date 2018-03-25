package com.example.vishalarora.notes.contracts;

import com.example.vishalarora.notes.data.Note;

/**
 * Created by vishalarora on 25/03/18.
 */

public interface HomePresenter extends BasePresenter<HomeView> {
    void loadData();
    void reload();
    void removeNote(Note note, int action, int itemCount);
}
