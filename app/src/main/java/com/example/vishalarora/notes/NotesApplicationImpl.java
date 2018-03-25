package com.example.vishalarora.notes;

import android.app.Application;

import com.example.vishalarora.notes.di.components.AppComponent;
import com.example.vishalarora.notes.di.components.DaggerAppComponent;
import com.example.vishalarora.notes.di.modules.AppModule;

/**
 * Created by vishalarora on 25/03/18.
 */

public class NotesApplicationImpl extends Application implements NotesApplication {

    private AppComponent appComponent;
    private static NotesApplicationImpl instance;

    public NotesApplicationImpl() {
        instance = this;
        appComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).build();
    }

    public static NotesApplicationImpl getInstance() {
        return instance;
    }

    @Override
    public AppComponent getAppComponent() {
        return appComponent;
    }

    @Override
    public NotesApplication getApplication() {
        return instance;
    }
}
