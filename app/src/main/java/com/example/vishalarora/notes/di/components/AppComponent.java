package com.example.vishalarora.notes.di.components;

import com.example.vishalarora.notes.db.DbHelper;
import com.example.vishalarora.notes.di.modules.AppModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by vishalarora on 25/03/18.
 */

@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {
    DbHelper dbHelper();
}
