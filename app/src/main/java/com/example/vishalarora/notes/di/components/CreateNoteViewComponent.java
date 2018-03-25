package com.example.vishalarora.notes.di.components;

import com.example.vishalarora.notes.activity.CreateNoteActivity;
import com.example.vishalarora.notes.contracts.CreateNoteView;
import com.example.vishalarora.notes.di.modules.CreateNoteModule;
import com.example.vishalarora.notes.di.scope.ActivityScope;

import dagger.Component;

/**
 * Created by vishalarora on 25/03/18.
 */

@ActivityScope
@Component(modules = CreateNoteModule.class, dependencies = AppComponent.class)
public interface CreateNoteViewComponent {
    void inject(CreateNoteActivity createNoteView);
}
