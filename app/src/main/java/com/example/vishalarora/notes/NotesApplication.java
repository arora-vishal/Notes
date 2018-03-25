package com.example.vishalarora.notes;

import com.example.vishalarora.notes.di.components.AppComponent;

/**
 * Created by vishalarora on 25/03/18.
 */

public interface NotesApplication {
    AppComponent getAppComponent();
    NotesApplication getApplication();
}
